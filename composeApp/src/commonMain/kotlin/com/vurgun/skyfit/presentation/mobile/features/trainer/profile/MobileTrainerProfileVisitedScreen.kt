package com.vurgun.skyfit.presentation.mobile.features.trainer.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileTrainerProfileVisitedScreen(navigator: Navigator) {

    val scrollState = rememberScrollState()
    val showPosts: Boolean = false
    val followed: Boolean = false
    val specialities: List<Any> = emptyList()
    val privateClasses: List<Any> = emptyList()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Box {
                Column {
                    Box {
                        MobileTrainerProfileVisitedScreenBackgroundImageComponent()
                        MobileTrainerProfileVisitedScreenInfoCardComponent()
                    }

                    MobileTrainerProfileVisitedScreenActionsComponent()
                }

                MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack = { navigator.popBackStack() })
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (showPosts) {
                MobileTrainerProfileVisitedScreenPostsComponent()
            } else {

                MobileTrainerProfileVisitedScreenSpecialitiesComponent()

                Spacer(Modifier.height(16.dp))

                MobileTrainerProfileVisitedScreenPrivateClassesComponent()
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 24.dp)) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = onClickBack)
        )
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenBackgroundImageComponent() {
    TodoBox("MobileTrainerProfileVisitedScreenBackgroundImageComponent", Modifier.size(430.dp, 180.dp))
}

@Composable
private fun MobileTrainerProfileVisitedScreenInfoCardComponent() {
    TodoBox("MobileTrainerProfileVisitedScreenInfoCardComponent", Modifier.size(398.dp, 380.dp))
}

@Composable
private fun MobileTrainerProfileVisitedScreenPostsComponent() {
    TodoBox("MobileTrainerProfileVisitedScreenPostsComponent", Modifier.size(398.dp, 857.dp))
}

@Composable
private fun MobileTrainerProfileVisitedScreenActionsComponent() {
    TodoBox("MobileTrainerProfileVisitedScreenActionsComponent", Modifier.size(398.dp, 56.dp))
}

@Composable
private fun MobileTrainerProfileVisitedScreenSpecialitiesComponent() {
    TodoBox("MobileTrainerProfileVisitedScreenSpecialitiesComponent", Modifier.size(398.dp, 196.dp))
}

@Composable
private fun MobileTrainerProfileVisitedScreenPrivateClassesComponent() {
    TodoBox("MobileTrainerProfileVisitedScreenPrivateClassesComponent", Modifier.size(398.dp, 584.dp))
}