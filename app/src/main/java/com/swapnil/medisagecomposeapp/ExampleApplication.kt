package com.swapnil.medisagecomposeapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ExampleApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
    }

    companion object {
        @get:Synchronized
        var instance: ExampleApplication? = null
            private set

        //var appPrefManager: AppPrefManager? = null
        lateinit var appContext: Context
    }
}