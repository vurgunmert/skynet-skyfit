package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitBadgeTabBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.viewmodel.UserAppointmentsViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileUserAppointmentsScreen(navigator: Navigator) {
    val viewModel: UserAppointmentsViewModel = koinInject()

    val appointments by viewModel.appointments.collectAsState()
    var activeTab by remember { mutableStateOf(0) }
    val tabTitles by viewModel.tabTitles.collectAsState()
    var showCancelDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                SkyFitScreenHeader("Randevularim", onClickBack = { navigator.popBackStack() })

                SkyFitBadgeTabBarComponent(
                    titles = tabTitles,
                    selectedTabIndex = activeTab,
                    onTabSelected = { index -> activeTab = index },
                    deleteAllEnabled = appointments.size + appointments.size > 0,
                    onDeleteAll = { }
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
            items(appointments) {
                AppointmentCardItemComponent(it, Modifier.fillMaxWidth())
            }
        }
    }

    AppointmentCancelDialog(
        showDialog = showCancelDialog,
        onDismiss = { showCancelDialog = false },
        onConfirm = {
            showCancelDialog = false
        }
    )
}