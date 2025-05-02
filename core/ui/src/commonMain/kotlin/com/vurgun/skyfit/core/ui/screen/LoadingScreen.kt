package com.vurgun.skyfit.core.ui.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent

object LoadingScreen : Screen {
    @Composable
    override fun Content() {
        FullScreenLoaderContent()
    }
}
