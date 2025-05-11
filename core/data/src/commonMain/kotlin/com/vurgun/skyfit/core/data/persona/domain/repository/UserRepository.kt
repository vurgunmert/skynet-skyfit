package com.vurgun.skyfit.core.data.persona.domain.repository

import com.vurgun.skyfit.core.data.persona.domain.model.BaseUserDetail
import com.vurgun.skyfit.core.data.persona.domain.model.UserAccountType
import com.vurgun.skyfit.core.data.persona.data.model.SelectUserTypeResponse

interface UserRepository {
    suspend fun getRegisteredAccountTypes(): Result<List<UserAccountType>>
    suspend fun selectUserType(typeId: Int): Result<SelectUserTypeResponse>
    suspend fun getSecureUserDetails(): Result<BaseUserDetail>
}