package com.vurgun.skyfit.feature.settings.facility.trainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.data.domain.model.MissingTokenException
import com.vurgun.skyfit.data.settings.domain.model.Trainer
import com.vurgun.skyfit.data.settings.domain.repository.TrainerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class FacilityAddTrainersUiState(
    val filtered: List<Trainer> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)

internal class FacilityAddTrainerViewModel(
    private val trainerRepository: TrainerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FacilityAddTrainersUiState())
    val uiState: StateFlow<FacilityAddTrainersUiState> = _uiState.asStateFlow()

    private var cached: List<Trainer> = emptyList()

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                filtered = applyQuery(cached, query)
            )
        }
    }

    fun refreshPlatformTrainers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            trainerRepository.getPlatformTrainers(gymId = 10).fold(
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

    fun addTrainer(trainerId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            trainerRepository.addFacilityTrainer(gymId = 10, userId = trainerId).fold(
                onSuccess = {
                    refreshPlatformTrainers()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            unauthorized = error is MissingTokenException,
                            error = error.message ?: "Failed to add trainer"
                        )
                    }
                }
            )
        }
    }

    private fun applyQuery(trainers: List<Trainer>, query: String): List<Trainer> {
        return if (query.isBlank()) trainers else {
            trainers.filter { it.fullName.contains(query, ignoreCase = true) }
        }
    }
}
