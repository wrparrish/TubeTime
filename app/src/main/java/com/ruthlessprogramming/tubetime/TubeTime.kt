package com.ruthlessprogramming.tubetime

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.ruthlessprogramming.tubetime.di.ApplicationModule
import com.ruthlessprogramming.tubetime.di.DaggerTubeTimeComponent
import com.ruthlessprogramming.tubetime.di.TubeTimeComponent
import timber.log.Timber

class TubeTime : Application() {

    val component: TubeTimeComponent by lazy {
        DaggerTubeTimeComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        setupLogging()
        AndroidThreeTen.init(this)
    }

    private fun setupLogging() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var INSTANCE: TubeTime
            private set
    }
}