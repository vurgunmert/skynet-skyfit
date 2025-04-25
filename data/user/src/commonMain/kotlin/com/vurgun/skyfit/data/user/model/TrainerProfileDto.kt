package com.vurgun.skyfit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainerProfileDto(
    val userId: Int,
    val username: String,
    val trainerId: Int,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val bio: String,
    val name: String,
    val surname: String,
    val gymId: Int,
    val gymName: String,
    val postCount: Int,
    val lessonCount: Int,
    val followerCount: Int,
)
