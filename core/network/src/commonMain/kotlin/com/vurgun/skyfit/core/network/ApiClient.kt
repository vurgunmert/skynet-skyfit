package com.vurgun.skyfit.core.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
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
                        code = response.status.value,
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
