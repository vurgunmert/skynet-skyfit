package com.vurgun.skyfit.data.core.domain.manager

import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.model.UserRole
import kotlinx.coroutines.flow.StateFlow

interface UserManager {
    suspend fun getActiveUser(forceRefresh: Boolean = false): Result<UserDetail>
    suspend fun updateUserType(userTypeId: Int)

    val accountTypes: StateFlow<List<UserAccountType>>
    val user: StateFlow<UserDetail?>
    val userRole: StateFlow<UserRole>
}
