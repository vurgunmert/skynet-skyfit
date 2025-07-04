package com.vurgun.skyfit.onboarding.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserGoal
import com.vurgun.skyfit.core.ui.components.button.SkyFitSelectableTextButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.onboarding.model.OnboardingViewModel
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.onboarding_goal_message
import fiwe.core.ui.generated.resources.onboarding_goal_title

internal class SelectGoalsScreen(private val viewModel: OnboardingViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingGoalSelectionScreen(
            viewModel = viewModel,
            goToEnterProfile = { navigator.push(EnterProfileScreen(viewModel)) }
        )
    }
}

@Composable
private fun MobileOnboardingGoalSelectionScreen(
    viewModel: OnboardingViewModel,
    goToEnterProfile: () -> Unit
) {
    val selectedGoals = viewModel.uiState.collectAsState().value.goals.orEmpty()

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 7)

            Spacer(Modifier.weight(1f))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_goal_title),
                subtitle = stringResource(Res.string.onboarding_goal_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingGoalSelectionComponent(
                goals = viewModel.availableGoals.value,
                selectedGoals = selectedGoals,
                onGoalsUpdated = viewModel::updateGoals
            )

            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(onClickContinue = goToEnterProfile)

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MobileOnboardingGoalSelectionComponent(
    goals: List<UserGoal>,
    selectedGoals: Set<UserGoal>,
    onGoalsUpdated: (Set<UserGoal>) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        goals.forEach { goal ->
            SkyFitSelectableTextButton(
                text = goal.goalName,
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
