package com.example.thedailygrind

import android.app.Application
import com.example.thedailygrind.data.AppContainer
import com.example.thedailygrind.data.DefaultAppContainer

class TheDailyGrindApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}