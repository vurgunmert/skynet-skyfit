package com.vurgun.skyfit.data.settings.model

import kotlinx.serialization.Serializable

@Serializable
data class AddGymMemberRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetGymMembersRequest(val gymId: Int)

@Serializable
data class GetPlatformMembersRequest(val gymId: Int) //TODO: NO GYM ID?

@Serializable
data class DeleteGymMemberRequest(val gymId: Int, val userId: Int)


@Serializable
data class AddGymTrainerRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetGymTrainersRequest(val gymId: Int)

@Serializable
data class DeleteGymTrainerRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetPlatformTrainersRequest(val gymId: Int)