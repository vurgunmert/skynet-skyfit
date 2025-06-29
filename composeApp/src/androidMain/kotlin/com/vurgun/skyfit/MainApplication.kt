package com.vurgun.skyfit

import android.app.Application

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AndroidNotifier.initialize(this)
    }
}
