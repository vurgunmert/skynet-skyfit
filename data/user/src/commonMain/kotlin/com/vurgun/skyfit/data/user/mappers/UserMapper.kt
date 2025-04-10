package com.vurgun.skyfit.data.user.mappers

import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.model.CharacterType
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.data.user.model.UserDetailDto

object UserDetailMapper {
    fun UserDetailDto.toDomain(): UserDetail {
        return UserDetail(
            userId = this.userId,
            userRole = UserRole.fromId(this.userTypeId),
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
