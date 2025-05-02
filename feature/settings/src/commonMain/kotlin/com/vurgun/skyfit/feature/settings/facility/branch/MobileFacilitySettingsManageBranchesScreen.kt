package com.vurgun.skyfit.feature.settings.facility.branch

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader

class FacilitySettingsManageBranchesScreen : Screen {

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
            SkyFitScreenHeader("Şubeler", onClickBack = goToBack)
        }
    ) {

    }
}