package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.user.model.SelectUserTypeResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getRegisteredAccountTypes(): Result<List<UserAccountType>>
    suspend fun selectUserType(typeId: Int): Result<SelectUserTypeResponse>
    suspend fun getSecureUserDetails(): Result<BaseUserDetail>
}