package com.vurgun.skyfit.core.data.v1.domain.global.model

data class Member(
    val userId: Int,
    val profileImageUrl: String? = null,
    val username: String,
    val name: String,
    val surname: String
) {
    val fullName: String = "$name $surname"
}
