package com.vurgun.skyfit.data.core.domain.repository

import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail

interface UserRepository {
    suspend fun getUserDetails(): Result<UserDetail>
    suspend fun getUserTypes(): Result<List<UserAccountType>>
    suspend fun selectUserType(typeId: Int): Result<Boolean>
}