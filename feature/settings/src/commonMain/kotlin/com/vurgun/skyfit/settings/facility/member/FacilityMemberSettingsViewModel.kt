package com.vurgun.skyfit.settings.facility.member

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import kotlinx.coroutines.launch

sealed class FacilityMemberSettingsUiState {
    object Loading : FacilityMemberSettingsUiState()
    data class Content(
        val packages: List<FacilityLessonPackageDTO> = emptyList(),
        val members: List<Member> = emptyList(),
        val query: String = "",
        val isLoading: Boolean = false,
        val error: String? = null,
        val unauthorized: Boolean = false
    ) : FacilityMemberSettingsUiState()

    data class Error(val message: String) : FacilityMemberSettingsUiState()
}

internal class FacilityMemberSettingsViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityMemberSettingsUiState>(FacilityMemberSettingsUiState.Loading)
    internal val uiState = _uiState.asStateFlow()

    private var cached: List<Member> = emptyList()

    fun updateSearchQuery(query: String) {
        val content = uiState.value as? FacilityMemberSettingsUiState.Content ?: return

        _uiState.update(
            content.copy(
                query = query,
                members = applyQuery(cached, query)
            )
        )
    }

    private var activeFacilityId = 0

    fun loadData(facilityId: Int? = null) {
        activeFacilityId = facilityId ?: (userManager.account.value as? FacilityAccount)?.gymId ?: 0
        refreshGymMembers()
    }

    fun refreshGymMembers() {
        _uiState.update(FacilityMemberSettingsUiState.Loading)

        screenModelScope.launch {
            val packages = facilityRepository.getFacilityLessonPackages(gymId = activeFacilityId)
                .getOrDefault(emptyList())

            facilityRepository.getFacilityMembers(gymId = activeFacilityId).fold(
                onSuccess = { members ->
                    cached = members
                    _uiState.update(
                        FacilityMemberSettingsUiState.Content(
                            members = members,
                            packages = packages
                        )
                    )
                },
                onFailure = { error ->
                    error.printStackTrace()
                    _uiState.update(
                        FacilityMemberSettingsUiState.Content(
                            members = emptyList(),
                            packages = packages
                        )
                    )
                }
            )
        }
    }

    fun deleteMember(memberId: Int) {
        screenModelScope.launch {
            _uiState.update(FacilityMemberSettingsUiState.Loading)

            runCatching {
                facilityRepository.deleteFacilityMember(gymId = activeFacilityId, userId = memberId)
                refreshGymMembers()
            }.onFailure { error ->
                _uiState.update(FacilityMemberSettingsUiState.Error(error.message ?: "Failed to remove member"))
            }
        }
    }

    fun updateMemberPackage(memberId: Int, packageId: Int) {

        val content = uiState.value as? FacilityMemberSettingsUiState.Content ?: return
        val activatePackage = content.members.first { it.userId == memberId }.membershipPackage

        screenModelScope.launch {
            runCatching {
                if (activatePackage == null) {
                    facilityRepository.addPackageToMember(memberId, packageId)
                } else {
                    facilityRepository.updateMemberPackage(memberId, activeFacilityId, packageId)
                }
                refreshGymMembers()
            }.onFailure { error ->
                _uiState.update(FacilityMemberSettingsUiState.Error(error.message ?: "Failed to update member package"))

                return@launch
            }
        }
    }

    fun deleteMemberPackage(memberId: Int, packageId: Int) {
        screenModelScope.launch {
            runCatching {
                facilityRepository.deleteMemberPackage(memberId, packageId)
            }.onFailure { error ->
                _uiState.update(FacilityMemberSettingsUiState.Error(error.message ?: "Failed to delete member package"))
                return@launch
            }
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
