package com.vurgun.skyfit.feature_onboarding.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FacilityOnboardingViewModel : BaseOnboardingViewModel() {
    private val _state = MutableStateFlow(FacilityOnboardingState())
    val state = _state.asStateFlow()

    fun updateFacilityName(facilityName: String) {
        _state.value = _state.value.copy(facilityName = facilityName)
    }

    fun updateFacilityAddress(facilityAddress: String) {
        _state.value = _state.value.copy(facilityAddress = facilityAddress)
    }

    fun updateFacilityBiography(facilityBiography: String) {
        _state.value = _state.value.copy(facilityBiography = facilityBiography)
    }

    fun updateFacilityProfileTags(profileTags: List<String>) {
        _state.value = _state.value.copy(profileTags = profileTags)
    }

    fun saveFacilityData() {
        completeOnboarding() // Mark onboarding as completed
        // Call repository to save facility data if needed
    }
}

data class FacilityOnboardingState(
    val facilityName: String? = null,
    val facilityAddress: String? = null,
    val facilityBiography: String? = null,
    val profileTags: List<String> = emptyList()
)
