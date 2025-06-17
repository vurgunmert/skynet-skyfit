package com.vurgun.skyfit.core.ui.components.menu

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
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

data class NotificationSettingsOption(
    val title: String,
    val subtitle: String? = null,
    val isEnabled: Boolean = true
)

@Composable
fun NotificationSettingsItem(
    item: NotificationSettingsOption,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onToggle.invoke(!item.isEnabled) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(item.title, style = SkyFitTypography.bodyMediumMedium)
            if (!item.subtitle.isNullOrEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text(item.subtitle, style = SkyFitTypography.bodyMediumMedium, color = SkyFitColor.text.secondary)
            }
        }
        Spacer(Modifier.width(16.dp))
        Switch(
            checked = item.isEnabled,
            onCheckedChange = onToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = SkyFitColor.icon.secondary,
                checkedTrackColor = SkyFitColor.specialty.buttonBgDisabled,
                uncheckedThumbColor = SkyFitColor.icon.default,
                uncheckedTrackColor = SkyFitColor.specialty.buttonBgRest,
            )
        )
    }
}