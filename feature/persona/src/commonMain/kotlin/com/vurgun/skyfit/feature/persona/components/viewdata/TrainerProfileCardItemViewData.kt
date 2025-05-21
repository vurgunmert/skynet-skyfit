package com.vurgun.skyfit.feature.persona.components.viewdata

data class TrainerProfileCardItemViewData(
    val trainerId: Int,
    val imageUrl: String,
    val name: String,
    val followerCount: Int,
    val classCount: Int,
    val videoCount: Int,
    val rating: Float?
)

data class FacilityProfileCardItemViewData(
    val facilityId: Int,
    val imageUrl: String,
    val name: String,
    val memberCount: Int,
    val trainerCount: Int,
    val rating: Float?
)