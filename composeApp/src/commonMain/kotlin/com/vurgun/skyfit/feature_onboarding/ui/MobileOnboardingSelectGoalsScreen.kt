package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.GoalType
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableTextButton
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_goal_message
import skyfit.composeapp.generated.resources.onboarding_goal_title

@Composable
fun MobileOnboardingGoalSelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val selectedGoals = viewModel.uiState.collectAsState().value.goals.orEmpty()

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 7)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_goal_title),
                subtitle = stringResource(Res.string.onboarding_goal_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingGoalSelectionComponent(
                selectedGoals = selectedGoals,
                onGoalsUpdated = viewModel::updateGoals
            )

            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent { navigator.jumpAndStay(MobileNavRoute.OnboardingEnterProfile) }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MobileOnboardingGoalSelectionComponent(
    selectedGoals: Set<GoalType>,
    onGoalsUpdated: (Set<GoalType>) -> Unit
) {
    val goalTypes = GoalType.getAllGoals()

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        goalTypes.forEach { goal ->
            SkyFitSelectableTextButton(
                text = goal.label,
                selected = selectedGoals.contains(goal),
                onSelect = {
                    val updatedGoals = if (selectedGoals.contains(goal)) {
                        selectedGoals - goal
                    } else {
                        selectedGoals + goal
                    }
                    onGoalsUpdated(updatedGoals)
                }
            )
        }
    }
}
