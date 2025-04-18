package com.vurgun.skyfit.data.settings.domain.model

data class Member(
    val userId: Int,
    val profileImageUrl: String? = null,
    val username: String,
    val name: String,
    val surname: String
)
