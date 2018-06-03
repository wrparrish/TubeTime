package com.ruthlessprogramming.tubetime.features.stations

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.ruthlessprogramming.domain.StopPoint
import com.ruthlessprogramming.tubetime.Navigator
import com.ruthlessprogramming.tubetime.R
import com.ruthlessprogramming.tubetime.di.Injector
import com.ruthlessprogramming.tubetime.features.stations.epoxy.StationsController
import com.ruthlessprogramming.tubetime.features.stations.state.StationState
import kotlinx.android.synthetic.main.screen_station_view.rvStations


class StationsViewImpl : MvpFragment<StationsView, StationsPresenter>(), StationsView {

    lateinit var navigator: Navigator
    private val stationsCallback = object : StationViewCallbacks {
        override fun onLineChosenForDetail(stopPoint: StopPoint, lineId: String) {
            navigator.navigateToLineDetails(stopPoint, lineId)
        }

        override fun onRetryClicked() {
            presenter.fetchStopsForLocation()
        }

    }


    private val controller: StationsController by lazy {
        StationsController(
            resources,
            stationsCallback
        )
    }

    override fun render(stationState: StationState) {
        controller.setData(stationState)

        if (!stationState.hasFetchedStations && !stationState.isLoading) {
            presenter.fetchStopsForLocation()
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        navigator = (context) as Navigator
    }

    override fun createPresenter(): StationsPresenter = Injector.get().stationsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_station_view, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecView()

    }

    private val spanCount = 3
    private fun setupRecView() {
        val layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                LinearLayoutManager(activity).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
            } else {
                GridLayoutManager(context, spanCount)
            }

        rvStations.apply {
            setLayoutManager(layoutManager)
            setHasFixedSize(true)
            adapter = controller.adapter
        }
    }

    interface StationViewCallbacks {
        fun onLineChosenForDetail(stopPoint: StopPoint, lineId: String)
        fun onRetryClicked()
    }
}
