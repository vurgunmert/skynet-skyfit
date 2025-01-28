package com.vurgun.skyfit.presentation.mobile.features.user.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserExerciseInActionScreenStep {
    SESSION,
    BREAK,
    PROGRAM,
    TROPHY,
    COMPLETE
}

@Composable
fun MobileUserExerciseInActionScreen(navigator: Navigator) {

    val showToolbar: Boolean = false
    var activePage by remember { mutableStateOf(MobileUserExerciseInActionScreenStep.TROPHY) }

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            when (activePage) {
                MobileUserExerciseInActionScreenStep.SESSION -> {
                    if (showToolbar) {
                        MobileUserExerciseInActionScreenToolbarComponent()
                    }
                    Spacer(Modifier.weight(1f))
                    MobileUserExerciseInActionScreenActionsComponent()
                }

                MobileUserExerciseInActionScreenStep.BREAK -> {
                    MobileUserExerciseInActionScreenToolbarComponent()
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenBreakComponent()
                }

                MobileUserExerciseInActionScreenStep.PROGRAM -> {

                    MobileUserExerciseInActionScreenToolbarComponent()
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenProgramComponent()
                }

                MobileUserExerciseInActionScreenStep.TROPHY -> {
                    MobileUserExerciseInActionScreenToolbarComponent()
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenTrophyComponent(
                        onClickTrophy = {
                            navigator.jumpAndTakeover(SkyFitNavigationRoute.Dashboard, SkyFitNavigationRoute.UserTrophies)
                        },
                        onClickNext = {
                            activePage = MobileUserExerciseInActionScreenStep.COMPLETE
                        }
                    )
                }

                MobileUserExerciseInActionScreenStep.COMPLETE -> {
                    navigator.jumpAndTakeover(
                        SkyFitNavigationRoute.UserExerciseInAction,
                        SkyFitNavigationRoute.UserExerciseInActionComplete
                    )
                }
            }

        }
    }
}

@Composable
private fun MobileUserExerciseInActionScreenToolbarComponent() {
    TodoBox("MobileUserExerciseInActionScreenToolbarComponent", Modifier.size(430.dp, 56.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenBreakComponent() {
    TodoBox("MobileUserExerciseInActionScreenBreakComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenProgramComponent() {
    TodoBox("MobileUserExerciseInActionScreenProgramComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenTrophyComponent(
    onClickTrophy: () -> Unit,
    onClickNext: () -> Unit
) {
    Box(
        Modifier.padding(horizontal = 24.dp, vertical = 18.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceHover, RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            Modifier.fillMaxSize().padding(horizontal = 76.dp, vertical = 112.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .size(148.dp, 203.dp)
                    .background(SkyFitColor.background.surfaceDisabled)
            )
            Spacer(Modifier.height(36.dp))
            Text(
                text = "3 Günlük Seri Tamamlandı!",
                textAlign = TextAlign.Center,
                style = SkyFitTypography.heading3,
                modifier = Modifier.width(230.dp)
            )
            Spacer(Modifier.height(36.dp))
            SkyFitButtonComponent(
                Modifier.fillMaxWidth(), text = "Ödüller",
                onClick = onClickTrophy,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Large,
                initialState = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.logo_skyfit)
            )
            Spacer(Modifier.height(16.dp))
            SkyFitButtonComponent(
                Modifier.fillMaxWidth(), text = "Ileri",
                onClick = onClickNext,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                initialState = ButtonState.Rest
            )
        }
    }
}

@Composable
private fun MobileUserExerciseInActionScreenActionsComponent() {
    TodoBox("MobileUserExerciseInActionScreenActionsComponent", Modifier.size(382.dp, 95.dp))
}