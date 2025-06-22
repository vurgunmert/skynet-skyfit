package com.vurgun.skyfit.core.data.v1.data.social.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.social.mapper.SocialMediaDataMapper.toDomainUserPosts
import com.vurgun.skyfit.core.data.v1.data.social.model.CreateCommentRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.CreatePostRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.DeleteCommentRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.DeletePostRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.GetUserPostsRequestDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.LikeCommentDTO
import com.vurgun.skyfit.core.data.v1.data.social.model.LikePostDTO
import com.vurgun.skyfit.core.data.v1.data.social.service.SocialMediaApiService
import com.vurgun.skyfit.core.data.v1.domain.social.repository.SocialMediaRepository
import com.vurgun.skyfit.core.data.v1.domain.social.model.UserPost
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class SocialMediaRepositoryImpl(
    private val apiService: SocialMediaApiService,
    private val tokenManager: TokenManager,
    private val dispatchers: DispatcherProvider
) : SocialMediaRepository {

    override suspend fun createPost(content: String, originalPostId: Int?): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CreatePostRequestDTO(content, originalPostId)
        apiService.createPost(request, token).mapOrThrow { }
    }

    override suspend fun deletePost(postId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeletePostRequestDTO(postId)
        apiService.deletePost(request, token).mapOrThrow {}
    }

    override suspend fun getPostsByUser(userId: Int, userTypeId: Int): Result<List<UserPost>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUserPostsRequestDTO(userId, userTypeId)
        apiService.getPostsByUser(request, token).mapOrThrow { it.toDomainUserPosts() }
    }

    override suspend fun createComment(
        content: String,
        postId: Int, parentCommentId: Int?
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CreateCommentRequestDTO(postId, content, parentCommentId)
        apiService.createComment(request, token).mapOrThrow {}
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteCommentRequestDTO(commentId)
        apiService.deleteComment(request, token).mapOrThrow {}
    }

    override suspend fun likePost(postId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = LikePostDTO(postId)
        apiService.likePost(request, token).mapOrThrow {}
    }

    override suspend fun unlikePost(postId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = LikePostDTO(postId = postId)
        apiService.unlikePost(request, token).mapOrThrow {}
    }

    override suspend fun likeComment(commentId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = LikeCommentDTO(commentId = commentId)
        apiService.likeComment(request, token).mapOrThrow {}
    }

    override suspend fun unlikeComment(commentId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = LikeCommentDTO(commentId)
        apiService.unlikeComment(request, token).mapOrThrow {}
    }
}
