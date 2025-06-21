package com.vurgun.skyfit.onboarding.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
internal fun OnboardingStepProgressComponent(
    modifier: Modifier = Modifier.fillMaxWidth().padding(24.dp),
    totalSteps: Int = 7,
    currentStep: Int = 1,
    activeColor: Color = SkyFitColor.specialty.buttonBgRest,
    inactiveColor: Color = SkyFitColor.background.surfaceSecondary,
    stepHeight: Dp = 10.dp,
    stepSpacing: Dp = 6.dp,
) {
    BoxWithConstraints(modifier = modifier) {
        val availableWidth = maxWidth - (stepSpacing * (totalSteps - 1))
        val stepWidth = availableWidth / totalSteps

        Row(
            horizontalArrangement = Arrangement.spacedBy(stepSpacing),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            (1..totalSteps).forEach { step ->
                Box(
                    modifier = Modifier
                        .width(stepWidth)
                        .height(stepHeight)
                        .background(
                            color = if (step <= currentStep) activeColor else inactiveColor,
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}

@Composable
internal fun OnboardingTitleGroupComponent(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = SkyFitTypography.heading3
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = subtitle,
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
internal fun OnboardingActionGroupComponent(
    onClickContinue: () -> Unit
) {
    SkyFitButtonComponent(
        modifier = Modifier.padding(16.dp).fillMaxWidth(), text = "Devam Et",
        onClick = onClickContinue,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = ButtonState.Rest
    )
}