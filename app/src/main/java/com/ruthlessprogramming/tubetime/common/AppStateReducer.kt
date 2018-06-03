package com.ruthlessprogramming.tubetime.common

import zendesk.suas.Action
import zendesk.suas.Reducer
import javax.inject.Inject

class AppStateReducer  @Inject constructor(): Reducer<AppState>() {
    override fun getInitialState(): AppState = AppState()


    override fun reduce(state: AppState, action: Action<*>): AppState? {
        return when (action) {
            is AppActions.SetLocation -> state.copy(location = action.location)

            else -> state
        }
    }


}