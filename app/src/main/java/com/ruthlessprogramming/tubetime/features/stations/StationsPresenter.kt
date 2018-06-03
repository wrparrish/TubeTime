package com.ruthlessprogramming.tubetime.features.stations

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.ruthlessprogramming.domain.Arrival
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.domain.usecase.GetArrivalsForStop
import com.ruthlessprogramming.domain.usecase.GetLocation
import com.ruthlessprogramming.domain.usecase.GetStopsByRadius
import com.ruthlessprogramming.domain.usecase.StopParams
import com.ruthlessprogramming.tubetime.common.StationActions
import com.ruthlessprogramming.tubetime.features.stations.state.StationState
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import timber.log.Timber
import zendesk.suas.Listener
import zendesk.suas.State
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class StationsPresenter @Inject constructor(
    private val model: StationsModel,
    private val getStopsByRadius: GetStopsByRadius,
    private val getArrivalsForStop: GetArrivalsForStop,
    private val getLocation: GetLocation
) : MvpBasePresenter<StationsView>() {

    private val refreshIntervalSeconds = 30L
    private val compositeDisposable = CompositeDisposable()

    private val stateListener = Listener<State> { state ->
        val stationState = state.getState(StationState::class.java)
        if (stationState != null && isViewAttached) {
            view.render(stationState)
        }

    }

    override fun attachView(view: StationsView?) {
        super.attachView(view)
        model.addListener(stateListener)
        view?.render(model.getModelState())
        startTimerExecution()
    }


    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        model.removeListener(stateListener)
        compositeDisposable.clear()
    }

    fun fetchStopsForLocation() {
        model.dispatch(StationActions.SetLoading(true))
        getLocation.execute(GetLocation.Request)
            .map { it.resolvedLocation }
            .flatMap { location ->
                fetchStopsByRadius(location.lat, location.lon)
                    .map { response -> response.result }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<StopPoint>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(result: List<StopPoint>) {
                    model.dispatch(StationActions.SetStops(result))
                    getArrivalsForStops()
                }

                override fun onError(e: Throwable) {
                    model.dispatch(StationActions.SetError(e))
                    model.dispatch(StationActions.SetLoading(false))
                }
            })
    }

    private fun fetchStopsByRadius(
        lat: Double,
        lon: Double
    ): Observable<GetStopsByRadius.Response> {
        return getStopsByRadius.execute(
            GetStopsByRadius.Request(
                StopParams.LocationParams(
                    lat,
                    lon
                )
            )
        )

    }

    private fun startTimerExecution() {
        val currentState = model.getModelState()

        val refreshTime = currentState.refreshTime
        val timeNow = Instant.now()
        var delay = 0L
        if (refreshTime > timeNow) {  // could be handling activity reconfig
            val duration = Duration.between(timeNow, refreshTime)
            delay = duration.seconds
        }

        compositeDisposable.add(Observable.interval(delay, refreshIntervalSeconds, TimeUnit.SECONDS)
            .subscribe {
                model.dispatch(
                    StationActions.ClockTick(
                        Instant.now().plusSeconds(
                            refreshIntervalSeconds
                        )
                    )
                )
                getArrivalsForStops()
            })

    }


    private fun getArrivalsForStops() {
        val stops = model.getModelState().stops
        if (stops.isEmpty()) return

        Observable.fromIterable(stops)
            .flatMap {
                getArrivalsForStop.execute(GetArrivalsForStop.Request(it.naptanId))
                    .retry(2)
                    .map { it.result }

            }
            .subscribe(object: Observer<List<Arrival>?> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: List<Arrival>) {

                    model.dispatch(StationActions.SetArrivals(t))
                    model.dispatch(StationActions.SetLoading(false))
                }

                override fun onError(e: Throwable) {
                    model.dispatch(StationActions.SetLoading(false))
                    model.dispatch(StationActions.SetError(e))
                }
            })


}


}

