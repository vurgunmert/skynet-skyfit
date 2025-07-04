package com.vurgun.skyfit.core.data.v1.domain.global.model

sealed class CharacterType(val id: Int) {
    data object Carrot : CharacterType(1)
    data object Koala : CharacterType(2)
    data object Panda : CharacterType(3)

    companion object {
        fun fromId(id: Int?): CharacterType? {
            return when (id) {
                Carrot.id -> Carrot
                Koala.id -> Koala
                Panda.id -> Panda
                else -> null
            }
        }
    }
}