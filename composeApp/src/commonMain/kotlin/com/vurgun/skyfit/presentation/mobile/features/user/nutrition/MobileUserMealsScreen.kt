package com.vurgun.skyfit.presentation.mobile.features.user.nutrition


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
fun MobileUserMealsScreen(rootNavigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(22.dp))
            MobileDashboardNutritionScreenDayOfWeekComponent()
            Spacer(Modifier.height(22.dp))
            MobileDashboardNutritionScreenStatisticsComponent()
            Spacer(Modifier.height(32.dp))
            MobileDashboardNutritionScreenMealsComponent()

        }
    }
}


@Composable
private fun MobileDashboardNutritionScreenDayOfWeekComponent() {
    TodoBox("MobileDashboardNutritionScreenDayOfWeekComponent", Modifier.size(382.dp, 84.dp))
}

@Composable
private fun MobileDashboardNutritionScreenStatisticsComponent() {
    TodoBox("MobileDashboardNutritionScreenStatisticsComponent", Modifier.size(382.dp, 220.dp))
}

@Composable
private fun MobileDashboardNutritionScreenMealsComponent() {
    TodoBox("MobileDashboardNutritionScreenMealsComponent", Modifier.size(382.dp, 452.dp))
}