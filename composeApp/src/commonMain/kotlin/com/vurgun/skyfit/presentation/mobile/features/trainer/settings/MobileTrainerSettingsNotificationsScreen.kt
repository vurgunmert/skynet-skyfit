package com.vurgun.skyfit.presentation.mobile.features.trainer.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SettingsSwitchOptionItem
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSettingsSwitchOptionItemComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerSettingsNotificationsScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Bildirimler", onClickBack = { navigator.popBackStack() })
        }
    ) {
        MobileTrainerSettingsNotificationsScreenOptionsComponent()
    }
}

@Composable
private fun MobileTrainerSettingsNotificationsScreenOptionsComponent() {
    var options = listOf(
        SettingsSwitchOptionItem("Ders Hatırlatmaları", "Yaklaşan derslerin bir gün önceden bildirimini al", true),
        SettingsSwitchOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        SettingsSwitchOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        SettingsSwitchOptionItem("Yeni meydan okumalar", "", false),
        SettingsSwitchOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        SettingsSwitchOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        SettingsSwitchOptionItem("Yeni meydan okumalar", "", false),
        SettingsSwitchOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        SettingsSwitchOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        SettingsSwitchOptionItem("Yeni meydan okumalar", "", false),
        SettingsSwitchOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
        SettingsSwitchOptionItem("Günlük antrenman hatırlatıcıları", "", true),
        SettingsSwitchOptionItem("Yeni meydan okumalar", "", false)
    )

    LazyColumn(
        modifier = Modifier.padding(start = 33.dp, top = 32.dp, end = 23.dp, bottom = 24.dp).fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(options) {
            SkyFitSettingsSwitchOptionItemComponent(it, onChangeEnable = { })
        }
    }
}