package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserMeasurementsScreen(navigator: Navigator) {

    var measurements = listOf(
        "YMCA Submaximal Step Testi",
        "2YMCA Submaximal Step Testi",
        "3YMCA Submaximal Step Testi"
    )

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                SkyFitScreenHeader(
                    title = "Ölçümlerim",
                    onBackClick = { navigator.popBackStack() }
                )
                Spacer(Modifier.height(8.dp))
                MobileUserMeasurementsScreenSearchComponent()
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 24.dp)
        ) {
            item {
                Text(
                    text = "En Son Güncellenen",
                    style = SkyFitTypography.bodyMediumSemibold,
                    color = SkyFitColor.text.secondary,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(measurements) {
                MeasurementItemComponent(it, onClick = {

                })
            }
        }
    }
}

@Composable
private fun MobileUserMeasurementsScreenSearchComponent() {
    TodoBox("MobileUserMeasurementsScreenSearchComponent", Modifier.size(382.dp, 44.dp))
}

@Composable
private fun MeasurementItemComponent(item: String, onClick: () -> Unit) {
    Column {
        Row(Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = item,
                style = SkyFitTypography.bodyMediumSemibold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Enter",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(16.dp)
            )
        }
        Divider(Modifier.fillMaxWidth().height(1.dp), color = SkyFitColor.border.default)
    }
}