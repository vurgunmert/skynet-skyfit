package com.vurgun.skyfit.feature.settings.facility

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

class FacilityManageMembersViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ManageMembersUiState())
    val uiState: StateFlow<ManageMembersUiState> = _uiState.asStateFlow()

    private var cached: List<Member> = emptyList()

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                filtered = applyQuery(cached, query)
            )
        }
    }

    fun refreshGymMembers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            settingsRepository.getGymMembers(gymId = 10).fold(
                onSuccess = { members ->
                    cached = members
                    _uiState.update {
                        it.copy(
                            filtered = applyQuery(members, it.query),
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

    fun deleteMember(memberId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            settingsRepository.deleteGymMember(gymId = 10, userId = memberId).fold(
                onSuccess = {
                    refreshGymMembers()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            unauthorized = error is MissingTokenException,
                            error = error.message ?: "Failed to remove member"
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
