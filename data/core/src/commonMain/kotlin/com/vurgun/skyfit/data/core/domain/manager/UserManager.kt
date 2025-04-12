package com.vurgun.skyfit.data.core.domain.manager

import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.model.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UserManager {
    suspend fun getActiveUser(forceRefresh: Boolean = false): Result<UserDetail>
    fun observeUser(): Flow<UserDetail?>
    fun observeUserRole(): Flow<UserRole>
    val user: StateFlow<UserDetail?>
    val userRole: Flow<UserRole>
}
