package com.vurgun.skyfit.data.settings.model

import kotlinx.serialization.Serializable

@Serializable
data class MemberDto(
    val userId: Int,
    val profilePhoto: String? = null,
    val username: String,
    val name: String,
    val surname: String
)

data class Member(
    val userId: Int,
    val profilePhoto: String? = null,
    val username: String,
    val name: String,
    val surname: String
)

fun MemberDto.toDomain(): Member {
    return Member(
        userId = userId,
        profilePhoto = profilePhoto,
        username = username,
        name = name,
        surname = surname
    )
}

fun List<MemberDto>.toDomain(): List<Member> = map { it.toDomain() }
