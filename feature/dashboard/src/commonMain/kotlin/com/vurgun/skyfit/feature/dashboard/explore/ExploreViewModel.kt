package com.vurgun.skyfit.feature.dashboard.explore


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.feature.persona.components.viewdata.FacilityProfileCardItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.TrainerProfileCardItemViewData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class ExploreUiState {
    object Loading : ExploreUiState()
    data class Error(val message: String?) : ExploreUiState()
    data class Content(
        val trainers: List<TrainerProfileCardItemViewData>,
        val facilities: List<FacilityProfileCardItemViewData>,
    ) : ExploreUiState()
}

sealed class ExploreAction {
    data object OnClickBack : ExploreAction()
    data object OnClickExercise : ExploreAction()
    data object OnClickTrainer : ExploreAction()
    data object OnClickFacility : ExploreAction()
    data object OnClickCommunities : ExploreAction()
    data object OnClickChallanges : ExploreAction()
    // TODO: add other actions
}

sealed class ExploreEffect {
    object NavigateToBack : ExploreEffect()
    // TODO: add other effects
}

class ExploreViewModel() : ScreenModel {

    private val _uiState = UiStateDelegate<ExploreUiState>(ExploreUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<ExploreEffect>()
    val effect = _effect as SharedFlow<ExploreEffect>

    fun onAction(action: ExploreAction) {
        when (action) {
            ExploreAction.OnClickBack ->
                _effect.emitIn(screenModelScope, ExploreEffect.NavigateToBack)

            else -> {

            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            _uiState.update(ExploreUiState.Loading)

            _uiState.update(
                ExploreUiState.Content(
                    trainers,
                    facilities,
                )
            )
        }
    }

    val trainers = listOf(
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-5.jpg?updatedAt=1740259432295",
            "Lucas Bennett",
            1800,
            13,
            32,
            4.8f
        ),
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-9.jpg?updatedAt=1740259432298",
            "Olivia Hayes",
            1500,
            10,
            20,
            4.5f
        ),
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-9.jpg?updatedAt=1740259432298",
            "Mason Reed",
            2000,
            15,
            40,
            4.9f
        ),
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-11.jpg?updatedAt=1740259432273",
            "Sophia Hill",
            1700,
            12,
            28,
            4.7f
        ),
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-7.jpg?updatedAt=1740259432143",
            "Emma Johnson",
            1600,
            11,
            25,
            4.6f
        ),
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-6.jpg?updatedAt=1740259432118",
            "James Smith",
            1900,
            14,
            35,
            4.8f
        ),
        TrainerProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-10.jpg?updatedAt=1740259432035",
            "Ava Brown",
            1750,
            13,
            30,
            4.7f
        )
    )

    val facilities = listOf(
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-3.jpg?updatedAt=1740259189441",
            "ironstudio",
            2500,
            12,
            3.5f
        ),
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-4.jpg?updatedAt=1740259189394",
            "ironstudio2",
            2500,
            12,
            3.5f
        ),
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-1.jpg?updatedAt=1740259189362",
            "ironstudio3",
            2500,
            12,
            3.5f
        ),
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-2.jpg?updatedAt=1740259189345",
            "ironstudio4",
            2500,
            12,
            3.5f
        ),
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download.jpg?updatedAt=1740259189264",
            "ironstudio5",
            2500,
            12,
            3.5f
        ),
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/fake_facility_background.png?updatedAt=1739637015088",
            "ironstudio6",
            2500,
            12,
            3.5f
        ),
        FacilityProfileCardItemViewData(
            "https://ik.imagekit.io/skynet2skyfit/download-3.jpg?updatedAt=1740259189441",
            "ironstudio7",
            2500,
            12,
            3.5f
        ),
    )
}