package com.vurgun.skyfit.feature.courses.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.button.SecondaryLargeButton
import com.vurgun.skyfit.ui.core.components.event.AppointmentCardViewData
import com.vurgun.skyfit.ui.core.components.event.DetailedLessonEventItem
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.back_to_lessons_action
import skyfit.ui.core.generated.resources.go_to_home_action
import skyfit.ui.core.generated.resources.img_check_mark_blue_box
import skyfit.ui.core.generated.resources.lesson_create_action
import skyfit.ui.core.generated.resources.lesson_created_message

@Composable
fun MobileFacilityLessonCreatedScreen(
    goToListing: () -> Unit,
    goToNewLesson: () -> Unit,
    goToDashboard: () -> Unit,
    viewModel: FacilityEditLessonViewModel
) {

    val item = AppointmentCardViewData(
        iconId = 1,
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

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(48.dp))

            MobileFacilityClassEditCompletedContentGroup(item = item)

            Spacer(Modifier.height(24.dp))

            MobileFacilityClassEditCompletedActionGroup(
                onBackToLessons = goToListing,
                onCreateLesson = goToNewLesson,
                onGoToHome = goToDashboard
            )
        }
    }
}

@Composable
private fun MobileFacilityClassEditCompletedContentGroup(item: AppointmentCardViewData) {

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

        Text(
            stringResource(Res.string.lesson_created_message),
            style = SkyFitTypography.heading4,
            textAlign = TextAlign.Center
        )

        DetailedLessonEventItem(
            title = item.title,
            iconId = item.iconId,
            date = item.date,
            timePeriod = item.hours,
            category = item.category,
            location = item.location,
            trainer = item.trainer,
            capacity = item.capacity.toString(),
            cost = item.cost.toString()
        )
    }
}

@Composable
private fun MobileFacilityClassEditCompletedActionGroup(
    onBackToLessons: () -> Unit,
    onCreateLesson: () -> Unit,
    onGoToHome: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PrimaryLargeButton(
            text = stringResource(Res.string.back_to_lessons_action),
            modifier = Modifier.fillMaxWidth(),
            onClick = onBackToLessons,
        )
        SecondaryLargeButton(
            text = stringResource(Res.string.lesson_create_action),
            modifier = Modifier.fillMaxWidth(),
            onClick = onCreateLesson
        )
        SecondaryLargeButton(
            text = stringResource(Res.string.go_to_home_action),
            modifier = Modifier.fillMaxWidth(),
            onClick = onGoToHome
        )
    }
}