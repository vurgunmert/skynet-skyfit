package com.vurgun.skyfit.feature_onboarding.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.domain.models.FitnessTagType
import com.vurgun.skyfit.core.domain.models.GenderType
import com.vurgun.skyfit.core.domain.models.GoalType
import com.vurgun.skyfit.core.domain.models.HeightUnitType
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.core.domain.models.WeightUnitType
import com.vurgun.skyfit.core.ui.viewdata.BodyTypeViewData
import com.vurgun.skyfit.core.ui.viewdata.CharacterTypeViewData
import com.vurgun.skyfit.feature_onboarding.data.OnboardingRequest
import com.vurgun.skyfit.feature_onboarding.domain.model.OnboardingResult
import com.vurgun.skyfit.feature_onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val onboardingRepository: OnboardingRepository,
    val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characterTypes = listOf(CharacterTypeViewData.Carrot, CharacterTypeViewData.Koala, CharacterTypeViewData.Panda)

    private val _state = MutableStateFlow(UserOnboardingState())
    val state = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // ✅ **State Update Functions**
    fun updateUserType(userType: UserType) {
        _state.value = _state.value.copy(userType = userType)
    }

    fun updateCharacter(character: CharacterTypeViewData) {
        _state.value = _state.value.copy(character = character)
    }

    fun updateMonth(value: Int) {
        _state.value = _state.value.copy(birthMonth = value)
    }

    fun updateDay(value: Int) {
        _state.value = _state.value.copy(birthDay = value)
    }

    fun updateYear(value: Int) {
        _state.value = _state.value.copy(birthYear = value)
    }

    fun updateGender(gender: GenderType) {
        _state.value = _state.value.copy(gender = gender)
    }

    fun updateWeight(weight: Int) {
        _state.value = _state.value.copy(weight = weight)
    }

    fun updateWeightUnit(unit: WeightUnitType) {
        _state.value = _state.value.copy(weightUnit = unit)
    }

    fun updateHeight(height: Int) {
        _state.value = _state.value.copy(height = height)
    }

    fun updateHeightUnit(unit: HeightUnitType) {
        _state.value = _state.value.copy(heightUnit = unit)
    }

    fun updateBodyType(bodyType: BodyTypeViewData) {
        _state.value = _state.value.copy(bodyType = bodyType)
    }

    fun updateGoals(goals: Set<GoalType>) {
        _state.value = _state.value.copy(goals = goals)
    }

    fun updateFirstName(value: String) {
        _state.value = _state.value.copy(firstName = value)
    }

    fun updateLastName(value: String) {
        _state.value = _state.value.copy(lastName = value)
    }

    fun updateBiography(biography: String) {
        _state.value = _state.value.copy(bio = biography)
    }

    fun updateFacilityName(facilityName: String) {
        _state.value = _state.value.copy(facilityName = facilityName)
    }

    fun updateBackgroundImage(image: ByteArray) {
        _state.value = _state.value.copy(backgroundImage = image)
    }

    fun updateFacilityAddress(facilityAddress: String) {
        _state.value = _state.value.copy(facilityAddress = facilityAddress)
    }

    fun updateFacilityBiography(facilityBiography: String) {
        _state.value = _state.value.copy(facilityBiography = facilityBiography)
    }

    fun updateFacilityProfileTags(profileTags: List<FitnessTagType>) {
        _state.value = _state.value.copy(profileTags = profileTags)
    }

    // ✅ **Submit Request to Repository**
    fun submitFields() {
        val currentState = _state.value

        val birthDay = currentState.birthYear?.let { year ->
            currentState.birthMonth?.let { month ->
                currentState.birthDay?.let { day ->
                    "$year-$month-$day 00:00:00"
                }
            }
        }

        val request = OnboardingRequest(
            userType = currentState.userType.id,
            characterId = currentState.character?.id,
            birthdate = birthDay,
            gender = currentState.gender?.id,
            weight = currentState.weight,
            weightUnit = currentState.weightUnit.id,
            height = currentState.height,
            heightUnit = currentState.heightUnit.id,
            bodyTypeId = currentState.bodyType?.id,
            name = currentState.firstName,
            surname = currentState.lastName,
            gymName = currentState.facilityName,
            gymAddress = currentState.facilityAddress,
            bio = currentState.bio,
            backgroundImage = currentState.backgroundImage,
            profileTags = currentState.profileTags?.map { it.id },
            goals = currentState.goals?.map { it.id }
        )

        viewModelScope.launch {
            _isLoading.value = true

            when(onboardingRepository.completeOnboarding(request)) {
                is OnboardingResult.Error -> {
                    _isLoading.value = false
                }
                OnboardingResult.Success -> {
                    _isLoading.value = false
                }
            }
        }
    }
}


data class UserOnboardingState(
    val userType: UserType = UserType.User,
    val character: CharacterTypeViewData? = null,
    val birthYear: Int? = null,
    val birthMonth: Int? = null,
    val birthDay: Int? = null,
    val gender: GenderType = GenderType.MALE,
    val weight: Int? = null,
    val weightUnit: WeightUnitType = WeightUnitType.KG,
    val height: Int? = null,
    val heightUnit: HeightUnitType = HeightUnitType.CM,
    val bodyType: BodyTypeViewData? = null,
    val goals: Set<GoalType>? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val facilityName: String? = null,
    val backgroundImage: ByteArray? = null,
    val facilityAddress: String? = null,
    val facilityBiography: String? = null,
    val profileTags: List<FitnessTagType>? = null
)
