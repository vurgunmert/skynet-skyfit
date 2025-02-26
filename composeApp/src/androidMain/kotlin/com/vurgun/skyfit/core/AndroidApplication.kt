package com.vurgun.skyfit.core

import android.app.Application
import com.vurgun.skyfit.core.di.DependencyInjectionInitializer

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyInjectionInitializer.initKoin(this@AndroidApplication)
    }
}