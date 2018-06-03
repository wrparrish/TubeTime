package com.ruthlessprogramming.tubetime.features.linedetails

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.domain.usecase.GetLineDetails
import com.ruthlessprogramming.domain.usecase.Result
import com.ruthlessprogramming.tubetime.common.AppState
import com.ruthlessprogramming.tubetime.common.LineDetailsActions
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineDetailsState
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineWithLocation
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import zendesk.suas.Listener
import zendesk.suas.State
import javax.inject.Inject

class LineDetailPresenter @Inject constructor(
    private val model: LineDetailsModel,
    private val getLineDetails: GetLineDetails
) : MvpBasePresenter<LineDetailView>() {

    private val compositeDisposable = CompositeDisposable()

    private val stateListener = Listener<State> { state ->
        val appState = state.getState(AppState::class.java)
        val lineState = state.getState(LineDetailsState::class.java)
        if (lineState != null && appState != null && isViewAttached) {
            view.render(LineWithLocation(appState, lineState))
        }

    }

    override fun attachView(view: LineDetailView?) {
        super.attachView(view)
        model.addListener(stateListener)
    }


    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        model.removeListener(stateListener)
        compositeDisposable.clear()
    }


    fun fetchDetailsForLine(lineId: String) {
        model.dispatch(LineDetailsActions.SetLoading(true))
        getLineDetails.execute(GetLineDetails.Request(lineId))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GetLineDetails.Response?> {
                override fun onComplete() {
                    model.dispatch(LineDetailsActions.SetLoading(false))
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(t: GetLineDetails.Response) {
                    val result = t.result
                    val action = when (result) {
                        is Result.Success -> LineDetailsActions.SetLineDetails(result.data)
                        is Result.Error -> LineDetailsActions.SetError(result.exception)
                    }
                    model.dispatch(action)

                }

                override fun onError(e: Throwable) {
                    model.dispatch(LineDetailsActions.SetLoading(false))
                    model.dispatch(LineDetailsActions.SetError(e))
                }
            })
    }

    fun initPassedArgs(passedLineId: String, passedStopPoint: StopPoint) {
        val existingLineId = model.getModelState().passedLineId
        model.dispatch(LineDetailsActions.Init(passedLineId, passedStopPoint))
        if (existingLineId != passedLineId) {
            fetchDetailsForLine(passedLineId)
        }
    }
}