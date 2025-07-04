package com.vurgun.skyfit.health.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.exercise.MobileExerciseWorkoutItemComponent
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.RatingButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_calories
import fiwe.core.ui.generated.resources.ic_chevron_left
import fiwe.core.ui.generated.resources.ic_clock

@Composable
fun MobileUserExerciseDetailScreen(
    goToBack: () -> Unit,
    goToExerciseAction: () -> Unit
) {

    SkyFitScaffold(
        bottomBar = {
            SkyFitButtonComponent(
                modifier = Modifier.padding(24.dp).fillMaxWidth(),
                text = "Başla",
                onClick = goToExerciseAction,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest
            )
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            val width = maxWidth
            val imageHeight = width * 0.686f

            NetworkImage(
                imageUrl = "https://ik.imagekit.io/skynet2skyfit/exercise_detail_header_fake.png?updatedAt=1739507110520",
                modifier = Modifier.size(width, imageHeight)
            )

            val contentTopPadding = imageHeight * 9 / 10

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 22.dp)
            ) {
                Spacer(Modifier.height(contentTopPadding))
                MobileExerciseActionDetailScreenInfoComponent()
                Spacer(Modifier.height(16.dp))
                MobileExerciseActionDetailScreenCategoryComponent()
                Spacer(Modifier.height(16.dp))
                MobileExerciseActionDetailScreenWorkoutEditorialComponent()
                Spacer(Modifier.height(16.dp))
                MobileExerciseActionDetailScreenWorkoutInsightsComponent()
                Spacer(Modifier.height(124.dp))
            }

            MobileExerciseActionDetailScreenToolbarComponent(
                modifier = Modifier.align(Alignment.TopStart),
                onClickBack = goToBack
            )
        }
    }
}

@Composable
private fun MobileExerciseActionDetailScreenToolbarComponent(
    modifier: Modifier,
    onClickBack: () -> Unit
) {
    Row(modifier.fillMaxWidth().height(40.dp).padding(horizontal = 24.dp, vertical = 8.dp)) {
        Image(
            painter = painterResource(Res.drawable.ic_chevron_left),
            contentDescription = null,
            modifier = Modifier.size(24.dp).clickable(onClick = onClickBack)
        )
    }
}

@Composable
private fun MobileExerciseActionDetailScreenInfoComponent() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(24.dp))
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.ic_calories),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = "500 kcal",
                    style = SkyFitTypography.bodyMediumSemibold.copy(color = SkyFitColor.text.default)
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Yakılacak Enerji",
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
            )
        }

        VerticalDivider(Modifier.height(52.dp))

        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(
                    painter = painterResource(Res.drawable.ic_clock),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = "30 dk",
                    style = SkyFitTypography.bodyMediumSemibold.copy(color = SkyFitColor.text.default)
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Süre",
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
            )
        }
    }
}

@Composable
private fun MobileExerciseActionDetailScreenCategoryComponent() {
    Row(
        Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Beginner",
            onClick = { },
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Micro
        )

        SkyFitButtonComponent(
            modifier = Modifier.wrapContentWidth(), text = "Abs",
            onClick = { },
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Micro
        )
        Spacer(Modifier.weight(1f))
        RatingButton(4.8f, Modifier.wrapContentWidth())
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
