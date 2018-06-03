package com.ruthlessprogramming.tubetime.features.linedetails

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineWithLocation

interface LineDetailView : MvpView {
    fun render(combinedState: LineWithLocation)
}