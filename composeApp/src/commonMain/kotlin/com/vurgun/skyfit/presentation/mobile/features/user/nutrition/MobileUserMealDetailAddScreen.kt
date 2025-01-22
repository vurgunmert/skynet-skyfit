package com.vurgun.skyfit.presentation.mobile.features.user.nutrition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileUserMealDetailAddScreen(rootNavigator: Navigator) {

    val userMealDetailAddViewModel: Any = 1
    val detailNavigator = rememberNavigator()

    val onComplete: () -> Unit = {
        rootNavigator.popBackStack()
    }

    NavHost(
        navigator = detailNavigator,
        initialRoute = SkyFitNavigationRoute.UserMealDetailAdd.route
    ) {
        scene(SkyFitNavigationRoute.UserMealDetailAdd.route) {
            MobileUserMealDetailAddInputScreen(
                onAddImage = { detailNavigator.jumpAndStay(SkyFitNavigationRoute.UserMealDetailAddPhoto) },
                onSave = { /*TODO: logic */ }
            )
        }
        scene(SkyFitNavigationRoute.UserMealDetailAddPhoto.route) {
            MobileUserMealDetailAddPhotoScreen(detailNavigator)
        }
    }
}

@Composable
private fun MobileUserMealDetailAddInputScreen(onAddImage: () -> Unit, onSave: () -> Unit) {

    val showSave: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileMealDetailAddScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            MobileMealDetailAddScreenInputComponent()
            Spacer(Modifier.height(12.dp))
            MobileMealDetailAddScreenAddPhotoActionComponent(onAddImage)
            Spacer(Modifier.height(16.dp))
            if (showSave) {
                MobileMealDetailAddScreenSaveActionComponent()
            }
        }
    }
}


@Composable
private fun MobileMealDetailAddScreenToolbarComponent() {
    TodoBox("MobileMealDetailAddScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileMealDetailAddScreenInputComponent() {
    TodoBox("MobileMealDetailAddScreenInputComponent", Modifier.size(382.dp, 428.dp))
}

@Composable
private fun MobileMealDetailAddScreenAddPhotoActionComponent(onClick: () -> Unit) {
    TodoBox("MobileMealDetailAddScreenAddPhotoActionComponent", Modifier.size(382.dp, 65.dp).clickable(onClick = onClick))
}

@Composable
private fun MobileMealDetailAddScreenSaveActionComponent() {
    TodoBox("MobileMealDetailAddScreenSaveActionComponent", Modifier.size(382.dp, 48.dp))
}