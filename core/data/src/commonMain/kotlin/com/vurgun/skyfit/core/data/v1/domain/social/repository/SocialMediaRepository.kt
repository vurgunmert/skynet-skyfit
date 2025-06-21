package com.vurgun.skyfit.core.data.v1.domain.social.repository

import com.vurgun.skyfit.core.data.v1.domain.social.model.UserPost

interface SocialMediaRepository {
    suspend fun createPost(content: String, originalPostId: Int? = null): Result<Unit>
    suspend fun deletePost(postId: Int): Result<Unit>
    suspend fun getPostsByUser(userId: Int, userTypeId: Int): Result<List<UserPost>>
    suspend fun createComment(content: String, postId: Int, parentCommentId: Int? = null): Result<Unit>
    suspend fun deleteComment(commentId: Int): Result<Unit>
    suspend fun likePost(postId: Int): Result<Unit>
    suspend fun unlikePost(postId: Int): Result<Unit>
    suspend fun likeComment(commentId: Int): Result<Unit>
    suspend fun unlikeComment(commentId: Int): Result<Unit>
}