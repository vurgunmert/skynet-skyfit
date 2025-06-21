package com.vurgun.skyfit.profile.user.measurements

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo

@Composable
fun MobileUserMeasurementsScreen(
    goToBack: () -> Unit
) {

    var measurements = listOf(
        "YMCA Submaximal Step Testi",
        "2YMCA Submaximal Step Testi",
        "3YMCA Submaximal Step Testi"
    )

    SkyFitMobileScaffold(
        topBar = {
            Column {
                CompactTopBar(
                    title = "Ölçümlerim",
                    onClickBack = goToBack
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
    SkyFitSearchTextInputComponent(hint = "Test Ara")
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
                painter = painterResource(Res.drawable.ic_app_logo),
                contentDescription = "Enter",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(16.dp)
            )
        }
        Divider(Modifier.fillMaxWidth().height(1.dp), color = SkyFitColor.border.default)
    }
}