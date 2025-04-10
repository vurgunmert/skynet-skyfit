package com.vurgun.skyfit.data.core.domain.manager

import com.vurgun.skyfit.data.core.domain.model.UserDetail
import kotlinx.coroutines.flow.Flow

interface UserManager {
    suspend fun getActiveUser(forceRefresh: Boolean = false): Result<UserDetail>
    fun observeUser(): Flow<UserDetail?>
}
