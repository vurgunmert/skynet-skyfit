package com.vurgun.skyfit.data.user.domain

data class TrainerProfile(
    val userId: Int,
    val username: String,
    val trainerId: Int,
    val profileImageUrl: String?,
    val backgroundImageUrl: String?,
    val bio: String,
    val firstName: String,
    val lastName: String,
    val gymId: Int,
    val gymName: String,
    val postCount: Int,
    val lessonCount: Int,
    val followerCount: Int,
) {
    val fullName = "$firstName $lastName"
}
