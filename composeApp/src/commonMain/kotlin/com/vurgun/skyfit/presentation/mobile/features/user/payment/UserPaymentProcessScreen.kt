package com.vurgun.skyfit.presentation.mobile.features.user.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserPaymentProcessStep {
    METHOD,
    INPUT,
    SUMMARY,
    CONFIRMED
}

@Composable
fun MobileUserPaymentProcessScreen(navigator: Navigator) {

    var paymentStep by remember { mutableStateOf(MobileUserPaymentProcessStep.METHOD) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (paymentStep) {
                MobileUserPaymentProcessStep.METHOD -> {
                    SkyFitScreenHeader("Odeme Yap", onBackClick = { navigator.popBackStack() })
                    MobileUserPaymentStepProgressBar(paymentStep)
                    MobileUserActivityCalendarPaymentMethodComponent(
                        onClickMethod = { paymentStep = MobileUserPaymentProcessStep.INPUT }
                    )
                }

                MobileUserPaymentProcessStep.INPUT -> {
                    SkyFitScreenHeader("Odeme Yap", onBackClick = { paymentStep = MobileUserPaymentProcessStep.METHOD })
                    MobileUserPaymentStepProgressBar(paymentStep)
                    MobileUserActivityCalendarPaymentMethodInputComponent()
                }

                MobileUserPaymentProcessStep.SUMMARY -> {
                    SkyFitScreenHeader("Odeme Yap", onBackClick = { paymentStep = MobileUserPaymentProcessStep.INPUT })
                    MobileUserPaymentStepProgressBar(paymentStep)
                    MobileUserActivityCalendarPaymentSummaryComponent()
                }

                MobileUserPaymentProcessStep.CONFIRMED -> {
                    MobileUserActivityCalendarPaymentConfirmedComponent()
                }
            }
        }
    }
}


@Composable
private fun MobilePaymentMethodItemComponent(title: String, onClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = SkyFitColor.icon.default
            )
            Spacer(Modifier.height(16.dp))

            Text(
                text = title,
                style = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.height(16.dp))

            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
        }
    }

}

@Composable
private fun MobileUserActivityCalendarPaymentMethodComponent(onClickMethod: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Ödeme yönteminizi seçin:",
                textAlign = TextAlign.Center,
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            MobilePaymentMethodItemComponent("PayPal", onClickMethod)
        }

        item {
            MobilePaymentMethodItemComponent("ApplePay", onClickMethod)
        }

        item {
            MobilePaymentMethodItemComponent("Kredi Karti", onClickMethod)
        }
    }
}

@Composable
private fun MobileUserActivityCalendarPaymentMethodInputComponent() {
    TodoBox("MobileUserActivityCalendarPaymentMethodInputComponent", Modifier.size(398.dp, 408.dp))
}

@Composable
private fun MobileUserActivityCalendarPaymentSummaryComponent() {
    TodoBox("MobileUserActivityCalendarPaymentSummaryComponent", Modifier.size(398.dp, 500.dp))
}

@Composable
private fun MobileUserActivityCalendarPaymentConfirmedComponent() {
    TodoBox("MobileUserActivityCalendarPaymentConfirmedComponent", Modifier.size(398.dp, 481.dp))
}

@Composable
private fun MobileUserPaymentStepProgressBar(
    currentStep: MobileUserPaymentProcessStep,
    modifier: Modifier = Modifier
) {
    val progress = when (currentStep) {
        MobileUserPaymentProcessStep.METHOD -> 0.25f
        MobileUserPaymentProcessStep.INPUT -> 0.50f
        MobileUserPaymentProcessStep.SUMMARY -> 0.75f
        MobileUserPaymentProcessStep.CONFIRMED -> 1.00f
    }

    Box(
        modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(SkyFitColor.border.default) // Background track
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(2.dp)
                .background(SkyFitColor.specialty.buttonBgRest)
        )
    }
}
