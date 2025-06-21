package com.vurgun.skyfit.profile.trainer.schedule

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.event.NoLessonOnSelectedDaysEventItem
import com.vurgun.skyfit.core.ui.components.event.SelectableLessonEventItem
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.monthly.CalendarRangeDateSelectorCard
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.BodyLargeMediumText
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.CardFieldIconText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class TrainerProfileScheduleScreen(private val trainerId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerProfileScheduleViewModel>()
        var bookingError by remember { mutableStateOf<String?>(null) }

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                is TrainerProfileScheduleEffect.NavigateBack -> {
                    navigator.pop()
                }

                is TrainerProfileScheduleEffect.ShowBookingError -> {
                    bookingError = effect.message
                }

                is TrainerProfileScheduleEffect.NavigateToAppointmentDetail -> {
                    navigator.push(SharedScreen.UserAppointmentDetail(effect.lpId))
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(trainerId)
        }

        MobileTrainerProfileScheduleScreen(viewModel)

        if (bookingError != null) {
            ErrorDialog(
                message = bookingError,
                onDismiss = { bookingError = null }
            )
        }
    }
}


@Composable
fun MobileTrainerProfileScheduleScreen(
    viewModel: TrainerProfileScheduleViewModel
) {

    val uiState by viewModel.uiState.collectAsState()
    val lessons by viewModel.lessons.collectAsState()
    val isAppointmentAllowed by viewModel.isBookingEnabled.collectAsState()

    when (uiState) {
        TrainerProfileScheduleUiState.Loading -> FullScreenLoaderContent()
        is TrainerProfileScheduleUiState.Error -> {
            val message = (uiState as TrainerProfileScheduleUiState.Error).message
            ErrorScreen(message = message, onConfirm = { viewModel.onAction(TrainerProfileScheduleAction.NavigateBack) })
        }

        is TrainerProfileScheduleUiState.Content -> {
            val content = (uiState as TrainerProfileScheduleUiState.Content)

            SkyFitMobileScaffold(
                topBar = {
                    CompactTopBar(
                        title = stringResource(Res.string.appointment_book_action),
                        onClickBack = { viewModel.onAction(TrainerProfileScheduleAction.NavigateBack) }
                    )
                },
                bottomBar = {
                    if (isAppointmentAllowed) {
                        MobileTrainerProfileScheduleComponent.BookAction(onClick = {
                            viewModel.onAction(TrainerProfileScheduleAction.BookAppointment)
                        })
                    }
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Spacer(Modifier.height(4.dp))

                    MobileTrainerProfileScheduleComponent.ProfileCard(content.profile)

                    Spacer(Modifier.height(48.dp))

                    CalendarRangeDateSelectorCard(
                        onSelectionChanged = { startDate, endDate ->
                            viewModel.onAction(TrainerProfileScheduleAction.ChangeDate(startDate, endDate))
                        }
                    )

                    Spacer(Modifier.height(16.dp))

                    MobileTrainerProfileScheduleComponent.SelectableLessonsList(
                        lessons = lessons,
                        onClickItem = {
                            viewModel.onAction(TrainerProfileScheduleAction.ToggleLessonSelection(it))
                        },
                        onFilter = {}
                    )

                    Spacer(Modifier.height(112.dp))
                }
            }
        }

    }
}

private object MobileTrainerProfileScheduleComponent {

    @Composable
    fun ProfileCard(profile: TrainerProfile) {
        Row(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkImage(
                imageUrl = profile.backgroundImageUrl,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyLargeMediumText(
                        text = profile.fullName
                    )
                    BodyMediumRegularText(
                        text = " @${profile.username}",
                        color = SkyFitColor.text.secondary
                    )
                }
                Spacer(Modifier.height(8.dp))
                CardFieldIconText(
                    text = profile.gymName.orEmpty(),
                    iconRes = Res.drawable.ic_location_pin,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun SelectableLessonsList(
        lessons: List<LessonSessionItemViewData>,
        onClickItem: (LessonSessionItemViewData) -> Unit,
        onFilter: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = SkyFitAsset.getPainter(SkyFitAsset.SkyFitIcon.EXERCISES.id),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = SkyFitColor.icon.default
                )

                Text(
                    text = stringResource(Res.string.lesson_select_label),
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f)
                )

                // TODO: Filter
            }

            if (lessons.isEmpty()) {
                NoLessonOnSelectedDaysEventItem()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    lessons.forEach { item ->
                        SelectableLessonEventItem(
                            title = item.title,
                            iconId = item.iconId,
                            date = item.date.toString(),
                            timePeriod = item.hours.toString(),
                            location = item.location.toString(),
                            trainer = item.trainer.toString(),
                            capacity = item.capacityRatio.toString(),
                            note = item.note,
                            modifier = Modifier
                                .then(
                                    if (item.selected) {
                                        Modifier.border(
                                            width = 1.dp,
                                            color = SkyFitColor.border.secondaryButton,
                                            shape = RoundedCornerShape(16.dp)
                                        )
                                    } else {
                                        Modifier
                                    }
                                )
                                .clickable { onClickItem(item) }
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BookAction(onClick: () -> Unit) {
        Box(Modifier.fillMaxWidth().padding(32.dp)) {
            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.appointment_create_action),
                onClick = onClick,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.ic_check)
            )
        }
    }
}