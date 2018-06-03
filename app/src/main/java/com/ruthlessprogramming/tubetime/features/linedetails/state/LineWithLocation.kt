package com.ruthlessprogramming.tubetime.features.linedetails.state

import com.ruthlessprogramming.tubetime.common.AppState

data class LineWithLocation(
    val appState: AppState,
    val lineDetailsState: LineDetailsState
)