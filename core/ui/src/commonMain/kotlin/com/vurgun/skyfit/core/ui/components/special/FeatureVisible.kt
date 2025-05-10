package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.runtime.Composable

@Composable
fun FeatureVisible(enabled: Boolean, content: @Composable () -> Unit) {
    if (enabled) content()
}