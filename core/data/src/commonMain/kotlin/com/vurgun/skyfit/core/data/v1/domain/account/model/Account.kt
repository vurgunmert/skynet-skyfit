package com.vurgun.skyfit.core.data.v1.domain.account.model

import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.CharacterType
import com.vurgun.skyfit.core.data.v1.domain.global.model.GenderType
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole

sealed interface Account {
    val userId: Int
    val accountRole: AccountRole
    val username: String
    val phone: String
    val email: String?
    val backgroundImageUrl: String?
}

data class UserAccount(
    override val userId: Int,
    val normalUserId: Int,
    override val accountRole: AccountRole,
    override val username: String,
    override val phone: String,
    override val email: String?,
    override val backgroundImageUrl: String,
    val profileImageUrl: String?,
    val firstName: String,
    val lastName: String,
    val height: Int,
    val weight: Int,
    val birthday: String,
    val characterType: CharacterType,
    val bodyType: BodyType,
    val gender: GenderType,
) : Account

data class TrainerAccount(
    override val userId: Int,
    val trainerId: Int,
    override val accountRole: AccountRole,
    override val username: String,
    override val phone: String,
    override val email: String?,
    override val backgroundImageUrl: String?,
    val profileImageUrl: String?,
    val height: Int,
    val weight: Int,
    val birthday: String?,
    val characterType: CharacterType,
    val bodyType: BodyType,
    val gender: GenderType,
    val firstName: String,
    val lastName: String,
) : Account

data class FacilityAccount(
    override val userId: Int,
    val gymId: Int,
    val gymName: String,
    val gymAddress: String,
    val bio: String?,
    override val accountRole: AccountRole,
    override val username: String,
    override val phone: String,
    override val email: String?,
    override val backgroundImageUrl: String?
) : Account