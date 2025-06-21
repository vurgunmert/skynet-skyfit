package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.event.AppointmentCardItemComponent
import com.vurgun.skyfit.core.ui.components.event.AppointmentCardViewData
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo

class UserActivityCalendarPaymentRequiredScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileUserActivityCalendarPaymentRequiredScreen(
            goToBack = { navigator.pop() }
        )
    }

}

@Composable
private fun MobileUserActivityCalendarPaymentRequiredScreen(
    goToBack: () -> Unit
) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(32.dp))
            MobileUserActivityCalendarPaymentRequiredComponent(
                onClickContinue = {},
                onClickCancel = {}
            )
        }
    }
}


@Composable
private fun MobileUserActivityCalendarPaymentRequiredComponent(
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit
) {
    val appointmentCardItem = AppointmentCardViewData(
        iconId = 3,
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
            painter = painterResource(Res.drawable.ic_app_logo),
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

        AppointmentCardItemComponent(appointmentCardItem, Modifier.fillMaxWidth())

        Spacer(Modifier.height(24.dp))

        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Odeme Yap",
            onClick = onClickContinue,
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
    }
}