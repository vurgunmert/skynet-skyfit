package com.vurgun.skyfit.settings.facility.notification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.ui.components.menu.NotificationSettingsOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FacilityNotificationSettingsViewModel : ScreenModel {
    private val _options = MutableStateFlow<List<NotificationSettingsOption>>(emptyList())
    val options: StateFlow<List<NotificationSettingsOption>> = _options

    init {
        loadData()
    }

    fun loadData() {
        screenModelScope.launch {
            // Simulate fetching data from repository or local storage
            _options.value = listOf(
                NotificationSettingsOption("Yaklaşan dersler", "Yaklaşan derslerin bir gün önceden bildirimini al", true),
                NotificationSettingsOption("Yeni üye", "Yeni üye katıldığında bildirim al", true),
                NotificationSettingsOption("Günlük antrenman hatırlatıcıları", "", true),
                NotificationSettingsOption("Yorumlar ve beğeniler", "", false),
                NotificationSettingsOption("Topluluk bildirimleri", "", false),
                NotificationSettingsOption("Antrenör Güncellemeleri", "Antrenörlerin ders iptali veya değişiklik duyuruları.", true)
            )
        }
    }

    fun updateOption(option: NotificationSettingsOption, isEnabled: Boolean) {
        screenModelScope.launch {
            _options.update { list ->
                list.map {
                    if (it == option) it.copy(isEnabled = isEnabled) else it
                }
            }
        }
    }
}