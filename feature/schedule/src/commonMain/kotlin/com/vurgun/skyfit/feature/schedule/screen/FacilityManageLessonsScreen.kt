package com.vurgun.skyfit.feature.schedule.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import com.vurgun.skyfit.core.navigation.findRootNavigator

class FacilityManageLessonsScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()

        Navigator(FacilityLessonListingScreen()) { navigator ->
            SlideTransition(navigator)
        }
    }
}