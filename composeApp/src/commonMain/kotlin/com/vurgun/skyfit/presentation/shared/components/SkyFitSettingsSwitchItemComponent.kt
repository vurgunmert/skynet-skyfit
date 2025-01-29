package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor

data class SettingsSwitchOptionItem(
    val title: String,
    val subtitle: String,
    val enabled: Boolean
)

@Composable
fun SkyFitSettingsSwitchOptionItemComponent(
    item: SettingsSwitchOptionItem,
    onChangeEnable: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onChangeEnable.invoke(!item.enabled) },
        verticalAlignment = Alignment.Top
    ) {
        Column(Modifier.weight(1f)) {
            Text(item.title)
            Spacer(Modifier.height(4.dp))
            Text(item.subtitle)
        }
        Spacer(Modifier.width(16.dp))
        Switch(
            checked = item.enabled,
            onCheckedChange = onChangeEnable,
            colors = SwitchDefaults.colors(
                checkedThumbColor = SkyFitColor.icon.secondary,
                checkedTrackColor = SkyFitColor.specialty.buttonBgDisabled,
                uncheckedThumbColor = SkyFitColor.icon.default,
                uncheckedTrackColor = SkyFitColor.specialty.buttonBgRest,
            )
        )
    }
}