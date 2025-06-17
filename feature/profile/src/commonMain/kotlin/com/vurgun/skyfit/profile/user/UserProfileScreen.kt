package com.vurgun.skyfit.profile.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.profile.user.owner.UserProfileViewModel

class UserProfileScreen(private val userId: Int? = null) : Screen {

    override val key: ScreenKey
        get() = "profile:user:screen:$userId"

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<UserProfileViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadData(userId)
        }

        when (windowSize) {
            WindowSize.EXPANDED -> UserProfileExpanded(viewModel)
            else -> UserProfileCompact(viewModel)
        }
    }
}