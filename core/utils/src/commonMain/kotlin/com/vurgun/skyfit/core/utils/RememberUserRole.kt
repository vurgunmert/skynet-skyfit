package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserRole
import org.koin.compose.koinInject

@Composable
fun rememberUserRole(): UserRole {
    val userManager: ActiveAccountManager = koinInject()
    val userRole: UserRole by userManager.userRole.collectAsState()

    return userRole
}

@Composable
fun rememberAccount(): Account? {
    val userManager: ActiveAccountManager = koinInject()
    val state = userManager.user.collectAsState()
    return state.value
}

@Composable
fun rememberUserAccount(): UserAccount? {
    val userManager: ActiveAccountManager = koinInject()
    val state = userManager.user.collectAsState()
    return state.value as? UserAccount
}