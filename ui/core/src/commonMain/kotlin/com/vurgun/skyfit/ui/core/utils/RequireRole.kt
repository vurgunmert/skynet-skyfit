package com.vurgun.skyfit.ui.core.utils

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.ui.core.screen.UnauthorizedAccessScreen

@Composable
fun RequireRole(
    role: UserRole,
    allowed: List<UserRole>,
    fallback: @Composable () -> Unit = { UnauthorizedAccessScreen() },
    content: @Composable () -> Unit
) {
    if (allowed.contains(role)) content() else fallback()
}
