package com.vurgun.skyfit.core.data.v1.domain.global.model

import kotlinx.serialization.Serializable

@Serializable
enum class BodyType(
    val id: Int,
    val englishName: String,
    val turkishName: String,
    val englishShort: String,
    val turkishShort: String
) {
    ECTOMORPH(1, "Ectomorph", "Ektomorf", "Ecto", "Ekto"),
    MESOMORPH(2, "Mesomorph", "Mezomorf", "Meso", "Mezo"),
    ENDOMORPH(3, "Endomorph", "Endomorf", "Endo", "Endo"),
    NOT_DEFINED(0, "Not Defined", "Tanımlanmamış", "-", "-");

    companion object {
        fun fromDisplayName(name: String): BodyType {
            return entries.find {
                it.englishName.equals(name, ignoreCase = true) ||
                        it.turkishName.equals(name, ignoreCase = true)
            } ?: NOT_DEFINED
        }

        fun fromId(id: Int?): BodyType {
            return entries.find { it.id == id } ?: NOT_DEFINED
        }
    }
}