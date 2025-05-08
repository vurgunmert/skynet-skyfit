package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainerProfileDTO(
    val userId: Int,
    val username: String,
    val trainerId: Int,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val bio: String,
    val name: String,
    val surname: String,
    val gymId: Int?,
    val gymName: String?,
    val postCount: Int,
    val lessonCount: Int,
    val followerCount: Int,
    val point: Float? = null
)

@Serializable
data class FacilityTrainerProfileDTO(
    val trainerId: Int,
    val userId: Int,
    val name: String,
    val surname: String,
    val bio: String,
    val height: Int,
    val weight: Int,
    val birthday: String,
    val genderName: String,
    val bodyTypeId: Int,
    val typeName: String,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val lessonTypeCount: Int,
    val followerCount: Int,
    val videoCount: Int,
    val point: Float,
)
