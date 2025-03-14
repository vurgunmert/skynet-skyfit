package com.vurgun.skyfit.core.network

import com.vurgun.skyfit.core.network.model.ApiResponse
import com.vurgun.skyfit.core.network.model.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

class ApiClient(val client: HttpClient) {

    suspend inline fun <reified T> safeApiCall(
        request: HttpRequestBuilder.() -> Unit
    ): ApiResult<T> {
        return try {
            val response: HttpResponse = client.request {
                apply(request)
            }
            //TODO: IF RESPONSE BODY LOGOUT == 1 -> unauthorized access -> logout user
            when (response.status.value) {
                in 200..299 -> {
                    val apiResponse: ApiResponse<T> = response.body()
                    ApiResult.Success(apiResponse.data ?: throw SerializationException("Empty data field"))
                }

                else -> {
                    val errorResponse: ApiResponse<Unit> = response.body()
                    ApiResult.Error(
                        status = errorResponse.status ?: "error",
                        title = errorResponse.title,
                        message = errorResponse.message
                    )
                }
            }
        } catch (e: ClientRequestException) { // 4xx errors
            val errorResponse: ApiResponse<Unit>? = e.response.runCatching { body<ApiResponse<Unit>>() }.getOrNull()
            ApiResult.Error(
                status = errorResponse?.status ?: "error",
                title = errorResponse?.title ?: "Client Error",
                message = errorResponse?.message ?: "An unexpected client error occurred"
            )
        } catch (e: ServerResponseException) { // 5xx errors
            val errorResponse: ApiResponse<Unit>? = e.response.runCatching { body<ApiResponse<Unit>>() }.getOrNull()
            ApiResult.Error(
                status = errorResponse?.status ?: "error",
                title = errorResponse?.title ?: "Server Error",
                message = errorResponse?.message ?: "An unexpected server error occurred"
            )
        } catch (e: IOException) { // Network issues
            ApiResult.Exception(e)
        } catch (e: SerializationException) { // JSON parsing issues
            ApiResult.Exception(e)
        }
    }
}
