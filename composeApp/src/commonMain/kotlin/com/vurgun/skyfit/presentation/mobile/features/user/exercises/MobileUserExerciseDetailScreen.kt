package com.vurgun.skyfit.presentation.mobile.features.user.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.vurgun.skyfit.presentation.shared.components.SkyFitSelectableTextButton
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserExerciseDetailScreen(navigator: Navigator) {

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(contentTopPadding))
                MobileExerciseActionDetailScreenInfoComponent()
                Spacer(Modifier.height(16.dp))
                MobileExerciseActionDetailScreenCategoryComponent()
                Spacer(Modifier.height(16.dp))
                MobileExerciseActionDetailScreenWorkoutEditorialComponent()
                Spacer(Modifier.height(16.dp))
                MobileExerciseActionDetailScreenWorkoutInsightsComponent()
            }
        }
    }
}

@Composable
private fun MobileExerciseActionDetailScreenToolbarComponent(onClickBack: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(40.dp).padding(horizontal = 24.dp, vertical = 8.dp)) {
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp).clickable(onClick = onClickBack)
        )
    }
}

@Composable
private fun MobileExerciseActionDetailScreenInfoComponent() {
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
private fun MobileExerciseActionDetailScreenCategoryComponent() {
    Row(Modifier.fillMaxWidth().height(56.dp)) {
        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Beginner",
            onClick = {  },
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Abs",
            onClick = {  },
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "4.8*",
            onClick = {  },
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileExerciseActionDetailScreenWorkoutEditorialComponent() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Ectomorph Beginner", style = SkyFitTypography.heading5)
        Text(
            text = "This program was created specifically for the beginner ectomorph body type.",
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
        )
    }
}

@Composable
private fun MobileExerciseActionDetailScreenWorkoutInsightsComponent() {
    var items = 0..6
    items.forEach {
        MobileExerciseWorkoutItemComponent(Modifier.fillMaxWidth().padding(vertical = 6.dp))
        Spacer(Modifier.height(16.dp))
    }
}
