package com.vurgun.skyfit.settings.facility.branch

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar

class FacilityBranchSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileFacilitySettingsManageBranchesScreen(
            goToBack = { navigator.pop() }
        )
    }

}

@Composable
fun MobileFacilitySettingsManageBranchesScreen(
    goToBack: () -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar("Şubeler", onClickBack = goToBack)
        }
    ) {

    }
}