package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.resources.SkyFitColor

@Composable
fun SkyFitScaffold(
    modifier: Modifier = Modifier.fillMaxSize(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        modifier = modifier,
        backgroundColor = SkyFitColor.background.default,
        content = content,
        topBar = topBar,
        bottomBar = bottomBar
    )
}

