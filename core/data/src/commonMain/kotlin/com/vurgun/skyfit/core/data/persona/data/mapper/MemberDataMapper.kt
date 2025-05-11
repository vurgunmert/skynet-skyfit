package com.vurgun.skyfit.core.data.persona.data.mapper

import com.vurgun.skyfit.core.data.persona.domain.model.Member
import com.vurgun.skyfit.core.data.persona.domain.model.Trainer
import com.vurgun.skyfit.core.data.persona.data.model.MemberDTO
import com.vurgun.skyfit.core.data.persona.data.model.TrainerDTO

fun MemberDTO.toMemberDomain(): Member {
    return Member(
        userId = userId,
        profileImageUrl = serverImageFromPath(profilePhotoPath),
        username = username,
        name = name,
        surname = surname
    )
}

fun TrainerDTO.toTrainerDomain(): Trainer {
    return Trainer(
        userId = userId,
        trainerId = trainerId,
        profileImageUrl = serverImageFromPath(profilePhotoPath),
        username = username,
        fullName = "$name $surname"
    )
}

fun List<MemberDTO>.toMemberDomainList(): List<Member> = map { it.toMemberDomain() }

fun List<TrainerDTO>.toTrainerDomainList(): List<Trainer> = map { it.toTrainerDomain() }
