package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.OnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.FitnessTagPickerComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_facility_complete_profile_message
import skyfit.composeapp.generated.resources.onboarding_facility_complete_profile_title

@Composable
fun MobileOnboardingFacilityProfileTagsScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val selectedTags = viewModel.state.collectAsState().value.profileTags.orEmpty()

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 2, currentStep = 2)

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_facility_complete_profile_title),
                subtitle = stringResource(Res.string.onboarding_facility_complete_profile_message)
            )

            Spacer(Modifier.height(24.dp))
            FitnessTagPickerComponent(
                modifier = Modifier.padding(horizontal = 22.dp).fillMaxWidth(),
                selectedTags = selectedTags,
                onTagsSelected = { viewModel.updateFacilityProfileTags(it) }
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent { navigator.jumpAndTakeover(NavigationRoute.OnboardingCompleted) }
        }
    }
}
