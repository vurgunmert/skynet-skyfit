package com.vurgun.skyfit.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSURLRequestReloadIgnoringLocalCacheData

actual fun httpClientEngine(): HttpClientEngine = Darwin.create {

    configureSession {
        allowsCellularAccess = true
        waitsForConnectivity = true
        requestCachePolicy = NSURLRequestReloadIgnoringLocalCacheData
        timeoutIntervalForRequest = 30.0
        timeoutIntervalForResource = 60.0
    }
}