package com.vurgun.skyfit.core.data.v1.data.support.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.support.SupportApiService
import com.vurgun.skyfit.core.data.v1.data.support.mapper.SupportDataMapper.toDomainSupportTypes
import com.vurgun.skyfit.core.data.v1.data.support.model.SupportRequestDTO
import com.vurgun.skyfit.core.data.v1.domain.support.repository.SupportRepository
import com.vurgun.skyfit.core.data.v1.domain.support.model.SupportType
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class SupportRepositoryImpl(
    private val apiService: SupportApiService,
    private val tokenManager: TokenManager,
    private val dispatchers: DispatcherProvider,
) : SupportRepository {

    override suspend fun getSupportTypes(): Result<List<SupportType>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getSupportTypes(token).mapOrThrow { it.toDomainSupportTypes() }
    }

    override suspend fun createSupportRequest(
        typeId: Int,
        email: String,
        message: String
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val body = SupportRequestDTO(typeId, email, message)
        apiService.createSupportRequest(body, token).mapOrThrow { }
    }
}