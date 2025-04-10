package com.vurgun.skyfit.feature.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonState
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun MobileUserExerciseInActionCompletedScreen(
    goToDashboard: () -> Unit
) {

    SkyFitScaffold {

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val width = maxWidth
            val imageHeight = width * 0.686f

            NetworkImage(
                imageUrl = "https://ik.imagekit.io/skynet2skyfit/exercise_detail_header_fake.png?updatedAt=1739507110520",
                modifier = Modifier.size(width, imageHeight)
            )

            val contentTopPadding = imageHeight * 9 / 10

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Spacer(Modifier.height(contentTopPadding))
                MobileUserExerciseInActionCompletedScreenInfoComponent()
                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth()) {
                    MobileUserExerciseInActionCompletedScreenRateItemComponent(Modifier.weight(1f))
                    Spacer(Modifier.width(16.dp))
                    MobileUserExerciseInActionCompletedScreenRateItemComponent(Modifier.weight(1f))
                    Spacer(Modifier.width(16.dp))
                    MobileUserExerciseInActionCompletedScreenRateItemComponent(Modifier.weight(1f))
                }
                Spacer(Modifier.weight(1f))
                MobileUserExerciseInActionCompletedScreenActionComponent(onClick = goToDashboard)
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
private fun MobileUserExerciseInActionCompletedScreenRateItemComponent(modifier: Modifier) {
    Column(
        modifier = modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            imageUrl = "https://ik.imagekit.io/skynet2skyfit/image_emoji_sunglasses.png?updatedAt=1739512659847",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(Modifier.height(8.dp))
        Text("Mükemmel", style = SkyFitTypography.bodyMediumRegular)
        Spacer(Modifier.height(8.dp))
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