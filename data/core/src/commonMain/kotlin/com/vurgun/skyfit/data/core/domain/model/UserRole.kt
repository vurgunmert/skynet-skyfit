package com.vurgun.skyfit.data.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class UserRole(val typeId: Int) {
    @Serializable
    data object Guest : UserRole(0)
    @Serializable
    data object User : UserRole(1)
    @Serializable
    data object Trainer : UserRole(2)
    @Serializable
    data object Facility : UserRole(3)

    companion object {
        fun fromId(id: Int): UserRole = when (id) {
            1 -> User
            2 -> Trainer
            3 -> Facility
            else -> Guest
        }

        fun getAllUserTypes(): List<UserRole> = listOf(Guest, User, Trainer, Facility)
    }
}
