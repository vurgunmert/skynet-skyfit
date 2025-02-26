package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.ui.resources.SkyFitColor

@Composable
fun SkyFitScaffold(
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(backgroundColor = SkyFitColor.background.default, content = content, topBar = topBar, bottomBar = bottomBar)
}

