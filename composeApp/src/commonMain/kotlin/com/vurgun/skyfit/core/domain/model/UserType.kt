package com.vurgun.skyfit.core.domain.model

sealed class UserType(val id: Int) {
    data object Guest : UserType(0)
    data object User : UserType(1)
    data object Trainer : UserType(2)
    data object Facility : UserType(3)

    companion object {
        fun fromId(id: Int): UserType? = when (id) {
            0 -> Guest
            1 -> User
            2 -> Trainer
            3 -> Facility
            else -> null
        }

        fun getAllUserTypes(): List<UserType> = listOf(Guest, User, Trainer, Facility)
    }
}
