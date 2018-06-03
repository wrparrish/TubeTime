package com.ruthlessprogramming.tubetime.di

import com.ruthlessprogramming.tubetime.TubeTime

class Injector private constructor() {
    companion object {
        fun get(): TubeTimeComponent =
            TubeTime.INSTANCE.component
    }
}