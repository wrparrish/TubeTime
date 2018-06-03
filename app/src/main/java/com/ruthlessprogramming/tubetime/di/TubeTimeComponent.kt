package com.ruthlessprogramming.tubetime.di

import com.ruthlessprogramming.data.common.di.DataModule
import com.ruthlessprogramming.tubetime.features.linedetails.LineDetailPresenter
import com.ruthlessprogramming.tubetime.features.stations.StationsPresenter
import dagger.Component
import zendesk.suas.Store
import javax.inject.Singleton


@Singleton
@Component(modules = [(ApplicationModule::class),(ReduxModule::class), (DataModule::class)])
interface TubeTimeComponent {
    val store: Store
    val stationsPresenter: StationsPresenter
    val linePresenter: LineDetailPresenter
}
