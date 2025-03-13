package com.vurgun.skyfit.feature_onboarding.data.service

import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.model.ApiResult
import com.vurgun.skyfit.core.utils.toSlug
import com.vurgun.skyfit.feature_onboarding.data.OnboardingRequest
import com.vurgun.skyfit.feature_onboarding.data.OnboardingResponse
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

class OnboardingApiService(private val apiClient: ApiClient) {

    suspend fun onboardUser(request: OnboardingRequest, token: String): ApiResult<OnboardingResponse> {
        return apiClient.safeApiCall<OnboardingResponse> {
            method = HttpMethod.Post
            url("auth/onboarding")
            bearerAuth(token)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("userType", request.userType.toString())
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

                        val gymSlug = request.gymName?.toSlug()
                        request.backgroundImage?.let {
                            append(
                                key = "backgroundImage",
                                value = it,
                                headers = Headers.build {
                                    append(HttpHeaders.ContentType, "images/*")
                                    append(HttpHeaders.ContentDisposition, "filename=${gymSlug}-background.png")
                                }
                            )
                        }
                    }
                )
            )
        }
    }
}
