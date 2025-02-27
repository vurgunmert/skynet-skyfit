package com.vurgun.skyfit.feature_onboarding.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrainerOnboardingViewModel : BaseOnboardingViewModel() {
    private val _state = MutableStateFlow(TrainerOnboardingState())
    val state = _state.asStateFlow()

    fun updateCharacter(characterId: String) {
        _state.value = _state.value.copy(characterId = characterId)
    }

    fun updateBirthYear(birthYear: Int) {
        _state.value = _state.value.copy(birthYear = birthYear)
    }

    fun updateGender(gender: String) {
        _state.value = _state.value.copy(gender = gender)
    }

    fun updateWeight(weight: Int) {
        _state.value = _state.value.copy(weight = weight)
    }

    fun updateWeightUnit(unit: String) {
        _state.value = _state.value.copy(weightUnit = unit)
    }

    fun updateHeight(height: Int) {
        _state.value = _state.value.copy(height = height)
    }

    fun updateHeightUnit(unit: String) {
        _state.value = _state.value.copy(heightUnit = unit)
    }

    fun updateBodyType(bodyType: String) {
        _state.value = _state.value.copy(bodyType = bodyType)
    }

    fun updateFullName(fullName: String) {
        _state.value = _state.value.copy(fullName = fullName)
    }

    fun updateFacilityName(facilityName: String) {
        _state.value = _state.value.copy(facilityName = facilityName)
    }

    fun updateBiography(biography: String) {
        _state.value = _state.value.copy(biography = biography)
    }

    fun saveTrainerData() {
        completeOnboarding() // Mark onboarding as completed
        // Call repository to save trainer data if needed
    }
}

data class TrainerOnboardingState(
    val characterId: String? = null,
    val birthYear: Int? = null,
    val gender: String? = null,
    val weight: Int? = null,
    val weightUnit: String? = null,
    val height: Int? = null,
    val heightUnit: String? = null,
    val bodyType: String? = null,
    val fullName: String = "",
    val facilityName: String = "",
    val biography: String = ""
)
