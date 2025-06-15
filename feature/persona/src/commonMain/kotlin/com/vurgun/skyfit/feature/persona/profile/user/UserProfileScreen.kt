package com.vurgun.skyfit.feature.persona.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileViewModel

class UserProfileScreen(private val userId: Int? = null) : Screen {

    override val key: ScreenKey
        get() = "profile:user:screen:$userId"

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<UserProfileViewModel>()

        when (windowSize) {
            WindowSize.EXPANDED -> UserProfileExpanded(viewModel)
            else -> UserProfileCompact(viewModel)
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(userId)
        }
    }
}