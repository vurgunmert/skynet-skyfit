package com.vurgun.skyfit.data.network.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Platform-specific engine selection
expect fun httpClientEngine(): HttpClientEngineFactory<*>


val commonHttpClient = HttpClient(httpClientEngine()) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
}