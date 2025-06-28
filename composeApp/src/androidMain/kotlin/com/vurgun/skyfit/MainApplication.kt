package com.vurgun.skyfit

import android.app.Application
import com.vurgun.skyfit.AndroidNotifier

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

//        Log.d("MV->", "TLS")
//        System.setProperty("https.protocols", "TLSv1.3");
//        System.setProperty("javax.net.debug", "ssl,handshake,record")
//        Log.d("MV->", "TLS")

        AndroidNotifier.initialize()

    }
}
