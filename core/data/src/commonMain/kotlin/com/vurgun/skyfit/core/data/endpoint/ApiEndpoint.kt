package com.vurgun.skyfit.core.data.endpoint

import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

sealed class ApiEndpoint<Req, Res>(
    val path: String,
    val method: HttpMethod,
    val requiresToken: Boolean
)

suspend inline fun <reified Req, reified Res> ApiClient.callEndpoint(
    endpoint: ApiEndpoint<Req, Res>,
    token: String,
    requestBody: Req
): ApiResult<Res> {
    return safeApiCall {
        method = endpoint.method
        url(urlString = endpoint.path)
        if (endpoint.requiresToken) bearerAuth(token)
        setBody(requestBody)
    }
}