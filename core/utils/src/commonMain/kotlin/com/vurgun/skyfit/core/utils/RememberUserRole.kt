package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import org.koin.compose.koinInject

@Composable
fun rememberUserRole(): AccountRole {
    val userManager: ActiveAccountManager = koinInject()
    val accountRole: AccountRole by userManager.accountRole.collectAsState()

    return accountRole
}

@Composable
fun rememberAccount(): Account? {
    val userManager: ActiveAccountManager = koinInject()
    val state = userManager.account.collectAsState()
    return state.value
}

@Composable
fun rememberUserAccount(): UserAccount? {
    val userManager: ActiveAccountManager = koinInject()
    val state = userManager.account.collectAsState()
    return state.value as? UserAccount
}