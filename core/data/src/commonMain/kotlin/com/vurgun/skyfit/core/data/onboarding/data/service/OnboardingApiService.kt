package com.vurgun.skyfit.core.data.onboarding.data.service

import com.vurgun.skyfit.core.data.onboarding.data.model.OnboardingRequest
import com.vurgun.skyfit.core.data.onboarding.data.model.OnboardingResponse
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.headersOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class OnboardingApiService(private val apiClient: ApiClient) {

    suspend fun onboardNewAccount(request: OnboardingRequest, token: String): ApiResult<OnboardingResponse> {
        return apiClient.safeApiCall<OnboardingResponse> {
            method = HttpMethod.Post
            url("auth/onboarding")
            bearerAuth(token)
            setBody(buildOnboardingFormData(request))
        }
    }

    suspend fun onboardingAdditionalAccount(request: OnboardingRequest, token: String): ApiResult<OnboardingResponse> {
        return apiClient.safeApiCall<OnboardingResponse> {
            method = HttpMethod.Post
            url("new/type/onboarding")
            bearerAuth(token)
            setBody(buildOnboardingFormData(request))
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildOnboardingFormData(request: OnboardingRequest): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append("usertype", request.userType.toString())
                request.characterId?.let { append("characterId", it.toString()) }
                request.birthdate?.let { append("birthdate", it) }
                request.gender?.let { append("gender", it.toString()) }
                request.weight?.let { append("weight", it.toString()) }
                request.weightUnit?.let { append("weightUnit", it.toString()) }
                request.height?.let { append("height", it.toString()) }
                request.heightUnit?.let { append("heightUnit", it.toString()) }
                request.bodyTypeId?.let { append("bodyTypeId", it.toString()) }
                request.name?.let { append("name", it) }
                request.surname?.let { append("surname", it) }
                request.gymName?.let { append("gymName", it) }
                request.gymAddress?.let { append("gymAdress", it) }
                request.bio?.let { append("bio", it) }

                request.profileTags?.let { append("profileTags", it.joinToString(",")) }
                request.goals?.let { append("goals", it.joinToString(",")) }

                request.backgroundImage?.let { bytes ->
                    val uuid = Uuid.random()

                    append(
                        "backgroundImage", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${uuid}-background.png")
                        )
                    )
                }
            }
        )
    }
}
