package com.vurgun.skyfit.feature.profile.components.viewdata

data class TrainerProfileCardItemViewData(
    val imageUrl: String,
    val name: String,
    val followerCount: Int,
    val classCount: Int,
    val videoCount: Int,
    val rating: Double?
)

data class FacilityProfileCardItemViewData(
    val imageUrl: String,
    val name: String,
    val memberCount: Int,
    val trainerCount: Int,
    val rating: Double?
)