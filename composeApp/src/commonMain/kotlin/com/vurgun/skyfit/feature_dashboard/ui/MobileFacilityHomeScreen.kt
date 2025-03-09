package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityHomeScreen(rootNavigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileDashboardHomeToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            MobileDashboardHomeFacilityStatisticsComponent()

            MobileDashboardHomeFacilityNoClassComponent(
                onClick = { rootNavigator.jumpAndStay(NavigationRoute.FacilityManageLessons) }
            )

            MobileDashboardHomeUpcomingAppointmentsComponent()

            Spacer(Modifier.height(128.dp))
        }
    }
}

@Composable
fun MobileDashboardHomeFacilityNoClassComponent(onClick: () -> Unit) {
    MobileDashboardHomeTrainerNoClassComponent(onClickAdd = onClick)
}


@Composable
fun MobileDashboardHomeFacilityStatisticsComponent() {
    Column(
        modifier = Modifier
            .size(320.dp, 544.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        MobileDashboardHomeFacilityStatisticsOverview()
        Spacer(modifier = Modifier.height(16.dp))
        MobileDashboardHomeFacilityGraph()
    }
}

@Composable
private fun MobileDashboardHomeFacilityStatisticsOverview() {
    val stats = listOf(
        HomeFacilityStat("Aktif Üye", "327", "+53%", true),
        HomeFacilityStat("Aktif Dersler", "12", "0%", null),
        HomeFacilityStat("SkyFit Kazancın", "₺3120", "+53%", true),
        HomeFacilityStat("Profil Görüntülenmesi", "213", "-2%", false)
    )

    FacilityGridStatsLayout(stats)
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
                        .background(if (stat.isPositive == true) Color.Green.copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = it, fontSize = 12.sp, color = if (stat.isPositive == true) Color.Green else Color.Red)
                }
            }
        }
    }
}

@Composable
private fun MobileDashboardHomeFacilityGraph() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Üye Değişimi Grafiği", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
//            TimeFilterSelector()
        }
        Spacer(modifier = Modifier.height(8.dp))
        MemberChangeLineChart(
            dataPoints = listOf(12, 7, 15, 20, 14, 8, 10),
            labels = listOf("Pzts", "Sal", "Çar", "Per", "Cum", "Cmrts", "Paz")
        )
    }
}

private data class HomeFacilityStat(val title: String, val value: String, val percentage: String?, val isPositive: Boolean?)
