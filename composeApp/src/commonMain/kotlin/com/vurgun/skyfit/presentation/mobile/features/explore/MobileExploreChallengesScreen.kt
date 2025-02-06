package com.vurgun.skyfit.presentation.mobile.features.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileExploreChallengesScreen(rootNavigator: Navigator) {

    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SkyFitScreenHeader("Meydan Okumalaar", onClickBack = { rootNavigator.popBackStack() })
                Spacer(Modifier.height(16.dp))
                if (isSearchVisible) {
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                }
                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
            }
        }
    ) {
        Spacer(Modifier.height(30.dp))
        MobileExploreUserChallengesComponent()
        Spacer(Modifier.height(12.dp))
        MobileExploreActiveChallengesComponent()
        Spacer(Modifier.height(24.dp))
    }
}


@Composable
private fun MobileExploreUserChallengesComponent() {
    TodoBox("ExploreUserChallengesComponent", Modifier.size(382.dp, 202.dp))
}

@Composable
private fun MobileExploreActiveChallengesComponent() {
    TodoBox("ExploreActiveChallengesComponent", Modifier.size(382.dp, 588.dp))
}