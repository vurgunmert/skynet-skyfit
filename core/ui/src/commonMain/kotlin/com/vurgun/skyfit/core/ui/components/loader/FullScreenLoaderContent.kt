package com.vurgun.skyfit.core.ui.components.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun FullScreenLoaderContent() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularLoader()
    }
}