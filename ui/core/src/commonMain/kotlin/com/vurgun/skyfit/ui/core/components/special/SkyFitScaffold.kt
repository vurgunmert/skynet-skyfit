package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.ui.core.styling.LocalDimensions
import com.vurgun.skyfit.ui.core.styling.SkyFitColor

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


@Composable
fun SkyFitMobileScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SkyFitColor.background.default)
            .windowInsetsPadding(WindowInsets.systemBars), //TODO CHECK INSETS
        contentAlignment = Alignment.TopCenter
    ) {
        Scaffold(
            modifier = modifier
                .widthIn(max = LocalDimensions.current.mobileMaxWidth, min = LocalDimensions.current.mobileMinWidth)
                .fillMaxHeight(),
            backgroundColor = SkyFitColor.background.default,
            content = content,
            topBar = topBar,
            bottomBar = bottomBar
        )
    }
}


@Composable
fun SkyFitMobileFillScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = SkyFitColor.background.default),
        contentAlignment = Alignment.TopCenter
    ) {
        Scaffold(
            modifier = modifier
                .widthIn(max = LocalDimensions.current.mobileMaxWidth, min = LocalDimensions.current.mobileMinWidth)
                .fillMaxHeight(),
            backgroundColor = SkyFitColor.background.default,
            content = content,
            topBar = topBar,
            bottomBar = bottomBar
        )
    }
}

