package com.vurgun.skyfit.feature.settings.trainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.ui.core.components.menu.SettingsSwitchOptionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrainerNotificationSettingsViewModel : ViewModel() {
    private val _options = MutableStateFlow<List<SettingsSwitchOptionItem>>(emptyList())
    val options: StateFlow<List<SettingsSwitchOptionItem>> = _options

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            // Simulate fetching data from repository or local storage
            _options.value = listOf(
                SettingsSwitchOptionItem("Ders Hatırlatmaları", "Yaklaşan derslerin bir gün önceden bildirimini al", true),
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
        viewModelScope.launch {
            _options.update { list ->
                list.map {
                    if (it == option) it.copy(isEnabled = isEnabled) else it
                }
            }
        }
    }
}