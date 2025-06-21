package com.vurgun.skyfit.core.data.v1.data.account.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.v1.data.account.model.AccountDTO
import com.vurgun.skyfit.core.data.v1.data.account.model.AccountTypeDTO
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.data.v1.domain.global.model.GenderType
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole

internal object AccountDataMapper {

    private fun AccountDTO.toUserDetail(): UserAccount {
        return UserAccount(
            userId = userId,
            normalUserId = normalUserId ?: -1,
            accountRole = AccountRole.fromId(userTypeId),
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

    private fun AccountDTO.toTrainerDetail(): TrainerAccount {
        return TrainerAccount(
            userId = userId,
            trainerId = trainerId ?: -1,
            accountRole = AccountRole.fromId(userTypeId),
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

    private fun AccountDTO.toFacilityDetail(): FacilityAccount {
        return FacilityAccount(
            userId = userId,
            gymId = gymId ?: -1,
            gymName = gymName.orEmpty(),
            gymAddress = gymAddress.orEmpty(),
            bio = bio,
            accountRole = AccountRole.fromId(userTypeId),
            username = username,
            phone = phone,
            email = email,
            backgroundImageUrl = serverImageFromPath(backgroundImagePath)
        )
    }

    fun AccountDTO.toDomain(): Account {
        return when (AccountRole.fromId(userTypeId)) {
            AccountRole.User -> toUserDetail()
            AccountRole.Trainer -> toTrainerDetail()
            AccountRole.Facility -> toFacilityDetail()
            else -> throw IllegalStateException("Unsupported user role: $userTypeId")
        }
    }

    fun List<AccountTypeDTO>.toDomain(): List<AccountType> {
        return this.map {
            AccountType(
                typeId = it.usertypeId,
                profilePhoto = serverImageFromPath(it.profilePhoto),
                typeName = it.typeName,
                fullName = it.name
            )
        }
    }
}