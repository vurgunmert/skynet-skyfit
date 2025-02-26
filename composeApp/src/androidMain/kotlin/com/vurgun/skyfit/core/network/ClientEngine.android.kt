package com.vurgun.skyfit.core.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun httpClientEngine(): HttpClientEngineFactory<*> = OkHttp