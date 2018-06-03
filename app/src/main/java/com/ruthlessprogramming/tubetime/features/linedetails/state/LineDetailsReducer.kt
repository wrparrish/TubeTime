package com.ruthlessprogramming.tubetime.features.linedetails.state

import com.ruthlessprogramming.tubetime.common.LineDetailsActions
import zendesk.suas.Action
import zendesk.suas.Reducer
import javax.inject.Inject

class LineDetailsReducer @Inject constructor() : Reducer<LineDetailsState>() {
    override fun getInitialState(): LineDetailsState = LineDetailsState()


    override fun reduce(state: LineDetailsState, action: Action<*>): LineDetailsState? {
        return when (action) {
            is LineDetailsActions.SetLoading -> state.copy(
                isLoading = action.isLoading,
                isInErrorState = if (action.isLoading) false else state.isInErrorState
            )

            is LineDetailsActions.SetError -> state.copy(
                isInErrorState = true,
                hasFetchedDetails = true,
                errorMessage = action.throwable.toString()
            )

            is LineDetailsActions.SetLineDetails -> state.copy(
                lineDetails = action.details,
                isLoading = false,
                hasFetchedDetails = true
            )

            is LineDetailsActions.Init -> state.copy(
                passedLineId = action.passedLineId,
                passedStopPoint = action.passedStopPoint
            )

            else -> state
        }


    }
}
