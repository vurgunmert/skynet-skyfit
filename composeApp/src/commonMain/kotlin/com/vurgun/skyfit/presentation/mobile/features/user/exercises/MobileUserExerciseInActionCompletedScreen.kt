package com.vurgun.skyfit.presentation.mobile.features.user.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MobileUserExerciseInActionCompletedScreen(navigator: Navigator) {

    SkyFitScaffold {

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val width = maxWidth
            val imageHeight = width * 0.686f

            Image(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier.size(width, imageHeight)
            )

            val contentTopPadding = imageHeight * 9 / 10

            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(Modifier.height(contentTopPadding))
                MobileUserExerciseInActionCompletedScreenInfoComponent()
                Spacer(Modifier.height(16.dp))
                FlowRow {
                    MobileUserExerciseInActionCompletedScreenRateItemComponent()
                    MobileUserExerciseInActionCompletedScreenRateItemComponent()
                    MobileUserExerciseInActionCompletedScreenRateItemComponent()
                    MobileUserExerciseInActionCompletedScreenRateItemComponent()
                }
                Spacer(Modifier.weight(1f))
                MobileUserExerciseInActionCompletedScreenActionComponent(onClick = {
                    navigator.jumpAndTakeover(SkyFitNavigationRoute.UserExerciseDetail, SkyFitNavigationRoute.Dashboard)
                })
            }
        }
    }
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenInfoComponent() {
    Box(Modifier.fillMaxWidth().height(92.dp).background(SkyFitColor.background.fillTransparentHover, RoundedCornerShape(24.dp))) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "500 kcal",
                    style = SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Yakılacak Enerji",
                    style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
                )
            }
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "30 dk",
                    style = SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Süre",
                    style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
                )
            }
        }
    }
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenRateItemComponent() {
    Column(
        modifier = Modifier
            .background(SkyFitColor.background.surfaceSecondary)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Chip",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(96.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text("Mükemmel", style = SkyFitTypography.bodyMediumRegular)
    }
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenActionComponent(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(32.dp)) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Tamamla",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
    }
}