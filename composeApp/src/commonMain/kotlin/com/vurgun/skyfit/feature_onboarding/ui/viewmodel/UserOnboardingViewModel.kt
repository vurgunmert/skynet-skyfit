package com.vurgun.skyfit.feature_onboarding.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserOnboardingViewModel : BaseOnboardingViewModel() {
    private val _state = MutableStateFlow(LegacyUserOnboardingState())
    val state = _state.asStateFlow()

    fun updateCharacter(characterId: Int) {
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

    fun updateGoal(goal: String) {
        _state.value = _state.value.copy(goal = goal)
    }

    fun saveUserData() {
        completeOnboarding() // Mark onboarding as completed
        // Call repository to save user data if needed
    }
}

data class LegacyUserOnboardingState(
    val characterId: Int? = null,
    val birthYear: Int? = null,
    val gender: String? = null,
    val weight: Int? = null,
    val weightUnit: String? = null,
    val height: Int? = null,
    val heightUnit: String? = null,
    val bodyType: String? = null,
    val goal: String? = null
)
