package com.vurgun.skyfit.core.data.v1.usermedia.back.service

import com.vurgun.skyfit.core.data.v1.data.global.model.MediaCategoryDTO
import com.vurgun.skyfit.core.data.v1.usermedia.back.model.UserMediaItemDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class MediaApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_CATEGORIES = "media/categories"
        const val GET_USER_GALLERY = "media/user/gallery"
    }

    suspend fun getMediaCategories(token: String): ApiResult<List<MediaCategoryDTO>> {
        return apiClient.safeApiCall {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_CATEGORIES)
        }
    }

    suspend fun getUserMediaGallery(userId: String, token: String): ApiResult<List<UserMediaItemDTO>> {
        return apiClient.safeApiCall {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_USER_GALLERY)
            setBody(mapOf("userId" to userId))
        }
    }
}
