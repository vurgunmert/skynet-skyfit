package com.vurgun.skyfit.settings.payment

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
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_app_logo

private enum class MobileUserPaymentProcessStep {
    METHOD,
    INPUT,
    SUMMARY,
    CONFIRMED
}

@Composable
fun MobileUserPaymentProcessScreen(goToBack: () -> Unit) {

    var paymentStep by remember { mutableStateOf(MobileUserPaymentProcessStep.INPUT) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (paymentStep) {
                MobileUserPaymentProcessStep.METHOD -> {
                    CompactTopBar("Odeme Yap", onClickBack = goToBack)
                    MobileUserPaymentStepProgressBar(paymentStep)
                    MobileUserActivityCalendarPaymentMethodComponent(
                        onClickMethod = { paymentStep = MobileUserPaymentProcessStep.INPUT }
                    )
                }

                MobileUserPaymentProcessStep.INPUT -> {
                    CompactTopBar("Odeme Yap", onClickBack = { paymentStep = MobileUserPaymentProcessStep.METHOD })
                    MobileUserPaymentStepProgressBar(paymentStep)
                    MobileUserActivityCalendarPaymentMethodInputComponent()
                    Spacer(Modifier.weight(1f))
                    MobilePaymentActionGroupComponent(
                        primaryText = "Devam Et",
                        onClickPrimary = {},
                        onClickCancel = {}
                    )
                }

                MobileUserPaymentProcessStep.SUMMARY -> {
                    CompactTopBar("Odeme Yap", onClickBack = { paymentStep = MobileUserPaymentProcessStep.INPUT })
                    MobileUserPaymentStepProgressBar(paymentStep)
                    MobileUserActivityCalendarPaymentSummaryComponent()
                    Spacer(Modifier.weight(1f))
                    MobilePaymentActionGroupComponent(
                        primaryText = "Onaylat",
                        onClickPrimary = {},
                        onClickCancel = {}
                    )
                }

                MobileUserPaymentProcessStep.CONFIRMED -> {
//                    MobileUserActivityCalendarAddedScreen(navigator)
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
                painter = painterResource(Res.drawable.ic_app_logo),
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
                painter = painterResource(Res.drawable.ic_app_logo),
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
    var cardHolder by remember { mutableStateOf("") }
    var cardNo by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var ccv by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            "Kart Bilgileri",
            style = SkyFitTypography.bodyLargeSemibold
        )

        SingleLineInputText(
            title = "Kart Üzerindeki İsim",
            hint = "İsim ekle",
            value = cardHolder,
            onValueChange = { cardHolder = it }
        )

        SingleLineInputText(
            title = "Kart Numarası",
            hint = "Kart Numarası",
            value = cardNo,
            onValueChange = { cardNo = it }
        )

        SingleLineInputText(
            title = "Son Kullanma Tarihi",
            hint = "AA/YY",
            value = expiryDate,
            onValueChange = { expiryDate = it }
        )

        SingleLineInputText(
            title = "CCV",
            hint = "CCV ekle",
            value = cardHolder,
            onValueChange = { cardHolder = it }
        )
    }
}

@Composable
private fun MobileUserActivityCalendarPaymentSummaryComponent() {
    Column(
        Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Ozet",
            style = SkyFitTypography.bodyLargeSemibold
        )
        Spacer(Modifier.height(24.dp))
        MobileClassSummaryItemComponent(
            time = "",
            trainer = "",
            name = ""
        )
        Spacer(Modifier.height(24.dp))
        MobilePaymentMethodSummaryItemComponent(
            time = "",
            detail = "",
            expiry = "",
        )
        Spacer(Modifier.height(24.dp))
        MobilePaymentSummaryItemComponent(
            cost = "",
            serviceCost = "",
            totalCost = "",
        )
    }
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


@Composable
private fun MobileClassSummaryItemComponent(
    time: String,
    trainer: String,
    name: String
) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Ders Bilgileri",
                style = SkyFitTypography.bodyMediumSemibold
            )
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Saat",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "08:00 - 09:00",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Egitmen",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Micheal Blake",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Ders",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Pilates",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
        }
    }
}

@Composable
private fun MobilePaymentMethodSummaryItemComponent(
    time: String,
    detail: String,
    expiry: String
) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Kart Bilgileri",
                style = SkyFitTypography.bodyMediumSemibold
            )
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Kart Üzerindeki İsim",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "John Doe",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Kart Numarası",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "1234 5678 9012 3456",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    text = "Son Kulanma Tarihi",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "1112",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
        }
    }
}


@Composable
private fun MobilePaymentSummaryItemComponent(
    cost: String,
    serviceCost: String,
    totalCost: String
) {
    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        Column {
            Row {
                Text(
                    text = "Kurs Ücreti",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "₺300.00",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(8.dp))
            Row {
                Text(
                    text = "Hizmet Bedeli",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "₺6.00",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
            Spacer(Modifier.height(8.dp))
            Row {
                Text(
                    text = "Toplam",
                    style = SkyFitTypography.bodyMediumMedium,
                    color = SkyFitColor.text.secondary
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "₺306.00",
                    style = SkyFitTypography.bodyMediumSemibold
                )
            }
        }
    }
}

@Composable
private fun MobilePaymentActionGroupComponent(
    primaryText: String,
    onClickPrimary: () -> Unit,
    onClickCancel: () -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = primaryText,
            onClick = onClickPrimary,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "İptal",
            onClick = onClickCancel,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
        Spacer(Modifier.height(44.dp))
    }
}