package com.vurgun.skyfit.core.data.persona.data.service

import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.data.persona.data.model.SelectUserTypeRequest
import com.vurgun.skyfit.core.data.persona.data.model.SelectUserTypeResponse
import com.vurgun.skyfit.core.data.persona.data.model.UserAccountTypeDTO
import com.vurgun.skyfit.core.data.persona.data.model.UserDetailDTO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class UserApiService(private val apiClient: ApiClient) {

    suspend fun getDetails(token: String): ApiResult<UserDetailDTO> {
        return apiClient.safeApiCall<UserDetailDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("user/detail")
        }
    }

    suspend fun getAccountTypes(token: String): ApiResult<List<UserAccountTypeDTO>> {
        return apiClient.safeApiCall<List<UserAccountTypeDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/usertypes")
        }
    }

    suspend fun selectUserType(typeId: Int, token: String): ApiResult<SelectUserTypeResponse> {
        val request = SelectUserTypeRequest(typeId)
        return apiClient.safeApiCall<SelectUserTypeResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("select/usertype")
            setBody(request)
        }
    }
}