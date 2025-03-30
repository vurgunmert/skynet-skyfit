package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.FitnessTagType
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.error.ErrorDialog
import com.vurgun.skyfit.core.ui.components.loader.CircularLoader
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewEvent
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.FitnessTagPickerComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_facility_profile_message
import skyfit.composeapp.generated.resources.onboarding_facility_profile_title

@Composable
fun MobileOnboardingFacilityProfileTagsScreen(
    viewModel: OnboardingViewModel,
    rootNavigator: Navigator,
    onboardingNavigator: Navigator
) {
    val selectedTags = viewModel.uiState.collectAsState().value.profileTags.orEmpty()
    val eventState: OnboardingViewEvent by viewModel.eventState.collectAsState()

    SkyFitMobileScaffold {
        when(val event = eventState) {
            OnboardingViewEvent.Idle -> {
                ProfileTagsContent(
                    selectedTags = selectedTags,
                    onTagsUpdated = viewModel::updateFacilityProfileTags,
                    onNext = viewModel::submitRequest
                )
            }
            OnboardingViewEvent.Completed -> {
                onboardingNavigator.jumpAndTakeover(NavigationRoute.OnboardingCompleted)
            }
            is OnboardingViewEvent.Error -> {
                ErrorDialog(event.message, onDismiss = viewModel::clearError)
            }
            OnboardingViewEvent.InProgress -> {
                CircularLoader()
            }

            OnboardingViewEvent.NavigateToLogin -> {
                rootNavigator.jumpAndTakeover(NavigationRoute.Login)
            }
        }
    }
}

@Composable
private fun ProfileTagsContent(selectedTags: List<FitnessTagType>,
                               onTagsUpdated: (List<FitnessTagType>) -> Unit,
                               onNext: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OnboardingStepProgressComponent(totalSteps = 2, currentStep = 2)

        Spacer(Modifier.weight(1f))

        OnboardingTitleGroupComponent(
            title = stringResource(Res.string.onboarding_facility_profile_title),
            subtitle = stringResource(Res.string.onboarding_facility_profile_message)
        )

        Spacer(Modifier.height(24.dp))
        FitnessTagPickerComponent(
            modifier = Modifier.padding(horizontal = 22.dp).fillMaxWidth(),
            selectedTags = selectedTags,
            onTagsSelected = onTagsUpdated
        )

        Spacer(Modifier.weight(1f))

        OnboardingActionGroupComponent(onClickContinue = onNext)

        Spacer(Modifier.height(20.dp))
    }
}