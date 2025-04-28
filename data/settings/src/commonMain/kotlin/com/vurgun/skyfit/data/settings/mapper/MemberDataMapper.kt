package com.vurgun.skyfit.data.settings.mapper

import com.vurgun.skyfit.core.network.BASE_HOST_URL
import com.vurgun.skyfit.data.settings.domain.model.Member
import com.vurgun.skyfit.data.settings.domain.model.Trainer
import com.vurgun.skyfit.data.settings.model.MemberDto
import com.vurgun.skyfit.data.settings.model.TrainerDto

fun MemberDto.toMemberDomain(): Member {
    return Member(
        userId = userId,
        profileImageUrl = BASE_HOST_URL + profilePhotoPath,
        username = username,
        name = name,
        surname = surname
    )
}

fun TrainerDto.toTrainerDomain(): Trainer {
    return Trainer(
        userId = userId,
        trainerId = trainerId,
        profileImageUrl = BASE_HOST_URL + profilePhotoPath,
        username = username,
        fullName = "$name $surname"
    )
}

fun List<MemberDto>.toMemberDomainList(): List<Member> = map { it.toMemberDomain() }

fun List<TrainerDto>.toTrainerDomainList(): List<Trainer> = map { it.toTrainerDomain() }
