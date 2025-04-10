package com.vurgun.skyfit.data.core.domain.repository

import com.vurgun.skyfit.data.core.domain.model.UserDetail

interface UserRepository {
    suspend fun getUserDetails(): Result<UserDetail>
}