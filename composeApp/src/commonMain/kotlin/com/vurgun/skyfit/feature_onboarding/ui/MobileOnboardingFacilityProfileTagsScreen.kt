package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.FacilityOnboardingViewModel
import com.vurgun.skyfit.feature_settings.ui.MobileUserSettingsActivityTagEditComponent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_facility_complete_profile_message
import skyfit.composeapp.generated.resources.onboarding_facility_complete_profile_title

@Composable
fun MobileOnboardingFacilityProfileTagsScreen(
    viewModel: FacilityOnboardingViewModel,
    navigator: Navigator
) {
    val state = viewModel.state.collectAsState().value
    val profileTags = state.profileTags

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 2, currentStep = 2)

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_facility_complete_profile_title),
                subtitle = stringResource(Res.string.onboarding_facility_complete_profile_message)
            )

            Spacer(Modifier.height(24.dp))
            MobileUserSettingsActivityTagEditComponent(
                selectedTags = profileTags,
                onTagsSelected = { viewModel.updateFacilityProfileTags(it) }
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(
                onClickContinue = {
                    navigator.jumpAndTakeover(
                        NavigationRoute.OnboardingFacilityProfileTags,
                        NavigationRoute.OnboardingCompleted
                    )
                },
                onClickSkip = {
                    navigator.jumpAndTakeover(
                        NavigationRoute.OnboardingFacilityProfileTags,
                        NavigationRoute.OnboardingCompleted
                    )
                }
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}
