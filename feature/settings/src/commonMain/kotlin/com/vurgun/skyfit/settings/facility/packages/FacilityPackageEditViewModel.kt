package com.vurgun.skyfit.settings.facility.packages

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.data.v1.domain.workout.repository.WorkoutRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class FacilityPackageEditUiState {
    data object Loading : FacilityPackageEditUiState()
    data class Error(val message: String?) : FacilityPackageEditUiState()
    data class Content(
        val categories: List<LessonCategory>,
        val branches: List<String> = emptyList<String>()
    ) : FacilityPackageEditUiState()
}

data class FacilityPackageEditFormState(
    val isReadyToSave: Boolean = false,
    val packageId: Int? = null,
    val title: String? = null,
    val lessonCount: String? = null,
    val categories: List<LessonCategory> = emptyList(),
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
    data class OnServicesChanged(val services: List<LessonCategory>) : FacilityPackageEditAction()
    data class OnDescriptionChanged(val description: String) : FacilityPackageEditAction()
    data class OnDurationMonthChanged(val monthCount: String) : FacilityPackageEditAction()
    data class OnBranchChanged(val branch: String) : FacilityPackageEditAction()
    data class OnPriceChanged(val price: Float) : FacilityPackageEditAction()
}

sealed class FacilityPackageEditEffect {
    object NavigateToBack : FacilityPackageEditEffect()
}

class FacilityPackageEditViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
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
                _formState.update { it.copy(categories = action.services) }
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

                val categories = facilityRepository.getLessonCategories(facilityUser.gymId).getOrDefault(emptyList())

                if (lessonPackage != null) {
                    _formState.value = FacilityPackageEditFormState(
                        isReadyToSave = false,
                        packageId = lessonPackage.packageId,
                        title = lessonPackage.title,
                        lessonCount = lessonPackage.lessonCount.toString(),
                        categories = categories.filter { it.name in lessonPackage.packageContents },
                        description = lessonPackage.description,
                        monthCount = lessonPackage.duration.toString(),
                        branch = "",
                        price = lessonPackage.price
                    )
                    checkIfReadyToSave()
                }

                _uiState.update(FacilityPackageEditUiState.Content(categories = categories)
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
                        state.categories.isNotEmpty() &&
                        !state.monthCount.isNullOrEmpty()

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
                        contentIds = formState.categories.map { it.id },
                        description = formState.description.orEmpty(),
                        lessonCount = formState.lessonCount?.toIntOrNull() ?: 1,
                        duration = formState.monthCount?.toIntOrNull() ?: 1,
                        price = formState.price
                    )
                } else {
                    facilityRepository.createLessonPackage(
                        gymId = facilityUser.gymId,
                        title = formState.title.orEmpty(),
                        contentIds = formState.categories.map { it.id },
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
