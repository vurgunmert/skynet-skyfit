package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.data.core.storage.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserManagerImpl(
    private val storage: Storage,
    private val repository: UserRepository
) : UserManager {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val userFlow = MutableStateFlow<BaseUserDetail?>(null)
    override val user = userFlow.asStateFlow()

    // Derived role state
    override val userRole: StateFlow<UserRole> = userFlow
        .map { it?.userRole ?: UserRole.Guest }
        .stateIn(appScope, SharingStarted.Eagerly, UserRole.Guest)

    private val _accountTypes = MutableStateFlow<List<UserAccountType>>(emptyList())
    override val accountTypes: StateFlow<List<UserAccountType>> = _accountTypes

    override suspend fun getActiveUser(forceRefresh: Boolean): Result<BaseUserDetail> {
        // Return cached user unless forced to refresh
        if (!forceRefresh && user.value != null) {
            println("✅ Returning cached user: ${user.value}")
            return Result.success(user.value!!)
        }
        val result = repository.getSecureUserDetails()

        result.onSuccess { userDetail ->
            userFlow.value = userDetail
        }.onFailure {
            println("❌ Failed to get user securely: $it")
        }
        return result
    }

    override suspend fun updateUserType(userTypeId: Int) {
        val selection = repository.selectUserType(userTypeId)
        if (selection.isSuccess) {
            val result = getActiveUser(forceRefresh = true)
            result.getOrNull()?.let {
                userFlow.value = it // ensure flow triggers
            } ?: println("Failed to fetch user after type change")
        } else {
            println("User type selection failed.")
        }
    }

    override suspend fun logout() {
        println("🚪 Logging out: Clearing user and accountTypes")
        storage.clearAll()
        userFlow.value = null
        _accountTypes.value = emptyList()
    }
}
