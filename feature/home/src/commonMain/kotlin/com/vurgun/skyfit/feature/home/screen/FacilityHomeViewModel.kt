package com.vurgun.skyfit.feature.home.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface FacilityHomeAction {
    data object NavigateToNotifications : FacilityHomeAction
    data object NavigateToConversations : FacilityHomeAction
    data object NavigateToManageLessons : FacilityHomeAction
}

sealed interface FacilityHomeEffect {
    data object NavigateToNotifications : FacilityHomeEffect
    data object NavigateToConversations : FacilityHomeEffect
    data object NavigateToManageLessons : FacilityHomeEffect
}

class FacilityHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _effect = SingleSharedFlow<FacilityHomeEffect>()
    val effect: SharedFlow<FacilityHomeEffect> = _effect

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    private val _appointments = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    fun onAction(action: FacilityHomeAction) {
        when (action) {
            FacilityHomeAction.NavigateToConversations -> {
                emitEffect(FacilityHomeEffect.NavigateToConversations)
            }

            FacilityHomeAction.NavigateToManageLessons -> {
                emitEffect(FacilityHomeEffect.NavigateToManageLessons)
            }

            FacilityHomeAction.NavigateToNotifications -> {
                emitEffect(FacilityHomeEffect.NavigateToNotifications)
            }
        }
    }

    private fun loadData() {
        screenModelScope.launch {
            _appointments.value = courseRepository.getUpcomingLessonsByFacility(facilityUser.gymId)
                .getOrNull()?.let { list ->
                    list.map { mapper.map(it) }
                }.orEmpty()
        }
    }

    private fun emitEffect(effect: FacilityHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}