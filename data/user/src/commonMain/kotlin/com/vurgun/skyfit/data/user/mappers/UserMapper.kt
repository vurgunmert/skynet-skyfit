package com.vurgun.skyfit.data.user.mappers

import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.CharacterType
import com.vurgun.skyfit.data.core.domain.model.FacilityDetail
import com.vurgun.skyfit.data.core.domain.model.TrainerDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.data.network.BASE_IMAGE_URL
import com.vurgun.skyfit.data.user.model.UserAccountTypeDto
import com.vurgun.skyfit.data.user.model.UserDetailDto

object UserDetailMapper {

    private fun fullPath(path: String?): String =
        if (path.isNullOrBlank()) "" else BASE_IMAGE_URL + path

    fun UserDetailDto.toUserDetail(): UserDetail {
        return UserDetail(
            userId = userId,
            normalUserId = normalUserId ?: -1,
            userRole = UserRole.fromId(userTypeId),
            username = username,
            phone = phone,
            email = email,
            backgroundImageUrl = fullPath(backgroundImagePath),
            profileImageUrl = fullPath(profilePhotoPath),
            height = height,
            weight = weight,
            birthday = birthday,
            characterType = characterId?.let { CharacterType.fromId(it) },
            bodyTypeId = bodyTypeId,
            gender = gender,
            firstName = firstName.orEmpty(),
            lastName = lastName.orEmpty()
        )
    }

    fun UserDetailDto.toTrainerDetail(): TrainerDetail {
        return TrainerDetail(
            userId = userId,
            trainerId = trainerId ?: -1,
            userRole = UserRole.fromId(userTypeId),
            username = username,
            phone = phone,
            email = email,
            backgroundImageUrl = fullPath(backgroundImagePath),
            profileImageUrl = fullPath(profilePhotoPath),
            height = height,
            weight = weight,
            birthday = birthday,
            characterType = characterId?.let { CharacterType.fromId(it) },
            bodyTypeId = bodyTypeId,
            gender = gender,
            firstName = firstName.orEmpty(),
            lastName = lastName.orEmpty()
        )
    }

    fun UserDetailDto.toFacilityDetail(): FacilityDetail {
        return FacilityDetail(
            userId = userId,
            gymId = gymId ?: -1,
            gymName = gymName.orEmpty(),
            gymAddress = gymAddress.orEmpty(),
            bio = bio,
            userRole = UserRole.fromId(userTypeId),
            username = username,
            phone = phone,
            email = email,
            backgroundImageUrl = fullPath(backgroundImagePath)
        )
    }

    fun UserDetailDto.toDomain(): BaseUserDetail {
        return when (UserRole.fromId(userTypeId)) {
            UserRole.User -> toUserDetail()
            UserRole.Trainer -> toTrainerDetail()
            UserRole.Facility -> toFacilityDetail()
            else -> throw IllegalStateException("Unsupported user role: $userTypeId")
        }
    }

    fun List<UserAccountTypeDto>.toDomain(): List<UserAccountType> {
        return this.map {
            UserAccountType(
                typeId = it.usertypeId,
                profilePhoto = it.profilePhoto, //TODO: probably this one is also path
                typeName = it.typeName,
                fullName = it.name
            )
        }
    }
}