package com.ruthlessprogramming.tubetime.features.stations

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.ruthlessprogramming.tubetime.features.stations.state.StationState

interface StationsView: MvpView {
    fun render(stationState: StationState)
}