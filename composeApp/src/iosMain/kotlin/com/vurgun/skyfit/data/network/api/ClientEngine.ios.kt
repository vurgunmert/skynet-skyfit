package com.vurgun.skyfit.data.network.api

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun httpClientEngine(): HttpClientEngineFactory<*> = Darwin