package com.vurgun.skyfit.feature.persona.profile.user

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerViewModel

class UserProfileScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<UserProfileOwnerViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> UserProfileExpanded(viewModel)
            else -> UserProfileCompact(viewModel)
        }
    }
}