package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor

@Composable
fun SkyFitScaffold(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(backgroundColor = SkyFitColor.background.default, content = content)
}

