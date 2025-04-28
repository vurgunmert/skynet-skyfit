package com.vurgun.skyfit.core.data.mappers

import com.vurgun.skyfit.core.data.domain.model.BaseUserDetail
import com.vurgun.skyfit.core.data.domain.model.BodyType
import com.vurgun.skyfit.core.data.domain.model.CharacterType
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.model.GenderType
import com.vurgun.skyfit.core.data.domain.model.TrainerDetail
import com.vurgun.skyfit.core.data.domain.model.UserAccountType
import com.vurgun.skyfit.core.data.domain.model.UserDetail
import com.vurgun.skyfit.core.data.domain.model.UserRole
import com.vurgun.skyfit.core.data.model.UserAccountTypeDTO
import com.vurgun.skyfit.core.data.model.UserDetailDTO
import com.vurgun.skyfit.core.network.BASE_IMAGE_URL

fun serverImageFromPath(path: String?): String =
    if (path.isNullOrBlank()) "" else BASE_IMAGE_URL + path

object UserDetailMapper {

    fun UserDetailDTO.toUserDetail(): UserDetail {
        return UserDetail(
            userId = userId,
            normalUserId = normalUserId ?: -1,
            userRole = UserRole.fromId(userTypeId),
            username = username,
            phone = phone,
            email = email,
            backgroundImageUrl = serverImageFromPath(backgroundImagePath),
            profileImageUrl = serverImageFromPath(profilePhotoPath),
            height = height ?: -1,
            weight = weight ?: -1,
            birthday = birthday.orEmpty(),
            characterType = characterId?.let { CharacterType.fromId(it) } ?: CharacterType.Carrot,
            bodyType = BodyType.fromId(bodyTypeId),
            gender = GenderType.from(gender),
            firstName = firstName.orEmpty(),
            lastName = lastName.orEmpty()
        )
    }

    fun UserDetailDTO.toTrainerDetail(): TrainerDetail {
        return TrainerDetail(
            userId = userId,
            trainerId = trainerId ?: -1,
            userRole = UserRole.fromId(userTypeId),
            username = username,
            phone = phone,
            email = email,
            backgroundImageUrl = serverImageFromPath(backgroundImagePath),
            profileImageUrl = serverImageFromPath(profilePhotoPath),
            height = height ?: -1,
            weight = weight ?: -1,
            birthday = birthday,
            characterType = characterId?.let { CharacterType.fromId(it) } ?: CharacterType.Carrot,
            bodyType = BodyType.fromId(bodyTypeId),
            gender = GenderType.from(gender),
            firstName = firstName.orEmpty(),
            lastName = lastName.orEmpty()
        )
    }

    fun UserDetailDTO.toFacilityDetail(): FacilityDetail {
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
            backgroundImageUrl = serverImageFromPath(backgroundImagePath)
        )
    }

    fun UserDetailDTO.toDomain(): BaseUserDetail {
        return when (UserRole.fromId(userTypeId)) {
            UserRole.User -> toUserDetail()
            UserRole.Trainer -> toTrainerDetail()
            UserRole.Facility -> toFacilityDetail()
            else -> throw IllegalStateException("Unsupported user role: $userTypeId")
        }
    }

    fun List<UserAccountTypeDTO>.toDomain(): List<UserAccountType> {
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