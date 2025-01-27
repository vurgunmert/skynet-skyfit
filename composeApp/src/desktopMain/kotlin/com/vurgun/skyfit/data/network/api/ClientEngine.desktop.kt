package com.vurgun.skyfit.data.network.api

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.cio.CIO

actual fun httpClientEngine(): HttpClientEngineFactory<*> = CIO