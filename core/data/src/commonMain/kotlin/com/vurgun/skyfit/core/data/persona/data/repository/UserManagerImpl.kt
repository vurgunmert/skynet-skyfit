package com.vurgun.skyfit.core.data.persona.data.repository

import com.vurgun.skyfit.core.data.persona.domain.model.BaseUserDetail
import com.vurgun.skyfit.core.data.persona.domain.model.UserAccountType
import com.vurgun.skyfit.core.data.persona.domain.model.UserRole
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.persona.domain.repository.UserRepository
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserManagerImpl(
    appScope: CoroutineScope,
    private val storage: Storage,
    private val repository: UserRepository,
    private val tokenManager: TokenManager
) : UserManager {

    private val userFlow = MutableStateFlow<BaseUserDetail?>(null)
    override val user = userFlow.asStateFlow()

    // Derived role state
    override val userRole: StateFlow<UserRole> = userFlow
        .map { it?.userRole ?: UserRole.Guest }
        .stateIn(appScope, SharingStarted.Eagerly, UserRole.Guest)

    private val _accountTypes = MutableStateFlow<List<UserAccountType>>(emptyList())
    override val accountTypes: StateFlow<List<UserAccountType>> = _accountTypes

    override suspend fun getAccountTypes(): List<UserAccountType> {
        if (_accountTypes.value.isNotEmpty()) return _accountTypes.value

        return repository.getRegisteredAccountTypes()
            .onSuccess { _accountTypes.value = it }
            .getOrElse {
                println("❌ Could not fetch user account types: ${it.message}")
                emptyList()
            }
    }

    override suspend fun getActiveUser(forceRefresh: Boolean): Result<BaseUserDetail> {
        if (!forceRefresh && user.value != null) {
            return Result.success(user.value!!)
        }

        val result = repository.getSecureUserDetails()

        return result
            .onSuccess { userFlow.value = it }
            .onFailure { println("❌ Failed to get user securely: $it") }
    }

    override suspend fun updateUserType(userTypeId: Int): Result<Unit> {
        return runCatching {
            repository.selectUserType(userTypeId).getOrThrow()
            val updatedUser = getActiveUser(forceRefresh = true).getOrThrow()
            userFlow.value = updatedUser
        }
    }

    override suspend fun logout() {
        println("🚪 Logging out: Clearing user and accountTypes")
        tokenManager.clear()
        storage.clearAll()
        userFlow.value = null
        _accountTypes.value = emptyList()
    }
}
