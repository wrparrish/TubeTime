package com.ruthlessprogramming.tubetime

import com.ruthlessprogramming.tubetime.common.AppStateReducer
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineDetailsReducer
import com.ruthlessprogramming.tubetime.features.stations.state.StationReducer
import zendesk.suas.Store
import zendesk.suas.Suas

abstract class JvmStateTest {

    val store: Store = createTestStore()

    private fun createTestStore(): Store {
        return Suas.createStore(
            AppStateReducer(),
            StationReducer(),
            LineDetailsReducer()
        ).build()
    }
}