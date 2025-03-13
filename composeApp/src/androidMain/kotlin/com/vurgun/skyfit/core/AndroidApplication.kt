package com.vurgun.skyfit.core

import android.app.Application
import android.util.Log
import com.vurgun.skyfit.core.di.DependencyInjectionInitializer

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyInjectionInitializer.initKoin(this@AndroidApplication)


        Log.d("MV->", "TLS")
        System.setProperty("https.protocols", "TLSv1.3");
        System.setProperty("javax.net.debug", "ssl,handshake,record")
        Log.d("MV->", "TLS")
    }
}