package com.vurgun.skyfit.settings.facility.packages

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.ui.model.ServicePackageUiData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class FacilityPackageListingUiState {
    object Loading : FacilityPackageListingUiState()
    data class Error(val message: String?) : FacilityPackageListingUiState()
    data class Content(
        val packages: List<ServicePackageUiData>
    ) : FacilityPackageListingUiState()
}

sealed class FacilityPackageListingAction {
    data object OnClickBack : FacilityPackageListingAction()
    data object OnClickNew : FacilityPackageListingAction()
    data object OnClickMembers : FacilityPackageListingAction()
    data class OnClickDeletePackage(val packageId: Int) : FacilityPackageListingAction()
    data class OnClickEditPackage(val lessonPackage: FacilityLessonPackageDTO) : FacilityPackageListingAction()
}

sealed class FacilityPackageListingEffect {
    object NavigateToBack : FacilityPackageListingEffect()
    object NavigateToNewPackage : FacilityPackageListingEffect()
    object NavigateToMembers : FacilityPackageListingEffect()
    data class NavigateToEditPackage(val lessonPackage: FacilityLessonPackageDTO) : FacilityPackageListingEffect()
}

class FacilityPackageListingViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
            ?: error("User is not a Facility!")


    private val _uiState =
        UiStateDelegate<FacilityPackageListingUiState>(FacilityPackageListingUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<FacilityPackageListingEffect>()
    val effect = _effect as SharedFlow<FacilityPackageListingEffect>

    fun onAction(action: FacilityPackageListingAction) {
        when (action) {
            FacilityPackageListingAction.OnClickBack ->
                _effect.emitIn(screenModelScope, FacilityPackageListingEffect.NavigateToBack)

            FacilityPackageListingAction.OnClickNew ->
                _effect.emitIn(screenModelScope, FacilityPackageListingEffect.NavigateToNewPackage)

            FacilityPackageListingAction.OnClickMembers ->
                _effect.emitIn(screenModelScope, FacilityPackageListingEffect.NavigateToMembers)

            is FacilityPackageListingAction.OnClickDeletePackage ->
                deletePackage(action.packageId)

            is FacilityPackageListingAction.OnClickEditPackage ->
                editPackage(action.lessonPackage)
        }
    }

    fun loadData() {
        screenModelScope.launch {

            runCatching {
                val packages = facilityRepository.getFacilityLessonPackages(facilityUser.gymId)
                    .getOrThrow()
                    .map { data ->
                        ServicePackageUiData(
                            id = data.packageId,
                            title = data.title,
                            courseList = data.packageContents,
                            memberCount = data.packageMemberCount,
                            duration = data.duration,
                            price = data.price,
                            onEditClick = { onAction(FacilityPackageListingAction.OnClickEditPackage(data)) },
                            onDeleteClick = { onAction(FacilityPackageListingAction.OnClickDeletePackage(data.packageId)) },
                            onMembersClick = { onAction(FacilityPackageListingAction.OnClickMembers) },
                        )
                    }

                _uiState.update(FacilityPackageListingUiState.Content(packages))
            }.onFailure { error ->
                _uiState.update(FacilityPackageListingUiState.Error(error.message))
            }
        }
    }

    private fun editPackage(lessonPackage: FacilityLessonPackageDTO) {
        _effect.emitIn(screenModelScope, FacilityPackageListingEffect.NavigateToEditPackage(lessonPackage))
    }

    private fun deletePackage(packageId: Int) {
        _uiState.update(FacilityPackageListingUiState.Loading)

        screenModelScope.launch {
            runCatching {
                facilityRepository.deleteFacilityLessonPackage(packageId)
                loadData()
            }.onFailure { error ->
                loadData()
            }
        }
    }
}
