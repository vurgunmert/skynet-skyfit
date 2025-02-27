package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableCardComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitCharacterIcon
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_select_character_message
import skyfit.composeapp.generated.resources.onboarding_select_character_title

@Composable
fun MobileOnboardingCharacterSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    val characterIds = SkyFitCharacterIcon.iconMap.keys.toList()
    val cachedCharacterId by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.characterId
                is TrainerOnboardingViewModel -> viewModel.state.value.characterId
                else -> ""
            }
        }
    }

    var selectedCharacterId by remember { mutableStateOf(cachedCharacterId?.ifEmpty { characterIds.firstOrNull() }) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 1)

            Spacer(Modifier.height(120.dp))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_character_title),
                subtitle = stringResource(Res.string.onboarding_select_character_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
                items = characterIds,
                selectedCharacter = selectedCharacterId,
                onCharacterSelected = { selectedCharacterId = it }
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> viewModel.updateCharacter(selectedCharacterId ?: "")
                        is TrainerOnboardingViewModel -> viewModel.updateCharacter(selectedCharacterId ?: "")
                    }
                    navigator.jumpAndStay(NavigationRoute.OnboardingBirthYearSelection)
                },
                onClickSkip = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingCharacterSelection,
                            NavigationRoute.OnboardingCompleted
                        )

                        is TrainerOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingCharacterSelection,
                            NavigationRoute.OnboardingTrainerDetails
                        )
                    }
                }
            )
            Spacer(Modifier.height(36.dp))
        }
    }
}

@Composable
private fun MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
    items: List<String>,
    selectedCharacter: String?,
    onCharacterSelected: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            SkyFitSelectableCardComponent(
                isSelected = selectedCharacter == item,
                onClick = { onCharacterSelected(item) }
            ) {
                Image(
                    painter = SkyFitCharacterIcon.getIconResourcePainter(item),
                    contentDescription = item,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
