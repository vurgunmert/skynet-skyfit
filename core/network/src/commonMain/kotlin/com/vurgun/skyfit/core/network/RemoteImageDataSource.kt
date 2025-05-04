package com.vurgun.skyfit.core.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.readRawBytes

class RemoteImageDataSource(private val httpClient: HttpClient) {

    suspend fun getImageBytes(url: String): ByteArray {
        val response = httpClient.get(url)
        return response.readRawBytes()
    }
}
