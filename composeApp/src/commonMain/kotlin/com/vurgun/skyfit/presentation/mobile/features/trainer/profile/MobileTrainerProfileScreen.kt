package com.vurgun.skyfit.presentation.mobile.features.trainer.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerProfileScreen(navigator: Navigator) {

    val scrollState = rememberScrollState()
    val showPosts: Boolean = false
    val specialities: List<Any> = emptyList()
    val privateClasses: List<Any> = emptyList()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {

            Column {
                Box {
                    MobileTrainerProfileBackgroundImageComponent()
                    MobileTrainerProfileInfoCardComponent()
                }

                MobileTrainerProfileActionsComponent()

                if (showPosts) {
                    MobileTrainerProfilePostInputComponent()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (showPosts) {
                MobileTrainerProfilePostsComponent()
            } else {

                if (specialities.isEmpty()) {
                    MobileTrainerProfileSpecialitiesEmptyComponent()
                } else {
                    MobileTrainerProfileSpecialitiesComponent()
                }

                if (privateClasses.isEmpty()) {
                    MobileTrainerProfilePrivateClassesEmptyComponent()
                } else {
                    MobileTrainerProfilePrivateClassesComponent()
                }
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileBackgroundImageComponent() {
    TodoBox("MobileTrainerProfileBackgroundImageComponent", Modifier.size(430.dp, 180.dp))
}

@Composable
private fun MobileTrainerProfileInfoCardComponent() {
    TodoBox("MobileTrainerProfileInfoCardComponent", Modifier.size(398.dp, 230.dp))
}

@Composable
private fun MobileTrainerProfilePostInputComponent() {
    TodoBox("MobileTrainerProfilePostInputComponent", Modifier.size(398.dp, 64.dp))
}

@Composable
private fun MobileTrainerProfilePostsComponent() {
    TodoBox("MobileTrainerProfilePostsComponent", Modifier.size(398.dp, 857.dp))
}

@Composable
private fun MobileTrainerProfileActionsComponent() {
    TodoBox("MobileTrainerProfileActionsComponent", Modifier.size(398.dp, 56.dp))
}

@Composable
private fun MobileTrainerProfileSpecialitiesComponent() {
    TodoBox("MobileTrainerProfileSpecialitiesComponent", Modifier.size(398.dp, 196.dp))
}

@Composable
private fun MobileTrainerProfileSpecialitiesEmptyComponent() {
    TodoBox("MobileTrainerProfileSpecialitiesEmptyComponent", Modifier.size(398.dp, 188.dp))
}


@Composable
private fun MobileTrainerProfilePrivateClassesComponent() {
    TodoBox("MobileTrainerProfilePrivateClassesComponent", Modifier.size(398.dp, 584.dp))
}

@Composable
private fun MobileTrainerProfilePrivateClassesEmptyComponent() {
    TodoBox("MobileTrainerProfilePrivateClassesEmptyComponent", Modifier.size(398.dp, 188.dp))
}

