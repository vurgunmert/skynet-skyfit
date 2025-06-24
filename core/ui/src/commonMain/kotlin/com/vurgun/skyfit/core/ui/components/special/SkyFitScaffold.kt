package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.styling.LocalDimensions
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

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
fun SkyPageScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        content = content,
        topBar = topBar,
        bottomBar = bottomBar,
        backgroundColor = SkyFitColor.background.default,
        modifier = modifier
            .fillMaxSize()
            .background(color = SkyFitColor.background.default)
            .systemBarsPadding()
    )
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

