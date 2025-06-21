package com.vurgun.skyfit.core.data.v1.domain.global.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AccountRole(val typeId: Int) {
    @Serializable
    data object Guest : AccountRole(0)
    @Serializable
    data object User : AccountRole(1)
    @Serializable
    data object Trainer : AccountRole(2)
    @Serializable
    data object Facility : AccountRole(3)

    companion object {
        fun fromId(id: Int): AccountRole = when (id) {
            1 -> User
            2 -> Trainer
            3 -> Facility
            else -> Guest
        }

        fun listOfAllowedRoles() = listOf(User, Trainer, Facility)

        fun setOfAllowedRoles() = setOf(User, Trainer, Facility)
    }
}
