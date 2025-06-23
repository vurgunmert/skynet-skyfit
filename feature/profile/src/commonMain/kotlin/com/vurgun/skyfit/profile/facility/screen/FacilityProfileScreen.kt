package com.vurgun.skyfit.profile.facility.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

class FacilityProfileScreen(private val facilityId: Int? = null) : Screen {

    override val key: ScreenKey
        get() = "profile:facility:screen:$facilityId"

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val viewModel = koinScreenModel<FacilityProfileViewModel>()

        LaunchedEffect(Unit) {
            viewModel.loadData(facilityId)
        }

        if (windowSize == WindowSize.EXPANDED) {
            FacilityProfileExpanded(viewModel)
        } else {
            FacilityProfileCompact(viewModel)
        }
    }
}