package com.vurgun.skyfit.core.data.v1.data.account.service

import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.account.model.AccountTypeDTO
import com.vurgun.skyfit.core.data.v1.data.account.model.AccountDTO
import com.vurgun.skyfit.core.data.v1.data.account.model.SelectActiveAccountTypeRequestDTO
import com.vurgun.skyfit.core.data.v1.data.account.model.SelectActiveAccountTypeResponseDTO
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingFormData
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

class AccountApiService(private val apiClient: ApiClient) {

    private companion object Endpoint {
        const val ONBOARDING_NEW = "auth/onboarding"
        const val ONBOARDING_ADD = "new/type/onboarding"
    }

    suspend fun getDetails(token: String): ApiResult<AccountDTO> {
        return apiClient.safeApiCall<AccountDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url("user/detail")
        }
    }

    suspend fun getAccountTypes(token: String): ApiResult<List<AccountTypeDTO>> {
        return apiClient.safeApiCall<List<AccountTypeDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url("get/usertypes")
        }
    }

    suspend fun selectUserType(typeId: Int, token: String): ApiResult<SelectActiveAccountTypeResponseDTO> {
        val request = SelectActiveAccountTypeRequestDTO(typeId)
        return apiClient.safeApiCall<SelectActiveAccountTypeResponseDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url("select/usertype")
            setBody(request)
        }
    }

    suspend fun onboardNewAccount(request: AccountOnboardingFormData, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            url(Endpoint.ONBOARDING_NEW)
            bearerAuth(token)
            setBody(buildMultipartFormData(request))
        }
    }

    suspend fun onboardingAdditionalAccount(request: AccountOnboardingFormData, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            url(Endpoint.ONBOARDING_ADD)
            bearerAuth(token)
            setBody(buildMultipartFormData(request))
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun buildMultipartFormData(request: AccountOnboardingFormData): MultiPartFormDataContent {
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