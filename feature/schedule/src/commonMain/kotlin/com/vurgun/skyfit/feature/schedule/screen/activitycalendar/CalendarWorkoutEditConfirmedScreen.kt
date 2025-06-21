package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.popUntil
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.event.BasicActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class CalendarWorkoutEditConfirmedScreen(
    val workoutName: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SkyFitMobileScaffold {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(60.dp))

                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.img_check_mark_blue_box),
                        contentDescription = null,
                        modifier = Modifier.size(104.dp)
                    )

                    SkyText(
                        text = stringResource(Res.string.activity_added_label),
                        styleType = TextStyleType.Heading4
                    )

                    BasicActivityCalendarEventItem(
                        title = workoutName,
                        iconId = null,
                        timePeriod = "${startDateTime.time} - ${endDateTime.time}",
                        enabled = true
                    )

                    SkyButton(
                        label = stringResource(Res.string.calendar_label),
                        rightIcon = painterResource(Res.drawable.ic_calendar_dots),
                        variant = SkyButtonVariant.Primary,
                        size = SkyButtonSize.Large,
                        onClick = {
                            navigator.popUntil { it is UserActivityCalendarScreen }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    SkyButton(
                        label = stringResource(Res.string.go_to_home_action),
                        variant = SkyButtonVariant.Secondary,
                        size = SkyButtonSize.Large,
                        onClick = {
                            navigator.popUntil(SharedScreen.Main)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}