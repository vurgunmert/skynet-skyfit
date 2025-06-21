package com.vurgun.skyfit.core.data.v1.domain.support.repository

import com.vurgun.skyfit.core.data.v1.domain.support.model.SupportType

interface SupportRepository {

    suspend fun getSupportTypes(): Result<List<SupportType>>
    suspend fun createSupportRequest(typeId: Int, email: String, message: String): Result<Unit>
}