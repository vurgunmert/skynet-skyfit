package com.vurgun.skyfit.feature.persona.settings.facility.packages

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.access.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.persona.data.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.persona.domain.repository.FacilityRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutTag
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class FacilityPackageEditUiState {
    data object Loading : FacilityPackageEditUiState()
    data class Error(val message: String?) : FacilityPackageEditUiState()
    data class Content(
        val tags: List<WorkoutTag>,
        val branches: List<String>
    ) : FacilityPackageEditUiState()
}

data class FacilityPackageEditFormState(
    val isReadyToSave: Boolean = false,
    val packageId: Int? = null,
    val title: String? = null,
    val lessonCount: String? = null,
    val tags: List<WorkoutTag> = emptyList(),
    val description: String? = null,
    val monthCount: String? = null,
    val branch: String? = null,
    val price: Float = 0.0f,
) {
    val isUpdateMode = packageId != null
}

sealed class FacilityPackageEditAction {
    data object OnClickBack : FacilityPackageEditAction()
    data object OnClickSave : FacilityPackageEditAction()
    data class OnTitleChanged(val title: String) : FacilityPackageEditAction()
    data class OnLessonCountChanged(val lessonCount: String) : FacilityPackageEditAction()
    data class OnServicesChanged(val services: List<WorkoutTag>) : FacilityPackageEditAction()
    data class OnDescriptionChanged(val description: String) : FacilityPackageEditAction()
    data class OnDurationMonthChanged(val monthCount: String) : FacilityPackageEditAction()
    data class OnBranchChanged(val branch: String) : FacilityPackageEditAction()
    data class OnPriceChanged(val price: Float) : FacilityPackageEditAction()
}

sealed class FacilityPackageEditEffect {
    object NavigateToBack : FacilityPackageEditEffect()
}

class FacilityPackageEditViewModel(
    private val userManager: UserManager,
    private val authRepository: AuthRepository,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility!")

    private val _uiState = UiStateDelegate<FacilityPackageEditUiState>(FacilityPackageEditUiState.Loading)
    val uiState: StateFlow<FacilityPackageEditUiState> = _uiState.asStateFlow()

    private val _formState = MutableStateFlow(FacilityPackageEditFormState())
    val formState: StateFlow<FacilityPackageEditFormState> = _formState

    private val _effect = SingleSharedFlow<FacilityPackageEditEffect>()
    val effect = _effect as SharedFlow<FacilityPackageEditEffect>

    fun onAction(action: FacilityPackageEditAction) {
        when (action) {
            FacilityPackageEditAction.OnClickBack ->
                _effect.emitIn(screenModelScope, FacilityPackageEditEffect.NavigateToBack)

            is FacilityPackageEditAction.OnBranchChanged -> {
                _formState.update { it.copy(branch = action.branch) }
            }

            is FacilityPackageEditAction.OnDescriptionChanged -> {
                _formState.update { it.copy(description = action.description) }
            }

            is FacilityPackageEditAction.OnLessonCountChanged -> {
                _formState.update { it.copy(lessonCount = action.lessonCount) }
            }

            is FacilityPackageEditAction.OnPriceChanged -> {
                _formState.update { it.copy(price = action.price) }
            }

            is FacilityPackageEditAction.OnServicesChanged -> {
                _formState.update { it.copy(tags = action.services) }
            }

            is FacilityPackageEditAction.OnDurationMonthChanged -> {
                _formState.update { it.copy(monthCount = action.monthCount) }
            }

            is FacilityPackageEditAction.OnTitleChanged -> {
                _formState.update { it.copy(title = action.title) }
            }

            FacilityPackageEditAction.OnClickSave -> saveService()
        }
        checkIfReadyToSave()
    }

    fun loadData(lessonPackage: FacilityLessonPackageDTO? = null) {
        screenModelScope.launch {
            runCatching {
                val tags = authRepository.getTags().getOrThrow()
                val branches = listOf("Test Şube 1", "Test Şube 2")

                if (lessonPackage != null) {
                    _formState.value = FacilityPackageEditFormState(
                        isReadyToSave = false,
                        packageId = lessonPackage.packageId,
                        title = lessonPackage.title,
                        lessonCount = lessonPackage.lessonCount.toString(),
                        tags = tags.filter { it.tagName in lessonPackage.packageContents },
                        description = lessonPackage.description,
                        monthCount = lessonPackage.duration.toString(),
                        branch = "",
                        price = lessonPackage.price
                    )
                    checkIfReadyToSave()
                }

                _uiState.update(
                    FacilityPackageEditUiState.Content(
                        tags = tags,
                        branches = branches
                    )
                )

            }.onFailure { error ->
                _uiState.update(FacilityPackageEditUiState.Error(error.message))
            }
        }
    }

    private fun checkIfReadyToSave() {
        _formState.update { state ->

            val isReadyToSave =
                !state.title.isNullOrEmpty() &&
                        !state.lessonCount.isNullOrEmpty() &&
                        state.tags.isNotEmpty() &&
                        !state.monthCount.isNullOrEmpty() &&
                        !state.branch.isNullOrEmpty()

            state.copy(isReadyToSave = isReadyToSave)
        }
    }

    fun saveService() {
        _uiState.update(FacilityPackageEditUiState.Loading)
        screenModelScope.launch {
            runCatching {
                val formState = formState.value

                if (formState.packageId != null) {
                    facilityRepository.updateLessonPackage(
                        packageId = formState.packageId,
                        gymId = facilityUser.gymId,
                        title = formState.title.orEmpty(),
                        contentIds = formState.tags.map { it.tagId },
                        description = formState.description.orEmpty(),
                        lessonCount = formState.lessonCount?.toIntOrNull() ?: 1,
                        duration = formState.monthCount?.toIntOrNull() ?: 1,
                        price = formState.price
                    )
                } else {
                    facilityRepository.createLessonPackage(
                        gymId = facilityUser.gymId,
                        title = formState.title.orEmpty(),
                        contentIds = formState.tags.map { it.tagId },
                        description = formState.description.orEmpty(),
                        lessonCount = formState.lessonCount?.toIntOrNull() ?: 1,
                        duration = formState.monthCount?.toIntOrNull() ?: 1,
                        price = formState.price
                    )
                }
                _effect.emitIn(screenModelScope, FacilityPackageEditEffect.NavigateToBack)

            }.onFailure { error ->
                _uiState.update(FacilityPackageEditUiState.Error(error.message))
            }
        }
    }
}
