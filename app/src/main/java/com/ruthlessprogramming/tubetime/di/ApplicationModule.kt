package com.ruthlessprogramming.tubetime.di

import android.app.Application
import android.content.Context
import com.ruthlessprogramming.data.datasources.LocationDataSourceImpl
import com.ruthlessprogramming.domain.datasource.LocationDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context = application

    @Provides
    @Singleton
    fun providesLocationSource(context: Context): LocationDataSource {
        return LocationDataSourceImpl(context)
    }
}