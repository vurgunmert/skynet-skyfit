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

internal data class FacilityAddTrainersUiState(
    val filtered: List<Member> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)

internal class FacilityAddTrainerViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FacilityAddTrainersUiState())
    val uiState: StateFlow<FacilityAddTrainersUiState> = _uiState.asStateFlow()

    private var cached: List<Member> = emptyList()

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

            settingsRepository.getPlatformTrainers(gymId = 10).fold(
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

            settingsRepository.addGymTrainer(gymId = 10, userId = trainerId).fold(
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

    private fun applyQuery(members: List<Member>, query: String): List<Member> {
        return if (query.isBlank()) members else {
            members.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.surname.contains(query, ignoreCase = true)
            }
        }
    }
}
