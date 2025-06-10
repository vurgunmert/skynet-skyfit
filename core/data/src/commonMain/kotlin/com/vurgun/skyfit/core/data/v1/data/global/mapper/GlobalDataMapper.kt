package com.vurgun.skyfit.core.data.v1.data.global.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.v1.data.global.model.ChallengeDTO
import com.vurgun.skyfit.core.data.v1.data.global.model.MediaCategoryDTO
import com.vurgun.skyfit.core.data.v1.data.global.model.UnitDescriptionDTO
import com.vurgun.skyfit.core.data.v1.domain.global.model.*
import kotlinx.datetime.Instant

object GlobalDataMapper {

//    fun GoalDTO.toDomain(): Goal = Goal(
//        id = id,
//        code = code,
//        nameEn = nameEn,
//        nameTr = nameTr,
//        description = description
//    )
//
//    fun Goal.toDTO(): GoalDTO = GoalDTO(
//        id = id,
//        code = code,
//        nameEn = nameEn,
//        nameTr = nameTr,
//        description = description
//    )

    fun MediaCategoryDTO.toDomain(): MediaCategory = MediaCategory(
        id = id,
        nameEn = nameEn,
        nameTr = nameTr,
        type = MediaCategoryType.from(id)
    )

    fun ChallengeDTO.toDomain() = Challenge(
        id = id,
        title = title,
        description = description,
        startDate = Instant.parse(startDate),
        endDate = Instant.parse(endDate),
        category = ChallengeCategory(category.id, category.nameEn, category.nameTr),
        type = ChallengeType(type.id, type.nameEn, type.nameTr),
        unit = unit.toDomain(),
        goalValue = goalValue,
        participantCount = participantCount,
        creatorId = creatorId,
        imageUrl = serverImageFromPath(imageUrlPath),
        isPublic = isPublic
    )

    fun UnitDescriptionDTO.toDomain() = UnitDescription(
        id = id,
        shortName = shortName,
        fullName = fullName,
        system = system
    )
}