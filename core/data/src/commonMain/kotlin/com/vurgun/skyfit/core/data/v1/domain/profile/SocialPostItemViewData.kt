package com.vurgun.skyfit.core.data.v1.domain.profile

data class SocialPostItemViewData(
    val postId: Int,
    val creatorName: String,
    val creatorUsername: String,
    val timeAgo: String,
    val creatorImageUrl: String?,
    val content: String,
    val imageUrl: String?,
    val likeCount: Int,
    val commentCount: Int,
    val shareCount: Int,
)