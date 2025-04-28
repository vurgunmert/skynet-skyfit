package com.vurgun.skyfit.core.data.domain.repository

import com.vurgun.skyfit.core.data.domain.model.BaseUserDetail
import com.vurgun.skyfit.core.data.domain.model.UserAccountType
import com.vurgun.skyfit.core.data.model.SelectUserTypeResponse

interface UserRepository {
    suspend fun getRegisteredAccountTypes(): Result<List<UserAccountType>>
    suspend fun selectUserType(typeId: Int): Result<SelectUserTypeResponse>
    suspend fun getSecureUserDetails(): Result<BaseUserDetail>
}