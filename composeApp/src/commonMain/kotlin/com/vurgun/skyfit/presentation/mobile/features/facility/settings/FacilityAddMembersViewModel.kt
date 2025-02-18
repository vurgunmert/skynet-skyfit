@file:OptIn(ExperimentalUuidApi::class, ExperimentalUuidApi::class)

package com.vurgun.skyfit.presentation.mobile.features.facility.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FacilityAddMembersViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _allMembers = MutableStateFlow(generateFakeMembers(20)) // Initial fake members
    private val _filteredMembers = MutableStateFlow(_allMembers.value)
    val skyFitUsers: StateFlow<List<FacilitySettingsMemberViewData>> = _filteredMembers

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery.collect { query ->
                _filteredMembers.value = if (query.isEmpty()) {
                    _allMembers.value
                } else {
                    _allMembers.value.filter {
                        it.fullName.contains(query, ignoreCase = true) ||
                                it.userName.contains(query, ignoreCase = true)
                    }
                }
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun addMember(memberId: String) {
        _allMembers.value = _allMembers.value.filterNot { it.memberId == memberId }
        _filteredMembers.value = _allMembers.value // Refresh list
    }

    companion object {
        fun generateFakeMembers(count: Int): List<FacilitySettingsMemberViewData> {
            val images = listOf(
                "https://ik.imagekit.io/skynet2skyfit/exercise_detail_header_fake.png?updatedAt=1739507110520",
                "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680",
                "https://ik.imagekit.io/skynet2skyfit/Profile%20Photo.png?updatedAt=1739703080462",
                "https://ik.imagekit.io/skynet2skyfit/exercise_detail_header_fake.png?updatedAt=1739507110520",
                null,
                null
            )
            val names = listOf("Emma", "Liam", "Ava", "Noah", "Isabella", "Mason", "Sophia", "James", "Mia", "Benjamin")
            val surnames = listOf("Anderson", "Thomas", "Martinez", "White", "Harris", "Taylor", "Moore", "Lee", "Walker", "Hall")

            return List(count) {
                FacilitySettingsMemberViewData(
                    memberId = Uuid.random().toString(),
                    profileImageUrl = images.random(),
                    userName = "user${Random.nextInt(1000, 9999)}",
                    fullName = "${names.random()} ${surnames.random()}"
                )
            }
        }
    }
}