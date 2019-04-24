package com.example.companion

import android.app.Application

class App : Application() {

    private lateinit var configurationManager : ConfigurationManager

    override fun onCreate() {
        super.onCreate()
        configurationManager.init(this)
    }

    fun getConfigurationManager(): ConfigurationManager {
        return configurationManager
    }
}
