package com.vurgun.skyfit.core.domain.repository

import com.vurgun.skyfit.core.domain.models.UserDetail

interface UserRepository {
    suspend fun getUserDetails(): Result<UserDetail>
}