package com.vurgun.skyfit.feature.settings.facility.member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.data.domain.model.MissingTokenException
import com.vurgun.skyfit.data.settings.domain.model.Member
import com.vurgun.skyfit.data.settings.domain.repository.MemberRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class FacilityAddMembersUiState(
    val filtered: List<Member> = emptyList(),
    val query: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val unauthorized: Boolean = false
)

internal class FacilityAddMembersViewModel(
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FacilityAddMembersUiState())
    val uiState: StateFlow<FacilityAddMembersUiState> = _uiState.asStateFlow()

    private var cached: List<Member> = emptyList()

    fun updateSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                query = query,
                filtered = applyQuery(cached, query)
            )
        }
    }

    fun refreshPlatformMembers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            memberRepository.getPlatformMembers(gymId = 10).fold(
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

    fun addMember(memberId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            memberRepository.addMemberToFacility(gymId = 10, userId = memberId).fold(
                onSuccess = {
                    refreshPlatformMembers()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            unauthorized = error is MissingTokenException,
                            error = error.message ?: "Failed to add member"
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
