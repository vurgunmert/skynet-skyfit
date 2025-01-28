package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardComponent
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardComponentItem
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserActivityCalendarPaymentStep {
    REQUIRED,
    METHOD,
    INPUT,
    SUMMARY,
    CONFIRMED
}

@Composable
fun MobileUserActivityCalendarPaymentScreen(navigator: Navigator) {

    val step = MobileUserActivityCalendarPaymentStep.REQUIRED //TODO: logic

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (step) {
                MobileUserActivityCalendarPaymentStep.REQUIRED -> {
                    Spacer(Modifier.height(32.dp))
                    MobileUserActivityCalendarPaymentRequiredComponent(
                        onClickContinue = {},
                        onClickCancel = {}
                    )
                }

                MobileUserActivityCalendarPaymentStep.METHOD -> {

                    MobileUserActivityCalendarPaymentMethodToolbarComponent()
                    MobileUserActivityCalendarPaymentMethodComponent()
                }

                MobileUserActivityCalendarPaymentStep.INPUT -> {
                    MobileUserActivityCalendarPaymentMethodInputComponent()
                }

                MobileUserActivityCalendarPaymentStep.SUMMARY -> {
                    MobileUserActivityCalendarPaymentSummaryComponent()
                }

                MobileUserActivityCalendarPaymentStep.CONFIRMED -> {
                    MobileUserActivityCalendarPaymentConfirmedComponent()
                }
            }
        }
    }
}

@Composable
private fun MobileUserActivityCalendarPaymentRequiredComponent(
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit
) {
    val appointmentCardComponentItem = AppointmentCardComponentItem(
        iconUrl = "TODO()",
        title = "Shoulders and Abs",
        date = "30/11/2024",
        hours = "08:00 - 09:00",
        category = "Group Fitness",
        location = "@ironstudio",
        trainer = "Micheal Blake",
        capacity = "2/5",
        cost = "100",
        note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
        isFull = null
    )

    Column(
        Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(104.dp)
        )
        Spacer(Modifier.height(24.dp))

        Text(
            "Etkinliği eklemek için ödeme yapmalısnız",
            style = SkyFitTypography.heading4,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        AppointmentCardComponent(appointmentCardComponentItem, Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Odeme Yap",
            onClick = onClickContinue,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "İptal",
            onClick = onClickCancel,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileUserActivityCalendarPaymentMethodToolbarComponent() {
    TodoBox("MobileUserActivityCalendarPaymentMethodToolbarComponent", Modifier.size(430.dp, 44.dp))
}

@Composable
private fun MobileUserActivityCalendarPaymentMethodComponent() {
    TodoBox("MobileUserActivityCalendarPaymentMethodComponent", Modifier.size(398.dp, 312.dp))
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
