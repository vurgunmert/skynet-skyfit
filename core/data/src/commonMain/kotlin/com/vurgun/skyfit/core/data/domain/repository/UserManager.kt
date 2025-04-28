package com.vurgun.skyfit.core.data.domain.repository

import com.vurgun.skyfit.core.data.domain.model.BaseUserDetail
import com.vurgun.skyfit.core.data.domain.model.UserAccountType
import com.vurgun.skyfit.core.data.domain.model.UserRole
import kotlinx.coroutines.flow.StateFlow

interface UserManager {
    suspend fun getActiveUser(forceRefresh: Boolean = false): Result<BaseUserDetail>
    suspend fun updateUserType(userTypeId: Int): Result<Unit>
    suspend fun logout()

    val accountTypes: StateFlow<List<UserAccountType>>
    val user: StateFlow<BaseUserDetail?>
    val userRole: StateFlow<UserRole>
    suspend fun getAccountTypes(): List<UserAccountType>
}
