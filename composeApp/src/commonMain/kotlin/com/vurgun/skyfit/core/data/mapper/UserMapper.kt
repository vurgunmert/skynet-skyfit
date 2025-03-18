package com.vurgun.skyfit.core.data.mapper

import com.vurgun.skyfit.core.data.models.UserDetailDto
import com.vurgun.skyfit.core.domain.models.CharacterType
import com.vurgun.skyfit.core.domain.models.UserDetail
import com.vurgun.skyfit.core.domain.models.UserType

object UserDetailMapper {
    fun UserDetailDto.toDomain(): UserDetail {
        return UserDetail(
            userId = this.userId,
            userType = UserType.fromId(this.userTypeId),
            characterType = CharacterType.fromId(this.characterId),
            username = this.username,
            phone = this.phone,
            gymName = this.gymName,
            gymAddress = this.gymAddress,
            bio = this.bio,
            backgroundImagePath = this.backgroundImagePath,
            email = this.email,
            birthday = this.birthday
        )
    }
}
