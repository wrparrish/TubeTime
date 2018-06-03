package com.ruthlessprogramming.tubetime.features.linedetails.state

import com.ruthlessprogramming.domain.LineDetails
import com.ruthlessprogramming.domain.StopPoint

data class LineDetailsState(
    val isLoading: Boolean = false,
    val hasFetchedDetails: Boolean = false,
    val isInErrorState: Boolean = false,
    val errorMessage: String = "",
    val lineDetails: LineDetails? = null,
    val passedStopPoint: StopPoint? = null,
    val passedLineId: String = ""
)