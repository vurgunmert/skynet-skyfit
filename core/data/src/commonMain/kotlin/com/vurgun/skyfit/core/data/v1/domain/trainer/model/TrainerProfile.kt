package com.vurgun.skyfit.core.data.v1.domain.trainer.model

import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import kotlinx.datetime.LocalDate

data class TrainerProfile(
    val userId: Int,
    val username: String,
    val trainerId: Int,
    val profileImageUrl: String?,
    val backgroundImageUrl: String?,
    val bio: String,
    val firstName: String,
    val lastName: String,
    val gymId: Int?,
    val gymName: String?,
    val postCount: Int = 0,
    val lessonCount: Int = 0,
    val followerCount: Int = 0,
    val videoCount: Int = 0,
    val point: Float = 0f
) {
    val fullName = "$firstName $lastName"
    val isFacilityAssigned = gymId != null
}

data class FacilityTrainerProfile(
    val trainerId: Int,
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val bio: String,
    val height: Int,
    val weight: Int,
    val birthday: LocalDate,
    val genderName: String,
    val bodyType: BodyType,
    val bodyTypeName: String,
    val profileImageUrl: String?,
    val backgroundImageUrl: String?,
    val lessonTypeCount: Int,
    val followerCount: Int,
    val videoCount: Int,
    val point: Float,
) {
    val fullName = "$firstName $lastName"
}