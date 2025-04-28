package com.vurgun.skyfit.core.ui.utils

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.data.domain.model.UserRole
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen

@Composable
fun RequireRole(
    role: UserRole,
    allowed: List<UserRole>,
    fallback: @Composable () -> Unit = { UnauthorizedAccessScreen() },
    content: @Composable () -> Unit
) {
    if (allowed.contains(role)) content() else fallback()
}
