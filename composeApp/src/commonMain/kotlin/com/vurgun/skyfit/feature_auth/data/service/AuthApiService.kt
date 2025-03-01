package com.vurgun.skyfit.feature_auth.data.service

import com.vurgun.skyfit.core.data.models.ApiResponse
import com.vurgun.skyfit.feature_auth.data.model.SignInRequest
import com.vurgun.skyfit.feature_auth.data.model.SignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class AuthApiService(private val client: HttpClient) {

    suspend fun login(phone: String, password: String?): ApiResponse<SignInResponse> {
        val response: HttpResponse = client.post("auth") {
            setBody(SignInRequest(phone, password))
        }
        return response.body()
    }
}
