package com.vurgun.skyfit.feature.calendar.screen.appointments

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class TrainerAppointmentListingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileTrainerAppointmentsScreen(
            goToBack = { navigator.pop() }
        )
    }
    @Composable
    private fun MobileTrainerAppointmentsScreen(goToBack: () -> Unit) {


    }

}



@Composable
private fun MobileTrainerAppointmentListingScreen(
    goToBack: () -> Unit,
    goToDetails: (lpId: Int) -> Unit,
    viewModel: TrainerAppointmentListingViewModel
) {

//    val appointments by viewModel.filteredAppointments.collectAsState()
//    val activeTab by viewModel.activeTab.collectAsState()
//    val tabTitles by viewModel.tabTitles.collectAsState()
//
//    var showDeleteDialog by remember { mutableStateOf(false) }
//    var appointmentToDelete by remember { mutableStateOf<Appointment?>(null) }
//
//
//    SkyFitMobileScaffold(
//        topBar = {
//            Column {
//                SkyFitScreenHeader(stringResource(Res.string.appointments_title), onClickBack = goToBack)
//
//                SkyFitBadgeTabBarComponent(
//                    titles = tabTitles,
//                    selectedTabIndex = activeTab,
//                    onTabSelected = { index -> viewModel.onAction(UserAppointmentListingAction.ChangeTab(index)) },
//                    onFilter = { viewModel.onAction(UserAppointmentListingAction.ShowFilter) }
//                )
//            }
//        }
//    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(appointments) { appointment ->
//
//                when (activeTab) {
//                    0 -> {
//                        ActiveAppointmentEventItem(
//                            title = appointment.title,
//                            iconId = appointment.iconId,
//                            date = appointment.startDate.toString(),
//                            timePeriod = "${appointment.startTime} - ${appointment.endTime}",
//                            location = appointment.facilityName,
//                            trainer = appointment.trainerFullName,
//                            note = appointment.trainerNote,
//                            onDelete = {
//                                appointmentToDelete = appointment
//                                showDeleteDialog = true
//                            },
//                            onClick = { viewModel.onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
//                        )
//                    }
//
//                    1 -> {
//                        BasicAppointmentEventItem(
//                            title = appointment.title,
//                            iconId = appointment.iconId,
//                            date = appointment.startDate.toString(),
//                            timePeriod = "${appointment.startTime} - ${appointment.endTime}",
//                            location = appointment.facilityName,
//                            trainer = appointment.trainerFullName,
//                            note = appointment.trainerNote,
//                            onClick = { viewModel.onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
//                        )
//                    }
//
//                    2 -> {
//                        AttendanceAppointmentEventItem(
//                            title = appointment.title,
//                            iconId = appointment.iconId,
//                            date = appointment.startDate.toString(),
//                            timePeriod = "${appointment.startTime} - ${appointment.endTime}",
//                            location = appointment.facilityName,
//                            trainer = appointment.trainerFullName,
//                            note = appointment.trainerNote,
//                            isCompleted = appointment.status == 2, //TODO: WTF?
//                            onClick = { viewModel.onAction(UserAppointmentListingAction.NavigateToDetail(appointment.lpId)) }
//                        )
//                    }
//                }
//            }
//        }
//    }
//
//    // Confirm Delete Single Appointment
//    AppointmentDeleteDialog(
//        showDialog = showDeleteDialog,
//        onDismiss = { showDeleteDialog = false },
//        onConfirm = {
//            appointmentToDelete?.let { viewModel.onAction(UserAppointmentListingAction.CancelAppointment(it)) }
//            showDeleteDialog = false
//        }
//    )
}
