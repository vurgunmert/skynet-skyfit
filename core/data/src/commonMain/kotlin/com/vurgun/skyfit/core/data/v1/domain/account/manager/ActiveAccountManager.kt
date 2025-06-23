package com.vurgun.skyfit.core.data.v1.domain.account.manager

import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import kotlinx.coroutines.flow.StateFlow

//TODO: probably rename better
interface ActiveAccountManager {
    suspend fun getActiveUser(forceRefresh: Boolean = false): Result<Account>
    suspend fun updateUserType(userTypeId: Int): Result<Unit>
    suspend fun logout()

    val accountTypes: StateFlow<List<AccountType>>
    val account: StateFlow<Account?>
    val accountRole: StateFlow<AccountRole>
    suspend fun getAccountTypes(): List<AccountType>
    val accountType: StateFlow<AccountType?>
}