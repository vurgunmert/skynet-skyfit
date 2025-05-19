package com.vurgun.skyfit.feature.dashboard.home.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.dashboard.component.EmptyFacilityAppointmentContent
import com.vurgun.skyfit.feature.dashboard.component.MobileDashboardHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeAction
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeEffect
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeUiState
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeViewModel
import com.vurgun.skyfit.feature.dashboard.home.desktop.FacilityHomeExtendedScreen
import com.vurgun.skyfit.feature.dashboard.home.desktop.MobileDashboardHomeFacilityGraph
import com.vurgun.skyfit.feature.dashboard.home.desktop.StatCardGrid
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

class FacilityHomeScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<FacilityHomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityHomeEffect.NavigateToConversations -> {
                    appNavigator.push(SharedScreen.Conversations)
                }

                FacilityHomeEffect.NavigateToManageLessons -> {
                    appNavigator.push(SharedScreen.FacilityManageLessons)
                }

                FacilityHomeEffect.NavigateToNotifications -> {
                    appNavigator.push(SharedScreen.NotificationsCompact)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            FacilityHomeUiState.Loading ->
                FullScreenLoaderContent()

            is FacilityHomeUiState.Error -> {
                val message = (uiState as FacilityHomeUiState.Error).message
                ErrorScreen(
                    message = message,
                    confirmText = stringResource(Res.string.refresh_action),
                    onConfirm = { viewModel.loadData() }
                )
            }

            is FacilityHomeUiState.Content -> {
                val content = uiState as FacilityHomeUiState.Content

                when(windowSize) {
                    WindowSize.COMPACT,
                    WindowSize.MEDIUM,
                    WindowSize.EXPANDED -> FacilityHomeExtendedScreen(content, viewModel::onAction)
                }
            }
        }
    }
}

@Composable
private fun FacilityHomeCompact(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            MobileHomeTopBar(
                notificationsEnabled = false,
                onClickNotifications = { onAction(FacilityHomeAction.OnClickNotifications) },
                conversationsEnabled = false,
                onClickConversations = { onAction(FacilityHomeAction.OnClickConversations) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            FacilityDashboardAppointments(content, onAction)

            MobileDashboardHomeFacilityStatisticsComponent()

            MobileDashboardHomeTrainerClassScheduleComponent()
            Spacer(Modifier.height(128.dp))
        }
    }
}

@Composable
internal fun FacilityDashboardAppointments(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {
    if (content.appointments.isEmpty()) {
        EmptyFacilityAppointmentContent(
            assignedFacilityId = content.facility.gymId,
            onClickAdd = { onAction(FacilityHomeAction.OnClickLessons) }
        )
    } else {
        MobileDashboardHomeUpcomingAppointmentsComponent(
            appointments = content.appointments,
            onClickShowAll = { onAction(FacilityHomeAction.OnClickLessons) }
        )
    }
}

@Composable
fun MobileDashboardHomeFacilityStatisticsComponent() {
    val stats = listOf(
        HomeFacilityStat("Aktif Üye", "327", "+53%", true),
        HomeFacilityStat("Aktif Dersler", "12", "0%", null),
        HomeFacilityStat("SkyFit Kazancın", "₺3120", "+53%", true),
        HomeFacilityStat("Profil Görüntülenmesi", "213", "-2%", false)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        StatCardGrid(stats)

        MobileDashboardHomeFacilityGraph()
    }
}

@Composable
private fun FacilityGridStatsLayout(stats: List<HomeFacilityStat>) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stats.subList(0, 2).forEach { MobileDashboardHomeFacilityStatCard(it) }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            stats.subList(2, 4).forEach { MobileDashboardHomeFacilityStatCard(it) }
        }
    }
}


@Composable
private fun MobileDashboardHomeFacilityStatCard(stat: HomeFacilityStat) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stat.title, fontSize = 14.sp, color = Color.Gray)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stat.value, fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            stat.percentage?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            if (stat.isPositive == true) Color.Green.copy(alpha = 0.2f) else Color.Red.copy(
                                alpha = 0.2f
                            )
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = it, fontSize = 12.sp, color = if (stat.isPositive == true) Color.Green else Color.Red)
                }
            }
        }
    }
}

data class HomeFacilityStat(
    val title: String,
    val value: String,
    val percentage: String?,
    val isPositive: Boolean?
)
