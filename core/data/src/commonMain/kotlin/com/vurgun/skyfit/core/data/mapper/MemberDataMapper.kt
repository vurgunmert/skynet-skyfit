package com.vurgun.skyfit.core.data.mapper

import com.vurgun.skyfit.core.data.mappers.serverImageFromPath
import com.vurgun.skyfit.core.data.domain.model.Member
import com.vurgun.skyfit.core.data.domain.model.Trainer
import com.vurgun.skyfit.core.data.model.MemberDto
import com.vurgun.skyfit.core.data.model.TrainerDto

fun MemberDto.toMemberDomain(): Member {
    return Member(
        userId = userId,
        profileImageUrl = serverImageFromPath(profilePhotoPath),
        username = username,
        name = name,
        surname = surname
    )
}

fun TrainerDto.toTrainerDomain(): Trainer {
    return Trainer(
        userId = userId,
        trainerId = trainerId,
        profileImageUrl = serverImageFromPath(profilePhotoPath),
        username = username,
        fullName = "$name $surname"
    )
}

fun List<MemberDto>.toMemberDomainList(): List<Member> = map { it.toMemberDomain() }

fun List<TrainerDto>.toTrainerDomainList(): List<Trainer> = map { it.toTrainerDomain() }
