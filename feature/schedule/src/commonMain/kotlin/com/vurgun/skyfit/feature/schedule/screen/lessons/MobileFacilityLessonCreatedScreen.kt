package com.vurgun.skyfit.feature.schedule.screen.lessons

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.popUntil
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.event.AppointmentCardViewData
import com.vurgun.skyfit.core.ui.components.event.DetailedLessonEventItem
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.back_to_lessons_action
import skyfit.core.ui.generated.resources.go_to_home_action
import skyfit.core.ui.generated.resources.img_check_mark_blue_box
import skyfit.core.ui.generated.resources.lesson_create_action
import skyfit.core.ui.generated.resources.lesson_created_message
import skyfit.core.ui.generated.resources.lesson_updated_message

class FacilityLessonCreatedScreen(
    private val isUpdate: Boolean,
    private val lesson: AppointmentCardViewData
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileFacilityLessonCreatedScreen(
            goToListing = { navigator.pop() },
            goToNewLesson = { navigator.replace(FacilityLessonEditScreen()) },
            goToDashboard = { navigator.popUntil(SharedScreen.Dashboard) },
            isUpdate,
            lesson
        )
    }

}

@Composable
private fun MobileFacilityLessonCreatedScreen(
    goToListing: () -> Unit,
    goToNewLesson: () -> Unit,
    goToDashboard: () -> Unit,
    isUpdate: Boolean,
    lesson: AppointmentCardViewData
) {

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

            MobileFacilityClassEditCompletedContentGroup(isUpdate, lesson)

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
private fun MobileFacilityClassEditCompletedContentGroup(
    isUpdate: Boolean,
    lesson: AppointmentCardViewData
) {

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
            text = stringResource(if (isUpdate) Res.string.lesson_updated_message else Res.string.lesson_created_message),
            style = SkyFitTypography.heading4,
            textAlign = TextAlign.Center
        )

        DetailedLessonEventItem(
            title = lesson.title,
            iconId = lesson.iconId,
            date = lesson.date,
            timePeriod = lesson.hours,
            category = lesson.category,
            location = lesson.location,
            trainer = lesson.trainer,
            capacity = lesson.capacity.toString(),
            cost = lesson.cost.toString()
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