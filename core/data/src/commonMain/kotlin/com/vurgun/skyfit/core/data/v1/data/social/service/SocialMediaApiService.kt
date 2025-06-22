package com.vurgun.skyfit.core.data.v1.data.social.service

import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.CreateCommentRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.CreatePostRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.DeleteCommentRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.DeletePostRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.GetUserPostsRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.LikeCommentDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.LikePostDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.UserPostDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class SocialMediaApiService(private val apiClient: ApiClient) {

    private companion object Endpoint {
        const val CREATE_POST = "social/post/add"
        const val DELETE_POST = "social/post/delete"
        const val GET_POSTS_BY_USER = "get/user/posts"
        const val CREATE_COMMENT = "social/post/comment/add"
        const val DELETE_COMMENT = "social/post/comment/delete"
        const val CREATE_POST_LIKE = "social/post/like"
        const val DELETE_POST_LIKE = "social/post/unlike"
        const val CREATE_COMMENT_LIKE = "social/post/comment/like"
        const val DELETE_COMMENT_LIKE = "social/post/comment/unlike"
    }

    suspend fun createPost(request: CreatePostRequestDTO, token: String): ApiResult<EmptyDTO> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(CREATE_POST)
            setBody(request)
        }

    suspend fun deletePost(request: DeletePostRequestDTO, token: String): ApiResult<EmptyDTO> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(DELETE_POST)
            setBody(request)
        }

    suspend fun getPostsByUser(request: GetUserPostsRequestDTO, token: String): ApiResult<List<UserPostDTO>> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(GET_POSTS_BY_USER)
            setBody(request)
        }

    suspend fun createComment(request: CreateCommentRequestDTO, token: String): ApiResult<EmptyDTO> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(CREATE_COMMENT)
            setBody(request)
        }

    suspend fun deleteComment(request: DeleteCommentRequestDTO, token: String): ApiResult<Unit> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(DELETE_COMMENT)
            setBody(request)
        }

    suspend fun likePost(request: LikePostDTO, token: String): ApiResult<Unit> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(CREATE_POST_LIKE)
            setBody(request)
        }

    suspend fun unlikePost(request: LikePostDTO, token: String): ApiResult<Unit> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(DELETE_POST_LIKE)
            setBody(request)
        }

    suspend fun likeComment(request: LikeCommentDTO, token: String): ApiResult<Unit> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(CREATE_COMMENT_LIKE)
            setBody(request)
        }

    suspend fun unlikeComment(request: LikeCommentDTO, token: String): ApiResult<Unit> =
        apiClient.safeApiCall {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(DELETE_COMMENT_LIKE)
            setBody(request)
        }
}