package com.vurgun.skyfit.data.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDto(
    @SerialName("userId") val userId: Int,
    @SerialName("userTypeId") val userTypeId: Int,
    @SerialName("name") val firstName: String? = null,
    @SerialName("surname") val lastName: String? = null,
    @SerialName("username") val username: String = "",
    @SerialName("height") val height: Int? = null,
    @SerialName("heightUnit") val heightUnit: Int? = null,
    @SerialName("weight") val weight: Int? = null,
    @SerialName("weightUnit") val weightUnit: Int? = null,
    @SerialName("birthday") val birthday: String? = null,
    @SerialName("gender") val gender: Int? = null,
    @SerialName("genderName") val genderName: String? = null,
    @SerialName("bodyTypeId") val bodyTypeId: Int? = null,
    @SerialName("bodyTypeName") val bodyTypeName: String? = null,
    @SerialName("characterId") val characterId: Int? = null,
    @SerialName("characterName") val characterName: String? = null,
    @SerialName("mail") val email: String? = null,
    @SerialName("phone") val phone: String = "",
    @SerialName("isNotification") val isNotification: Boolean? = null,
    @SerialName("isMail") val isMail: Boolean? = null,
    @SerialName("isSms") val isSms: Boolean? = null,
    @SerialName("profilePhoto") val profilePhotoPath: String? = null,
    @SerialName("createdDate") val createdDate: String? = null,
    @SerialName("bio") val bio: String? = null,
    @SerialName("gymId") val gymId: Int? = null,
    @SerialName("trainerId") val trainerId: Int? = null,
    @SerialName("nmId") val normalUserId: Int? = null,
    @SerialName("gymName") val gymName: String? = null,
    @SerialName("gymAdress") val gymAddress: String? = null,
    @SerialName("backgroundImage") val backgroundImagePath: String? = null
)