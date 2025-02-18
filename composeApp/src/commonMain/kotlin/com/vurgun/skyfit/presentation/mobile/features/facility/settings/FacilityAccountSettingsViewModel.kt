package com.vurgun.skyfit.presentation.mobile.features.facility.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class FacilityAccountState(
    val biography: String? = null,
    val backgroundImageUrl: String? = null,
    val location: String? = null,
    val profileTags: List<String> = emptyList(),
    val isUpdated: Boolean = false
)

class FacilityAccountSettingsViewModel : ViewModel() {

    private val _accountState = MutableStateFlow(FacilityAccountState())
    val accountState: StateFlow<FacilityAccountState> = _accountState

    private var initialState: FacilityAccountState? = null

    fun loadData() {
        val initial = FacilityAccountState(
            biography = "At IronStudio Fitness, we’re all about building strength, confidence, and a community of like-minded individuals. Our expert trainers offer personalized programs in strength training, functional fitness, and overall wellness. Let's forge your fitness together!",
            profileTags = listOf("Kardiyo", "Kas Gelişimi", "Fonksiyonel Antrenman"),
            location = "1425 Maplewood Avenue, Apt 3B, Brookfield, IL 60513, USA",
            backgroundImageUrl = null
        )
        _accountState.value = initial
        initialState = initial
    }

    private fun updateState(update: FacilityAccountState.() -> FacilityAccountState) {
        _accountState.update {
            val newState = it.update().copy(isUpdated = it != initialState)
            newState
        }
    }

    fun updateBiography(value: String) {
        updateState { copy(biography = value) }
    }

    fun updateLocation(value: String) {
        updateState { copy(location = value) }
    }

    fun updateTags(tags: List<String>) {
        updateState { copy(profileTags = tags) }
    }

    fun saveChanges() {
        updateState { copy(isUpdated = false) }
        initialState = _accountState.value
    }

    fun deleteAccount() {
        // TODO: Implement account deletion logic
    }
}
