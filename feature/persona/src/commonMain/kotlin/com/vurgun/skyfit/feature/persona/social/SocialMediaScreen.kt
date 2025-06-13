package com.vurgun.skyfit.feature.persona.social

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

class SocialMediaScreen : Screen {

    override val key: ScreenKey
        get() = "social"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SocialMediaViewModel>()
        val windowSize = LocalWindowSize.current

        if (windowSize == WindowSize.EXPANDED) {
            SocialMediaExpanded(
                onClickNewPost = { navigator.push(SharedScreen.CreatePost) },
                viewModel = viewModel
            )
        } else {
            SocialMediaCompact(
                onClickNewPost = { navigator.push(SharedScreen.CreatePost) },
                viewModel = viewModel
            )
        }
    }
}