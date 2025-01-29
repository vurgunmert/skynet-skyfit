package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSettingsNotificationsScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Bildirimler", onBackClick = { navigator.popBackStack() })
        }
    ) {
        MobileUserSettingsNotificationsScreenOptionsComponent()
    }
}

@Composable
private fun MobileUserSettingsNotificationsScreenOptionsComponent() {
    var options = listOf(
        NotificationSettingsOptionItem("Ders Hatırlatmaları", "Yaklaşan derslerin bir gün önceden bildirimini al", true),
        NotificationSettingsOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        NotificationSettingsOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        NotificationSettingsOptionItem("Yeni meydan okumalar", "", false),
        NotificationSettingsOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        NotificationSettingsOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        NotificationSettingsOptionItem("Yeni meydan okumalar", "", false),
        NotificationSettingsOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        NotificationSettingsOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        NotificationSettingsOptionItem("Yeni meydan okumalar", "", false),
        NotificationSettingsOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        NotificationSettingsOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        NotificationSettingsOptionItem("Yeni meydan okumalar", "", false)
    )

    LazyColumn(
        modifier = Modifier.padding(start = 33.dp, top = 32.dp, end = 23.dp, bottom = 24.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(options) {
            MobileUserSettingsNotificationsScreenOptionsComponent(it, onChangeEnable = { })
        }
    }
}

data class NotificationSettingsOptionItem(
    val title: String,
    val subtitle: String,
    val enabled: Boolean
)

@Composable
private fun MobileUserSettingsNotificationsScreenOptionsComponent(
    item: NotificationSettingsOptionItem,
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