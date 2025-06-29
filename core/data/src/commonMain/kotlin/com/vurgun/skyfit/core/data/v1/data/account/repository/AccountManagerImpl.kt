package com.vurgun.skyfit.core.data.v1.data.account.repository

import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.account.repository.AccountRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AccountManagerImpl(
    appScope: CoroutineScope,
    private val storage: Storage,
    private val repository: AccountRepository,
    private val tokenManager: TokenManager
) : ActiveAccountManager {

    private val userFlow = MutableStateFlow<Account?>(null)
    override val account = userFlow.asStateFlow()

    // Derived role state
    override val accountRole: StateFlow<AccountRole> = userFlow
        .map { it?.accountRole ?: AccountRole.Guest }
        .stateIn(appScope, SharingStarted.Companion.Eagerly, AccountRole.Guest)

    private val _accountTypes = MutableStateFlow<List<AccountType>>(emptyList())
    override val accountTypes: StateFlow<List<AccountType>> = _accountTypes

    override val accountType: StateFlow<AccountType?> = combine(
        accountRole,
        accountTypes
    ) { role, types ->
        types.firstOrNull { it.typeId == role.typeId }
    }.stateIn(appScope, SharingStarted.Eagerly, null)

    init {
        appScope.launch {
            runCatching { getAccountTypes() }.onFailure { error ->
                print(error)
            }
        }
    }

    override suspend fun getAccountTypes(): List<AccountType> {
        if (_accountTypes.value.isNotEmpty()) return _accountTypes.value

        tokenManager.waitUntilTokenReady()

        return repository.getRegisteredAccountTypes()
            .onSuccess { _accountTypes.value = it }
            .getOrElse {
                println("❌ Could not fetch user account types: ${it.message}")
                emptyList()
            }
    }

    override suspend fun getActiveUser(forceRefresh: Boolean): Result<Account> {
        if (!forceRefresh && account.value != null) {
            return Result.success(account.value!!)
        }

        val result = repository.getAccountDetails().onSuccess {
            userFlow.value = it
            getAccountTypes()
        }
        return result
    }

    override suspend fun updateUserType(userTypeId: Int): Result<Unit> {
        return runCatching {
            repository.selectActiveAccountType(userTypeId).getOrThrow()
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