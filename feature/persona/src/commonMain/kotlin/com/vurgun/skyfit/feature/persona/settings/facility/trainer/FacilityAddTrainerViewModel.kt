package com.vurgun.skyfit.feature.persona.settings.facility.trainer

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

internal data class FacilityAddTrainersUiState(
    val filtered: List<TrainerPreview> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)

internal class FacilityAddTrainerViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val facilityUser: FacilityAccount
        get() = userManager.user.value as? FacilityAccount
            ?: error("User is not a Facility")

    private val _uiState = MutableStateFlow(FacilityAddTrainersUiState())
    val uiState: StateFlow<FacilityAddTrainersUiState> = _uiState.asStateFlow()

    private var cached: List<TrainerPreview> = emptyList()

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                filtered = applyQuery(cached, query)
            )
        }
    }

    fun refreshPlatformTrainers() {
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            facilityRepository.getPlatformTrainers(gymId = facilityUser.gymId).fold(
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
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            facilityRepository.addFacilityTrainer(gymId = facilityUser.gymId, userId = trainerId).fold(
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

    private fun applyQuery(trainers: List<TrainerPreview>, query: String): List<TrainerPreview> {
        return if (query.isBlank()) trainers else {
            trainers.filter { it.fullName.contains(query, ignoreCase = true) }
        }
    }
}
