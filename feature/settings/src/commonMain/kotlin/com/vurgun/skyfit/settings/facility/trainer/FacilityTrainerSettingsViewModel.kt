package com.vurgun.skyfit.settings.facility.trainer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.MissingTokenException
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class FacilityManageTrainersUiState(
    val filtered: List<TrainerPreview> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)

internal class FacilityTrainerSettingsViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
            ?: error("User is not a Facility")

    private val _uiState = MutableStateFlow(FacilityManageTrainersUiState())
    val uiState: StateFlow<FacilityManageTrainersUiState> = _uiState.asStateFlow()

    private var cached: List<TrainerPreview> = emptyList()

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                filtered = applyQuery(cached, query)
            )
        }
    }

    fun refreshGymTrainers() {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            facilityRepository.getFacilityTrainers(gymId = facilityUser.gymId).fold(
                onSuccess = { trainers ->
                    cached = trainers
                    _uiState.update {
                        it.copy(
                            filtered = applyQuery(trainers, it.query),
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            filtered = emptyList(),
                            isLoading = false,
                            unauthorized = error is MissingTokenException,
                            error = error.message
                        )
                    }
                }
            )
        }
    }

    fun deleteTrainer(trainerId: Int) {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            facilityRepository.deleteFacilityTrainer(gymId = facilityUser.gymId, userId = trainerId).fold(
                onSuccess = {
                    refreshGymTrainers()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            unauthorized = error is MissingTokenException,
                            error = error.message ?: "Failed to delete trainer"
                        )
                    }
                }
            )
        }
    }

    private fun applyQuery(list: List<TrainerPreview>, query: String): List<TrainerPreview> {
        return if (query.isBlank()) list else {
            list.filter { it.fullName.contains(query, ignoreCase = true) }
        }
    }
}
