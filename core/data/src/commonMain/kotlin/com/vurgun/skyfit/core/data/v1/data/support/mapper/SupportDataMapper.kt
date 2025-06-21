package com.vurgun.skyfit.core.data.v1.data.support.mapper

import com.vurgun.skyfit.core.data.v1.data.support.model.SupportTypeDTO
import com.vurgun.skyfit.core.data.v1.domain.support.model.SupportType

internal object SupportDataMapper {

    fun SupportTypeDTO.toDomainSupportTypes(): SupportType = SupportType(typeId, typeName)

    fun List<SupportTypeDTO>.toDomainSupportTypes(): List<SupportType> = this.map { it.toDomainSupportTypes() }
}