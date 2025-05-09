package com.vurgun.skyfit.feature.persona.settings.facility.trainer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.model.MissingTokenException
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.domain.model.Trainer
import com.vurgun.skyfit.core.data.domain.repository.TrainerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class FacilityManageTrainersUiState(
    val filtered: List<Trainer> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)

internal class FacilityManageTrainersViewModel(
    private val userManager: UserManager,
    private val trainerRepository: TrainerRepository
) : ScreenModel {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    private val _uiState = MutableStateFlow(FacilityManageTrainersUiState())
    val uiState: StateFlow<FacilityManageTrainersUiState> = _uiState.asStateFlow()

    private var cached: List<Trainer> = emptyList()

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

            trainerRepository.getFacilityTrainers(gymId = facilityUser.gymId).fold(
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

            trainerRepository.deleteFacilityTrainer(gymId = facilityUser.gymId, userId = trainerId).fold(
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

    private fun applyQuery(list: List<Trainer>, query: String): List<Trainer> {
        return if (query.isBlank()) list else {
            list.filter { it.fullName.contains(query, ignoreCase = true) }
        }
    }
}
