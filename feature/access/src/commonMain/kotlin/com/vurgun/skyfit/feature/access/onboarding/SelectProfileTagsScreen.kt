package com.vurgun.skyfit.feature.access.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutTag
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.FitnessTagPickerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.onboarding_facility_profile_message
import skyfit.core.ui.generated.resources.onboarding_facility_profile_title

internal class SelectProfileTagsScreen(private val viewModel: OnboardingViewModel) : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingFacilityProfileTagsScreen(
            viewModel = viewModel,
            goToCompleted = { navigator.push(OnboardingCompletedScreen(viewModel)) },
            goToLogin = { appNavigator.replaceAll(SharedScreen.Authorization) }
        )
    }
}


@Composable
internal fun MobileOnboardingFacilityProfileTagsScreen(
    viewModel: OnboardingViewModel,
    goToCompleted: () -> Unit,
    goToLogin: () -> Unit
) {
    val selectedTags = viewModel.uiState.collectAsState().value.profileTags.orEmpty()
    val availableTags by viewModel.availableTags.collectAsState()
    val eventState: OnboardingViewEvent by viewModel.eventState.collectAsState()

    SkyFitMobileScaffold {
        when (val event = eventState) {
            OnboardingViewEvent.Idle -> {
                ProfileTagsContent(
                    allTags = availableTags,
                    selectedTags = selectedTags,
                    onTagsUpdated = viewModel::updateFacilityProfileTags,
                    onNext = viewModel::submitRequest
                )
            }

            OnboardingViewEvent.Completed -> {
                goToCompleted()
            }

            is OnboardingViewEvent.Error -> {
                ErrorDialog(message = event.message, onDismiss = viewModel::clearError)
            }

            OnboardingViewEvent.InProgress -> {
                FullScreenLoaderContent()
            }

            OnboardingViewEvent.NavigateToLogin -> {
                goToLogin()
            }
        }
    }
}

@Composable
private fun ProfileTagsContent(
    allTags: List<WorkoutTag>,
    selectedTags: List<WorkoutTag>,
    onTagsUpdated: (List<WorkoutTag>) -> Unit,
    onNext: () -> Unit
) {
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
            availableTags = allTags,
            selectedTags = selectedTags,
            onTagsSelected = onTagsUpdated
        )

        Spacer(Modifier.weight(1f))

        OnboardingActionGroupComponent(onClickContinue = onNext)

        Spacer(Modifier.height(20.dp))
    }
}