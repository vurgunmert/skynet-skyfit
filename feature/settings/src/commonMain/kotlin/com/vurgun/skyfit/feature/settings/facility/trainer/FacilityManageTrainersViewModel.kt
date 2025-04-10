package com.vurgun.skyfit.feature.settings.facility.trainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.settings.model.Member
import com.vurgun.skyfit.data.settings.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class FacilityManageTrainersUiState(
    val filtered: List<Member> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)


internal class FacilityManageTrainersViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FacilityManageTrainersUiState())
    val uiState: StateFlow<FacilityManageTrainersUiState> = _uiState.asStateFlow()

    private var cached: List<Member> = emptyList()

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                filtered = applyQuery(cached, query)
            )
        }
    }

    fun refreshGymTrainers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            settingsRepository.getGymTrainers(gymId = 10).fold(
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
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            settingsRepository.deleteGymTrainer(gymId = 10, userId = trainerId).fold(
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

    private fun applyQuery(list: List<Member>, query: String): List<Member> {
        return if (query.isBlank()) list else {
            list.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.surname.contains(query, ignoreCase = true)
            }
        }
    }
}
