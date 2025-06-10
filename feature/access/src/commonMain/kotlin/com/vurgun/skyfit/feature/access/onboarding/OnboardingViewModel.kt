package com.vurgun.skyfit.feature.access.onboarding

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingFormData
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingResult
import com.vurgun.skyfit.core.data.v1.domain.account.repository.AccountRepository
import com.vurgun.skyfit.core.data.v1.domain.auth.repository.AuthRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.*
import com.vurgun.skyfit.core.data.v1.domain.global.repository.GlobalRepository
import com.vurgun.skyfit.core.ui.model.BodyTypeViewData
import com.vurgun.skyfit.core.ui.model.CharacterTypeViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("ArrayInDataClass")
data class UserOnboardingState(
    val userRole: UserRole = UserRole.User,
    val character: CharacterTypeViewData? = null,
    val birthYear: Int? = null,
    val birthMonth: Int? = null,
    val birthDay: Int? = null,
    val gender: GenderType = GenderType.MALE,
    val weight: Int = 70,
    val weightUnit: WeightUnitType = WeightUnitType.KG,
    val height: Int = 170,
    val heightUnit: HeightUnitType = HeightUnitType.CM,
    val bodyType: BodyTypeViewData = BodyTypeViewData.MaleEctomorph,
    val goals: Set<UserGoal> = emptySet(),
    val firstName: String? = null,
    val lastName: String? = null,
    val bio: String? = null,
    val facilityName: String? = null,
    val backgroundImage: ByteArray? = null,
    val address: String? = null,
    val profileTags: List<ProfileTag>? = null
)

internal sealed class OnboardingViewEvent {
    data object Idle : OnboardingViewEvent()
    data object InProgress : OnboardingViewEvent()
    data object Completed : OnboardingViewEvent()
    data class Error(val message: String?) : OnboardingViewEvent()
    data object NavigateToLogin : OnboardingViewEvent()
}

internal data class SelectableUserRole(
    val userRole: UserRole,
    val enabled: Boolean
)

internal class OnboardingViewModel(
    private val accountRepository: AccountRepository,
    private val userManager: ActiveAccountManager,
    private val authRepository: AuthRepository,
    private val globalRepository: GlobalRepository
) : ScreenModel {

    private var isAccountAddition: Boolean = false
    private val _availableUserRoles = MutableStateFlow(
        listOf(
            SelectableUserRole(UserRole.User, true),
            SelectableUserRole(UserRole.Trainer, true),
            SelectableUserRole(UserRole.Facility, true)
        )
    )
    val availableUserRoles: StateFlow<List<SelectableUserRole>> = _availableUserRoles

    private val _availableGoals: MutableStateFlow<List<UserGoal>> = MutableStateFlow(emptyList())
    val availableGoals: StateFlow<List<UserGoal>> = _availableGoals

    private val _availableTags: MutableStateFlow<List<ProfileTag>> = MutableStateFlow(emptyList())
    val availableTags: StateFlow<List<ProfileTag>> = _availableTags

    init {
        screenModelScope.launch {
            val registeredRoles = userManager.getAccountTypes().map { UserRole.fromId(it.typeId) }

            _availableUserRoles.value = _availableUserRoles.value.map { selectable ->
                if (registeredRoles.contains(selectable.userRole)) {
                    selectable.copy(enabled = false)
                } else selectable
            }
            updateIsAccountAddition(_availableUserRoles.value.size < 3)

            _availableGoals.value = globalRepository.getGoals().getOrThrow()

            _availableTags.value = globalRepository.getProfileTags().getOrThrow()
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

    fun updateGoals(goals: Set<UserGoal>) {
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
        _uiState.value = _uiState.value.copy(bio = facilityBiography)
    }

    fun updateFacilityProfileTags(profileTags: List<ProfileTag>) {
        _uiState.value = _uiState.value.copy(profileTags = profileTags)
    }

    fun updateIsAccountAddition(value: Boolean) {
        isAccountAddition = value
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

        val request = AccountOnboardingFormData(
            userType = currentState.userRole.typeId,
            characterId = currentState.character?.id,
            birthdate = birthDay,
            gender = currentState.gender.id,
            weight = currentState.weight,
            weightUnit = currentState.weightUnit.id,
            height = currentState.height,
            heightUnit = currentState.heightUnit.id,
            bodyTypeId = currentState.bodyType.id,
            name = currentState.firstName,
            surname = currentState.lastName,
            gymName = currentState.facilityName,
            gymAddress = currentState.address,
            bio = currentState.bio,
            backgroundImage = currentState.backgroundImage,
            profileTags = (currentState.profileTags?.map { it.id.toIntOrNull() } ?: emptyList()) as List<Int>?, //TODO: Careful right there
            goals = currentState.goals.map { it.goalId }
        )

        screenModelScope.launch {
            _eventState.value = OnboardingViewEvent.InProgress

            when (val result = accountRepository.submitOnboarding(request, isAccountAddition)) {
                AccountOnboardingResult.Unauthorized -> {
                    _eventState.value = OnboardingViewEvent.NavigateToLogin
                }

                is AccountOnboardingResult.Error -> {
                    _eventState.value = OnboardingViewEvent.Error(result.message)
                }

                AccountOnboardingResult.Success -> {
                    userManager.updateUserType(currentState.userRole.typeId)
                    userManager.getActiveUser(true)
                    _eventState.value = OnboardingViewEvent.Completed
                }
            }
        }
    }

    fun clearError() {
        _eventState.value = OnboardingViewEvent.Idle
    }
}