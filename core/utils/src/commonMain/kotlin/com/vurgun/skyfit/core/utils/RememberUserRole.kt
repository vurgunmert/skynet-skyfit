package com.vurgun.skyfit.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vurgun.skyfit.core.data.persona.domain.model.UserRole
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import org.koin.compose.koinInject

@Composable
fun rememberUserRole(): UserRole {
    val userManager: UserManager = koinInject()
    val userRole: UserRole by userManager.userRole.collectAsState()

    return userRole
}