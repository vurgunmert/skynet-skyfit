package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableTextButton
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.goal_to_be_fit
import skyfit.composeapp.generated.resources.goal_to_gain_muscle
import skyfit.composeapp.generated.resources.goal_to_lose_weight
import skyfit.composeapp.generated.resources.onboarding_user_select_goal_message
import skyfit.composeapp.generated.resources.onboarding_user_select_goal_title

enum class FitnessGoalViewData(val key: String, val displayTextRes: StringResource) {
    LOSE_WEIGHT("lose_weight", Res.string.goal_to_lose_weight),
    BE_FIT("be_fit", Res.string.goal_to_be_fit),
    GAIN_MUSCLE("gain_muscle", Res.string.goal_to_gain_muscle);

    companion object {
        fun fromKey(dbKey: String?): FitnessGoalViewData = entries.find { it.key == dbKey } ?: BE_FIT
    }
}

@Composable
fun MobileOnboardingGoalSelectionScreen(
    viewModel: UserOnboardingViewModel,
    navigator: Navigator
) {

    val cachedGoal by remember(viewModel) {
        derivedStateOf {
            FitnessGoalViewData.fromKey(viewModel.state.value.goal)
        }
    }

    var selectedGoal by remember { mutableStateOf(cachedGoal) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 7)
            Spacer(Modifier.height(178.dp))
            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_user_select_goal_title),
                subtitle = stringResource(Res.string.onboarding_user_select_goal_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingGoalSelectionComponent(
                selectedGoal = selectedGoal,
                onGoalSelected = { selectedGoal = it }
            )

            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = {
                    viewModel.updateGoal(selectedGoal.key)
                    navigator.jumpAndTakeover(
                        NavigationRoute.OnboardingUserGoalSelection,
                        NavigationRoute.OnboardingCompleted
                    )
                },
                onClickSkip = {
                    navigator.jumpAndTakeover(
                        NavigationRoute.OnboardingUserGoalSelection,
                        NavigationRoute.OnboardingCompleted
                    )
                }
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileOnboardingGoalSelectionComponent(
    selectedGoal: FitnessGoalViewData,
    onGoalSelected: (FitnessGoalViewData) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        FitnessGoalViewData.entries.forEach { goal ->
            SkyFitSelectableTextButton(
                text = stringResource(goal.displayTextRes),
                selected = selectedGoal == goal,
                onSelect = { onGoalSelected(goal) }
            )
        }
    }
}