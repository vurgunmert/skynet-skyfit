package com.vurgun.skyfit.data.core.domain.model

sealed class GenderType(val id: Int, val label: String) {
    data object MALE : GenderType(1, "Erkek")
    data object FEMALE : GenderType(2, "Kadın")

    companion object {
        fun from(id: Int): GenderType? {
            return when (id) {
                1 -> MALE
                2 -> FEMALE
                else -> null
            }
        }

        fun getAllGenders(): List<GenderType> = listOf(MALE, FEMALE)
    }
}
