package com.vurgun.skyfit.presentation.mobile.features.user.nutrition


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserMealDetailAddPhotoScreen(rootNavigator: Navigator) {

    SkyFitScaffold {
        Box(Modifier.fillMaxSize()) {

            MobileMealDetailAddPhotoScreenGuideComponent()

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(24.dp))
                MobileMealDetailAddPhotoScreenToolbarComponent { rootNavigator.popBackStack() }
                Spacer(Modifier.weight(1f))
                MobileMealDetailAddPhotoScreenMediaActionsComponent()
            }
        }
    }
}


@Composable
private fun MobileMealDetailAddPhotoScreenToolbarComponent(onClick: () -> Unit) {
    TodoBox("MobileMealDetailAddPhotoScreenToolbarComponent", Modifier.size(430.dp, 64.dp).clickable(onClick = onClick))
}

@Composable
private fun MobileMealDetailAddPhotoScreenGuideComponent() {
    TodoBox("MobileMealDetailAddPhotoScreenGuideComponent", Modifier.fillMaxSize())
}

@Composable
private fun MobileMealDetailAddPhotoScreenMediaActionsComponent() {
    TodoBox("MobileMealDetailAddScreenSaveActionComponent", Modifier.size(352.dp, 112.dp))
}