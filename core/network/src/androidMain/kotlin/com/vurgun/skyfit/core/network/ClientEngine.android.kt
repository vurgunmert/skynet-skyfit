package com.vurgun.skyfit.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.ConnectionSpec
import okhttp3.TlsVersion
import java.util.concurrent.TimeUnit

actual fun httpClientEngine(): HttpClientEngine {
    return OkHttp.create {
        config {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // Allow HTTP (cleartext) traffic
            connectionSpecs(
                listOf(
                    ConnectionSpec.CLEARTEXT,  // <- Allow HTTP (non-SSL)
                    ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                        .tlsVersions(TlsVersion.TLS_1_3, TlsVersion.TLS_1_2)
                        .allEnabledCipherSuites()
                        .build()
                )
            )
        }
    }
}

