package com.vurgun.skyfit.core.network.client

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.JsClient

actual fun httpClientEngine(): HttpClientEngine = JsClient().create()