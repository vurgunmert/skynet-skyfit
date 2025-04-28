package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUserProfileRequest(val nmId: Int)

@Serializable
data class GetTrainerProfileRequest(val trainerId: Int)

@Serializable
data class GetFacilityTrainerProfilesRequest(val gymId: Int)

@Serializable
data class GetFacilityProfileRequest(val gymId: Int)