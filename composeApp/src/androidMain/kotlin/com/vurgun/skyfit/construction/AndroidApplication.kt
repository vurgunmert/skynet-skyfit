package com.vurgun.skyfit.construction

import android.app.Application

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        loadKoinDependencyModules()
    }
}