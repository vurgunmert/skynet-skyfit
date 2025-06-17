package com.vurgun.skyfit.feature.home.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.statistics.front.StatisticCardUiData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.NavigateToManageLessons
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect.ShowOverlay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class DashboardStatCardModel(
    val primaryMetrics: List<StatisticCardUiData>,
    val chartData: List<Any> = emptyList(),
    val timeRange: TimeRange = TimeRange.H
) {
    enum class TimeRange {
        Y, M6, M3, M1, H
    }
}

sealed interface FacilityHomeUiState {
    data object Loading : FacilityHomeUiState
    data class Error(val message: String?) : FacilityHomeUiState
    data class Content(
        val facility: FacilityAccount,
        val profile: FacilityProfile,
        val activeLessons: List<LessonSessionItemViewData> = emptyList(),
        val allLessons: List<LessonSessionItemViewData> = emptyList(),
        val statistics: DashboardStatCardModel,
        val notificationsEnabled: Boolean = true,
        val conversationsEnabled: Boolean = true,
    ) : FacilityHomeUiState
}

sealed interface FacilityHomeAction {
    data class OnOverlayRequest(val screen: ScreenProvider) : FacilityHomeAction
    data object OnClickLessons : FacilityHomeAction
    data object OnClickNotifications : FacilityHomeAction
    data object OnClickConversations : FacilityHomeAction
    data object OnClickChatBot : FacilityHomeAction
}

sealed interface FacilityHomeEffect {
    data class ShowOverlay(val screen: ScreenProvider) : FacilityHomeEffect
    data object NavigateToManageLessons : FacilityHomeEffect
}

class FacilityHomeViewModel(
    private val userManager: ActiveAccountManager,
    private val facilityRepository: FacilityRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityHomeUiState>(FacilityHomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<FacilityHomeEffect>()
    val effect: SharedFlow<FacilityHomeEffect> = _effect

    fun onAction(action: FacilityHomeAction) {
        when (action) {
            is FacilityHomeAction.OnOverlayRequest ->
                emitEffect(ShowOverlay(action.screen))

            FacilityHomeAction.OnClickLessons -> {
                emitEffect(NavigateToManageLessons)
            }

            FacilityHomeAction.OnClickChatBot ->
                emitEffect(ShowOverlay(SharedScreen.ChatBot))

            FacilityHomeAction.OnClickConversations ->
                emitEffect(ShowOverlay(SharedScreen.Conversations))

            FacilityHomeAction.OnClickNotifications ->
                emitEffect(ShowOverlay(SharedScreen.Notifications))
        }
    }

    fun loadData() {
        screenModelScope.launch {
            val result = runCatching {
                val facility = userManager.account.value as FacilityAccount
                val profile = facilityRepository.getFacilityProfile(facility.gymId).getOrThrow()

                val statistics = DashboardStatCardModel(
                    primaryMetrics = listOf(
                        StatisticCardUiData("Aktif Üye", "${profile.memberCount}"),
                        StatisticCardUiData("Aktif Egitmen", "${profile.trainerCount}"),
                        StatisticCardUiData("Puan", "${profile.point}")
                    )
                )

                val activeLessons = facilityRepository.getUpcomingLessonsByFacility(facility.gymId, 5)
                    .getOrNull()?.map(mapper::map).orEmpty()

                val allLessons = facilityRepository.getAllLessonsByFacility(facility.gymId, LocalDate(2025, 1, 1), null)
                    .getOrNull()?.map(mapper::map).orEmpty()

                FacilityHomeUiState.Content(
                    facility = facility,
                    profile = profile,
                    activeLessons = activeLessons,
                    allLessons = allLessons,
                    statistics = statistics
                )
            }

            result.fold(
                onSuccess = { _uiState.update(it) },
                onFailure = { _uiState.update(FacilityHomeUiState.Error(it.message)) }
            )
        }
    }

    private fun emitEffect(effect: FacilityHomeEffect) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }
}