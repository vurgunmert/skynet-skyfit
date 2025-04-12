package com.vurgun.skyfit.feature.onboarding.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.onboarding.OnboardingRepository
import com.vurgun.skyfit.data.onboarding.OnboardingRequest
import com.vurgun.skyfit.data.onboarding.OnboardingResult
import com.vurgun.skyfit.data.core.domain.model.FitnessTagType
import com.vurgun.skyfit.data.core.domain.model.GenderType
import com.vurgun.skyfit.data.core.domain.model.GoalType
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.ui.core.viewdata.BodyTypeViewData
import com.vurgun.skyfit.ui.core.viewdata.CharacterTypeViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal sealed class OnboardingViewEvent {
    data object Idle: OnboardingViewEvent()
    data object InProgress: OnboardingViewEvent()
    data object Completed: OnboardingViewEvent()
    data class Error(val message: String?): OnboardingViewEvent()
    data object NavigateToLogin: OnboardingViewEvent()
}

internal data class SelectableUserRole(
    val userRole: UserRole,
    val enabled: Boolean
)

internal class OnboardingViewModel(
    private val onboardingRepository: OnboardingRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _availableUserRoles = MutableStateFlow(
        listOf(
            SelectableUserRole(UserRole.User, true),
            SelectableUserRole(UserRole.Trainer, true),
            SelectableUserRole(UserRole.Facility, true)
        )
    )
    val availableUserRoles: StateFlow<List<SelectableUserRole>> = _availableUserRoles

    init {
        viewModelScope.launch {
            val result = userRepository.getUserTypes()
            val registeredRoles = result
                .getOrNull()
                ?.map { UserRole.fromId(it.typeId) }
                ?: emptyList()

            _availableUserRoles.value = _availableUserRoles.value.map { selectable ->
                if (registeredRoles.contains(selectable.userRole)) {
                    selectable.copy(enabled = false)
                } else selectable
            }
        }
    }

    val characterTypes = listOf(CharacterTypeViewData.Carrot, CharacterTypeViewData.Koala, CharacterTypeViewData.Panda)

    private val _uiState = MutableStateFlow(UserOnboardingState())
    val uiState = _uiState.asStateFlow()

    private val _eventState = MutableStateFlow<OnboardingViewEvent>(OnboardingViewEvent.Idle)
    val eventState = _eventState.asStateFlow()

    fun updateUserType(userRole: UserRole) {
        _uiState.value = _uiState.value.copy(userRole = userRole)
    }

    fun updateCharacter(character: CharacterTypeViewData) {
        _uiState.value = _uiState.value.copy(character = character)
    }

    fun updateMonth(value: Int) {
        _uiState.value = _uiState.value.copy(birthMonth = value)
    }

    fun updateDay(value: Int) {
        _uiState.value = _uiState.value.copy(birthDay = value)
    }

    fun updateYear(value: Int) {
        _uiState.value = _uiState.value.copy(birthYear = value)
    }

    fun updateGender(gender: GenderType) {
        _uiState.value = _uiState.value.copy(gender = gender)
    }

    fun updateWeight(weight: Int) {
        _uiState.value = _uiState.value.copy(weight = weight)
    }

    fun updateWeightUnit(unit: WeightUnitType) {
        _uiState.value = _uiState.value.copy(weightUnit = unit)
    }

    fun updateHeight(height: Int) {
        _uiState.value = _uiState.value.copy(height = height)
    }

    fun updateHeightUnit(unit: HeightUnitType) {
        _uiState.value = _uiState.value.copy(heightUnit = unit)
    }

    fun updateBodyType(bodyType: BodyTypeViewData) {
        _uiState.value = _uiState.value.copy(bodyType = bodyType)
    }

    fun updateGoals(goals: Set<GoalType>) {
        _uiState.value = _uiState.value.copy(goals = goals)
    }

    fun updateFirstName(value: String) {
        _uiState.value = _uiState.value.copy(firstName = value)
    }

    fun updateLastName(value: String) {
        _uiState.value = _uiState.value.copy(lastName = value)
    }

    fun updateBiography(biography: String) {
        _uiState.value = _uiState.value.copy(bio = biography)
    }

    fun updateFacilityName(facilityName: String) {
        _uiState.value = _uiState.value.copy(facilityName = facilityName)
    }

    fun updateBackgroundImage(image: ByteArray) {
        _uiState.value = _uiState.value.copy(backgroundImage = image)
    }

    fun updateFacilityAddress(facilityAddress: String) {
        _uiState.value = _uiState.value.copy(address = facilityAddress)
    }

    fun updateFacilityBiography(facilityBiography: String) {
        _uiState.value = _uiState.value.copy(biography = facilityBiography)
    }

    fun updateFacilityProfileTags(profileTags: List<FitnessTagType>) {
        _uiState.value = _uiState.value.copy(profileTags = profileTags)
    }

    fun submitRequest() {
        val currentState = _uiState.value

        val birthDay = currentState.birthYear?.let { year ->
            currentState.birthMonth?.let { month ->
                currentState.birthDay?.let { day ->
                    "$year-$month-$day 00:00:00"
                }
            }
        }

        val request = OnboardingRequest(
            userType = currentState.userRole.typeId,
            characterId = currentState.character?.id,
            birthdate = birthDay,
            gender = currentState.gender.id,
            weight = currentState.weight,
            weightUnit = currentState.weightUnit.id,
            height = currentState.height,
            heightUnit = currentState.heightUnit.id,
            bodyTypeId = currentState.bodyType?.id,
            name = currentState.firstName,
            surname = currentState.lastName,
            gymName = currentState.facilityName,
            gymAddress = currentState.address,
            bio = currentState.bio,
            backgroundImage = currentState.backgroundImage,
            profileTags = currentState.profileTags?.map { it.id },
            goals = currentState.goals?.map { it.id }
        )

        viewModelScope.launch {
            _eventState.value = OnboardingViewEvent.InProgress

            when(val result = onboardingRepository.submitOnboarding(request)) {
                OnboardingResult.Unauthorized -> {
                    _eventState.value = OnboardingViewEvent.NavigateToLogin
                }
                is OnboardingResult.Error -> {
                    _eventState.value = OnboardingViewEvent.Error(result.message)
                }
                OnboardingResult.Success -> {
                    _eventState.value = OnboardingViewEvent.Completed
                }
            }
        }
    }

    fun clearError() {
        _eventState.value = OnboardingViewEvent.Idle
    }
}

@Suppress("ArrayInDataClass")
data class UserOnboardingState(
    val userRole: UserRole = UserRole.User,
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
    val address: String? = null,
    val biography: String? = null,
    val profileTags: List<FitnessTagType>? = null
)
