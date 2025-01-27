package com.vurgun.skyfit.data.network.api

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun httpClientEngine(): HttpClientEngineFactory<*> = OkHttp