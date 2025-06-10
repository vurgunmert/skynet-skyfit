package com.vurgun.skyfit.core.data.v1.domain.global.model

sealed class GenderType(val id: Int, val label: String) {
    data object MALE : GenderType(1, "Erkek")
    data object FEMALE : GenderType(2, "Kadın")

    companion object {
        fun from(id: Int?): GenderType {
            return when (id) {
                2 -> FEMALE
                else -> MALE
            }
        }

        fun getAllGenders(): List<GenderType> = listOf(MALE, FEMALE)
    }
}
