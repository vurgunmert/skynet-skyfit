package com.vurgun.skyfit.feature.persona.settings.facility.member

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import com.vurgun.skyfit.core.data.v1.domain.global.model.MissingTokenException
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
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val facilityUser: FacilityAccount
        get() = userManager.user.value as? FacilityAccount
            ?: error("User is not a Facility!")

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
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, unauthorized = false) }

            facilityRepository.getPlatformMembers(gymId = facilityUser.gymId).fold(
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
        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            facilityRepository.addMemberToFacility(gymId = facilityUser.gymId, userId = memberId).fold(
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
