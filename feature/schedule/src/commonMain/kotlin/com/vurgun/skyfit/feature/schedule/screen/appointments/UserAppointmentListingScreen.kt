package com.vurgun.skyfit.feature.schedule.screen.appointments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.user.model.Appointment
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.chip.SecondaryPillChip
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.SkyFitDestructiveDialogComponent
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.event.ActiveAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.event.AttendanceAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.event.BasicAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.ExpandedTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyMediumMediumText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalOverlayController
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class UserAppointmentListingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserAppointmentListingViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val activeTab = (uiState as? UserAppointmentListingUiState.Content)?.activeTab
            ?: UserAppointmentListingTab.Active
        val cancelDialog = rememberErrorDialogState()
        val overlayController = LocalOverlayController.current
        val windowSize = LocalWindowSize.current

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserAppointmentListingEffect.NavigateToBack -> {
                    navigator.pop()
                    overlayController?.invoke(null)
                }

                UserAppointmentListingEffect.ShowFilter ->
                    navigator.push(UserAppointmentListingFilterScreen(viewModel))

                is UserAppointmentListingEffect.NavigateToDetail ->
                    navigator.push(SharedScreen.UserAppointmentDetail(effect.lpId))

                is UserAppointmentListingEffect.ShowCancelError ->
                    cancelDialog.show(effect.message)
            }
        }

        SkyFitMobileScaffold(
            topBar = {
                Column {
                    if (windowSize == WindowSize.EXPANDED) {
                        ExpandedTopBar(
                            title = stringResource(Res.string.appointments_title),
                            onClickBack = { viewModel.onAction(UserAppointmentListingAction.NavigateToBack) })
                    } else {
                        CompactTopBar(
                            title = stringResource(Res.string.appointments_title),
                            onClickBack = { viewModel.onAction(UserAppointmentListingAction.NavigateToBack) })
                    }

                    UserAppointmentListingTabRow(
                        selectedTab = activeTab,
                        tabTitles = (uiState as? UserAppointmentListingUiState.Content)?.tabTitles.orEmpty(),
                        onTabSelected = { tab ->
                            viewModel.onAction(UserAppointmentListingAction.ChangeTab(tab))
                        },
                        onClickFilter = {
                            viewModel.onAction(UserAppointmentListingAction.ShowFilter)
                        }
                    )
                }
            }
        ) {
            when (uiState) {
                UserAppointmentListingUiState.Loading -> FullScreenLoaderContent()
                is UserAppointmentListingUiState.Error -> {
                    val message = (uiState as UserAppointmentListingUiState.Error)
                    ErrorScreen(message = message.toString(), onConfirm = { navigator.pop() })
                }

                is UserAppointmentListingUiState.Content -> {
                    val content = (uiState as UserAppointmentListingUiState.Content)
                    MobileUserAppointmentListingContent(content, viewModel::onAction)
                }
            }
        }

        cancelDialog.message?.let { message ->
            ErrorDialog(
                title = stringResource(Res.string.error_cancel_appointment_title),
                message = message,
                onDismiss = cancelDialog::dismiss
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MobileUserAppointmentListingContent(
    content: UserAppointmentListingUiState.Content,
    onAction: (UserAppointmentListingAction) -> Unit
) {

    var showDeleteDialog by remember { mutableStateOf(false) }
    var appointmentToDelete by remember { mutableStateOf<Appointment?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (content.currentFilter.hasAny) {
            item {
                FlowRow(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    content.currentFilter.selectedTitles.forEach { filterForTitle ->
                        SecondaryPillChip(filterForTitle, selected = false, onClick = {
                            onAction(UserAppointmentListingAction.RemoveTitleFilter(filterForTitle))
                        })
                    }
                    content.currentFilter.selectedHours.forEach { filterForTime ->
                        SecondaryPillChip(filterForTime.toString(), selected = false, onClick = {
                            onAction(UserAppointmentListingAction.RemoveTimeFilter(filterForTime))
                        })
                    }
                    content.currentFilter.selectedDates.forEach { filterForDate ->
                        SecondaryPillChip(filterForDate.toString(), selected = false, onClick = {
                            onAction(UserAppointmentListingAction.RemoveDateFilter(filterForDate))
                        })
                    }
                }
            }
        }

        items(content.filteredAppointments) { appointment ->

            when (content.activeTab) {
                UserAppointmentListingTab.Active -> {
                    ActiveAppointmentEventItem(
                        title = appointment.title,
                        iconId = appointment.iconId,
                        date = appointment.startDate.toString(),
                        timePeriod = "${appointment.startTime} - ${appointment.endTime}",
                        location = appointment.facilityName,
                        trainer = appointment.trainerFullName,
                        note = appointment.trainerNote,
                        onDelete = {
                            appointmentToDelete = appointment
                            showDeleteDialog = true
                        },
                        onClick = { onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
                    )
                }

                UserAppointmentListingTab.Cancelled -> {
                    BasicAppointmentEventItem(
                        title = appointment.title,
                        iconId = appointment.iconId,
                        date = appointment.startDate.toString(),
                        timePeriod = "${appointment.startTime} - ${appointment.endTime}",
                        location = appointment.facilityName,
                        trainer = appointment.trainerFullName,
                        note = appointment.trainerNote,
                        onClick = { onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
                    )
                }

                UserAppointmentListingTab.Completed -> {
                    AttendanceAppointmentEventItem(
                        title = appointment.title,
                        iconId = appointment.iconId,
                        date = appointment.startDate.toString(),
                        timePeriod = "${appointment.startTime} - ${appointment.endTime}",
                        location = appointment.facilityName,
                        trainer = appointment.trainerFullName,
                        note = appointment.trainerNote,
                        isCompleted = appointment.status == 2, // TODO: StatusType
                        onClick = { onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
                    )
                }
            }
        }
    }

    // Confirm Delete Single Appointment
    AppointmentDeleteDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            appointmentToDelete?.let { onAction(UserAppointmentListingAction.CancelAppointment(it)) }
            showDeleteDialog = false
        }
    )
}

@Composable
private fun UserAppointmentListingTabRow(
    selectedTab: UserAppointmentListingTab,
    tabTitles: List<String>,
    onTabSelected: (UserAppointmentListingTab) -> Unit,
    onClickFilter: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppointmentListingTabItem(
                text = tabTitles.getOrNull(0) ?: stringResource(Res.string.lesson_status_active),
                selected = selectedTab == UserAppointmentListingTab.Active,
                onClick = { onTabSelected(UserAppointmentListingTab.Active) }
            )
            AppointmentListingTabItem(
                text = tabTitles.getOrNull(1) ?: stringResource(Res.string.lesson_status_cancelled),
                selected = selectedTab == UserAppointmentListingTab.Cancelled,
                onClick = { onTabSelected(UserAppointmentListingTab.Cancelled) }
            )
            AppointmentListingTabItem(
                text = tabTitles.getOrNull(2) ?: stringResource(Res.string.lesson_status_completed),
                selected = selectedTab == UserAppointmentListingTab.Completed,
                onClick = { onTabSelected(UserAppointmentListingTab.Completed) }
            )
            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { onClickFilter() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyMediumMediumText(
                    text = "Filtre",
                    modifier = Modifier,
                    color = SkyFitColor.text.linkInverse
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_filter),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.linkInverse
                )
            }

        }

        Divider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = SkyFitColor.border.secondaryButtonDisabled
        )
    }
}


@Composable
private fun AppointmentDeleteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "Randevuyu İptal Et",
    message: String = "Randevunuzu iptal etmek istiyor musunuz?"
) {
    SkyFitDestructiveDialogComponent(
        showDialog = showDialog,
        title = title,
        message = message,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}


