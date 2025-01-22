package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

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
                    MobileUserActivityCalendarPaymentRequiredComponent()
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
private fun MobileUserActivityCalendarPaymentRequiredComponent() {
    TodoBox("MobileUserActivityCalendarPaymentRequiredComponent", Modifier.size(382.dp, 556.dp))
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
