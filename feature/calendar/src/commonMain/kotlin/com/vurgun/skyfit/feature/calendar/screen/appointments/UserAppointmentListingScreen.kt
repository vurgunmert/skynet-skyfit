package com.vurgun.skyfit.feature.calendar.screen.appointments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.SkyFitDestructiveDialogComponent
import com.vurgun.skyfit.core.ui.components.dialog.rememberErrorDialogState
import com.vurgun.skyfit.core.ui.components.event.ActiveAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.event.AttendanceAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.event.BasicAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.special.SkyFitBadgeTabBarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.data.courses.domain.model.Appointment
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.appointments_title
import skyfit.core.ui.generated.resources.error_cancel_appointment_title

class UserAppointmentListingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserAppointmentListingViewModel>()
        val cancelDialog = rememberErrorDialogState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserAppointmentListingEffect.NavigateToBack ->
                    navigator.pop()

                UserAppointmentListingEffect.ShowFilter -> Unit //TODO

                is UserAppointmentListingEffect.NavigateToDetail ->
                    navigator.push(SharedScreen.UserAppointmentDetail(effect.lpId))

                is UserAppointmentListingEffect.ShowCancelError ->
                    cancelDialog.show(effect.message)
            }
        }

        LaunchedEffect(Unit) {
            viewModel.refreshData()
        }

        MobileUserAppointmentListingScreen(
            goToBack = { navigator.pop() },
            goToDetails = { navigator.push(SharedScreen.UserAppointmentDetail(it)) },
            viewModel = viewModel
        )

        cancelDialog.message?.let { message ->
            ErrorDialog(
                title = stringResource(Res.string.error_cancel_appointment_title),
                message = message,
                onDismiss = cancelDialog::dismiss
            )
        }
    }
}


@Composable
private fun MobileUserAppointmentListingScreen(
    goToBack: () -> Unit,
    goToDetails: (lpId: Int) -> Unit,
    viewModel: UserAppointmentListingViewModel
) {

    val appointments by viewModel.filteredAppointments.collectAsState()
    val activeTab by viewModel.activeTab.collectAsState()
    val tabTitles by viewModel.tabTitles.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var appointmentToDelete by remember { mutableStateOf<Appointment?>(null) }


    SkyFitMobileScaffold(
        topBar = {
            Column {
                SkyFitScreenHeader(stringResource(Res.string.appointments_title), onClickBack = goToBack)

                SkyFitBadgeTabBarComponent(
                    titles = tabTitles,
                    selectedTabIndex = activeTab,
                    onTabSelected = { index -> viewModel.onAction(UserAppointmentListingAction.ChangeTab(index)) },
                    onFilter = { viewModel.onAction(UserAppointmentListingAction.ShowFilter) }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(appointments) { appointment ->

                when (activeTab) {
                    0 -> {
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
                            onClick = { viewModel.onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
                        )
                    }

                    1 -> {
                        BasicAppointmentEventItem(
                            title = appointment.title,
                            iconId = appointment.iconId,
                            date = appointment.startDate.toString(),
                            timePeriod = "${appointment.startTime} - ${appointment.endTime}",
                            location = appointment.facilityName,
                            trainer = appointment.trainerFullName,
                            note = appointment.trainerNote,
                            onClick = { viewModel.onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
                        )
                    }

                    2 -> {
                        AttendanceAppointmentEventItem(
                            title = appointment.title,
                            iconId = appointment.iconId,
                            date = appointment.startDate.toString(),
                            timePeriod = "${appointment.startTime} - ${appointment.endTime}",
                            location = appointment.facilityName,
                            trainer = appointment.trainerFullName,
                            note = appointment.trainerNote,
                            isCompleted = appointment.status == 2, //TODO: WTF?
                            onClick = { viewModel.onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
                        )
                    }
                }
            }
        }
    }

    // Confirm Delete Single Appointment
    AppointmentDeleteDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            appointmentToDelete?.let { viewModel.onAction(UserAppointmentListingAction.CancelAppointment(it)) }
            showDeleteDialog = false
        }
    )
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


