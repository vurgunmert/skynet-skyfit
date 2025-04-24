package com.vurgun.skyfit.data.user.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUserProfileRequest(val nmId: Int)

@Serializable
data class GetTrainerProfileRequest(val trainerId: Int)

@Serializable
data class GetFacilityProfileRequest(val gymId: Int)