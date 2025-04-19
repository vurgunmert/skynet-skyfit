package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserRole
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
