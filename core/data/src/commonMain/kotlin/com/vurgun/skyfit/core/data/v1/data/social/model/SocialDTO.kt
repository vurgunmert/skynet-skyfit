package com.vurgun.skyfit.core.data.v1.data.social.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CreatePostRequestDTO(
    val contentText: String,
    val originalPostId: Int? = null
)

@Serializable
data class GetUserPostsRequestDTO(
    val userId: Int,
    val userTypeId: Int
)

@Serializable
data class DeletePostRequestDTO(
    val postId: Int
)

@Serializable
data class CreateCommentRequestDTO(
    val postId: Int,
    val content: String,
    val parentCommentId: Int? = null
)

@Serializable
data class DeleteCommentRequestDTO(
    val commentId: Int
)

@Serializable
data class LikePostDTO(
    val postId: Int
)

@Serializable
data class LikeCommentDTO(
    val commentId: Int
)

@Serializable
data class UserPostDTO(
    @SerialName("postId") var postId: Int,
    @SerialName("userId") var userId: Int,
    @SerialName("profilePhoto") var profilePhoto: String? = null,
    @SerialName("username") var username: String,
    @SerialName("name") var name: String,
    @SerialName("contentText") var contentText: String,
    @SerialName("createdDate") var createdDate: String,
    @SerialName("updateDate") var updateDate: String? = null,
    @SerialName("shareCount") var shareCount: Int? = null,
    @SerialName("likeCount") var likeCount: Int? = null,
    @SerialName("isLiked") var isLiked: Boolean? = null,
    @SerialName("commentCount") var commentCount: Int? = null
)