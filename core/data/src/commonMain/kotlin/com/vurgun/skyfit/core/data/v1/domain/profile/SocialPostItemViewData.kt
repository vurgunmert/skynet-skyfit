package com.vurgun.skyfit.core.data.v1.domain.profile

data class SocialPostItemViewData(
    val postId: String,
    val username: String,
    val socialLink: String?,
    val timeAgo: String?,
    val profileImageUrl: String?,
    val content: String,
    val imageUrl: String?,
    val favoriteCount: Int,
    val commentCount: Int,
    val shareCount: Int,
)