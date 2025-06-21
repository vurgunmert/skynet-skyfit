package com.vurgun.skyfit.core.data.v1.domain.social.model

import kotlinx.datetime.LocalDateTime

data class UserPost(
    val postId: Int,
    val userId: Int,
    val profileImageUrl: String? = null,
    val username: String,
    val name: String,
    val contentText: String,
    val createdDate: LocalDateTime,
    val updateDate: LocalDateTime? = null,
    val shareCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val isLiked: Boolean,
)