package com.vurgun.skyfit.core.data.persona.domain.model

sealed interface BaseUserDetail {
    val userId: Int
    val userRole: UserRole
    val username: String
    val phone: String
    val email: String?
    val backgroundImageUrl: String?
}

data class UserDetail(
    override val userId: Int,
    val normalUserId: Int,
    override val userRole: UserRole,
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
) : BaseUserDetail

data class TrainerDetail(
    override val userId: Int,
    val trainerId: Int,
    override val userRole: UserRole,
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
) : BaseUserDetail

data class FacilityDetail(
    override val userId: Int,
    val gymId: Int,
    val gymName: String,
    val gymAddress: String,
    val bio: String?,
    override val userRole: UserRole,
    override val username: String,
    override val phone: String,
    override val email: String?,
    override val backgroundImageUrl: String?
) : BaseUserDetail