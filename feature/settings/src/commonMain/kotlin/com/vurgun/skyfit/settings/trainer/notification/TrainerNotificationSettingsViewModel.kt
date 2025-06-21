package com.vurgun.skyfit.settings.trainer.notification

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.ui.components.menu.NotificationSettingsOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainerNotificationSettingsViewModel : ScreenModel {
    private val _options = MutableStateFlow<List<NotificationSettingsOption>>(emptyList())
    val options: StateFlow<List<NotificationSettingsOption>> = _options

    init {
        loadData()
    }

    fun loadData() {
        screenModelScope.launch {
            // Simulate fetching data from repository or local storage
            _options.value = listOf(
                NotificationSettingsOption("Ders Hatırlatmaları", "Yaklaşan derslerin bir gün önceden bildirimini al", true),
                NotificationSettingsOption("Günlük antrenman hatırlatıcıları", "", true),
                NotificationSettingsOption("Yeni meydan okumalar", "", false),
                NotificationSettingsOption("Günlük ve haftalık hedefler", "", false),
                NotificationSettingsOption("Yorumlar ve beğeniler", "", false),
                NotificationSettingsOption("Topluluk bildirimleri", "", false),
                NotificationSettingsOption("Başarılar ve rozetler", "", false)
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