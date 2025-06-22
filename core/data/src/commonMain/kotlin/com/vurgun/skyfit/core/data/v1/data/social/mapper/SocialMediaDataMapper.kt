package com.vurgun.skyfit.core.data.v1.data.social.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.utility.parseServerToLocalDateTime
import com.vurgun.skyfit.core.data.v1.data.social.model.UserPostDTO
import com.vurgun.skyfit.core.data.v1.domain.social.model.UserPost
import kotlinx.datetime.LocalDateTime

internal object SocialMediaDataMapper {

    fun UserPostDTO.toDomainUserPost(): UserPost {
        return UserPost(
            postId = postId,
            userId = userId,
            profileImageUrl = serverImageFromPath(profilePhoto),
            username = username,
            name = name,
            contentText = contentText,
            createdDate = createdDate.parseServerToLocalDateTime(),
            updateDate = updateDate?.parseServerToLocalDateTime(),
            shareCount = shareCount ?: 0,
            likeCount = likeCount ?: 0,
            commentCount = commentCount ?: 0,
            isLiked = isLiked ?: false
        )
    }

    fun List<UserPostDTO>.toDomainUserPosts(): List<UserPost> = this.map { it.toDomainUserPost() }
}