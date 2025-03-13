package com.vurgun.skyfit.core.domain.model

sealed class CharacterType(val id: Int) {
    data object Carrot: CharacterType(1)
    data object Koala: CharacterType(2)
    data object Panda: CharacterType(3)
}