package com.ruthlessprogramming.tubetime.di

import com.ruthlessprogramming.tubetime.common.AppStateReducer
import com.ruthlessprogramming.tubetime.features.linedetails.state.LineDetailsReducer
import com.ruthlessprogramming.tubetime.features.stations.state.StationReducer
import dagger.Module
import dagger.Provides
import zendesk.suas.Store
import zendesk.suas.Suas
import javax.inject.Singleton

@Module
class ReduxModule {

    @Provides
    @Singleton
    fun provideStore(
        appStateReducer: AppStateReducer,
        stationReducer: StationReducer,
        lineDetailsReducer: LineDetailsReducer
    ): Store {
        return Suas.createStore(
            appStateReducer,
            stationReducer,
            lineDetailsReducer
        )
            .build()
    }
}