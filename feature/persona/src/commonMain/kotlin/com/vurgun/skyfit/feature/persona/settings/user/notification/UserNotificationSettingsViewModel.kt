package com.vurgun.skyfit.feature.persona.settings.user.notification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.ui.components.menu.SettingsSwitchOptionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserNotificationSettingsViewModel : ScreenModel {

    private val _options = MutableStateFlow<List<SettingsSwitchOptionItem>>(emptyList())
    val options: StateFlow<List<SettingsSwitchOptionItem>> = _options

    init {
        loadData()
    }

    fun loadData() {
        screenModelScope.launch {
            // Simulate fetching data from repository or local storage
            _options.value = listOf(
                SettingsSwitchOptionItem("Ders Hatırlatmaları", "Yaklaşan derslerin bir gün önceden bildirimini al", true),
                SettingsSwitchOptionItem("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", false),
                SettingsSwitchOptionItem("Günlük antrenman hatırlatıcıları", "", true),
                SettingsSwitchOptionItem("Yeni meydan okumalar", "", false),
                SettingsSwitchOptionItem("Günlük ve haftalık hedefler", "", false),
                SettingsSwitchOptionItem("Yorumlar ve beğeniler", "", false),
                SettingsSwitchOptionItem("Topluluk bildirimleri", "", false),
                SettingsSwitchOptionItem("Başarılar ve rozetler", "", false)
            )
        }
    }

    fun updateOption(option: SettingsSwitchOptionItem, isEnabled: Boolean) {
        screenModelScope.launch {
            _options.update { list ->
                list.map {
                    if (it == option) it.copy(isEnabled = isEnabled) else it
                }
            }
        }
    }
}