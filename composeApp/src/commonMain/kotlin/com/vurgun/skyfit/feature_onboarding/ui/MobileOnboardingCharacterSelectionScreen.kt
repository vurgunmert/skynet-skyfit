package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
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

private data class CharacterItemViewData(
    val name: String,
    val iconId: String
)

private fun getCharacterItems(): List<CharacterItemViewData> {
    return SkyFitCharacterIcon.iconMap.keys.map { key ->
        CharacterItemViewData(name = key, iconId = key)
    }
}

@Composable
fun MobileOnboardingCharacterSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    val characterItems = remember { getCharacterItems() }

    val defaultCharacterId = "ic_character_carrot"

    val cachedCharacterId by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> viewModel.state.value.characterId
                is TrainerOnboardingViewModel -> viewModel.state.value.characterId
                else -> null
            }
        }
    }

    var selectedCharacterId by remember { mutableStateOf("") }

    LaunchedEffect(cachedCharacterId) {
        selectedCharacterId = cachedCharacterId.takeUnless { it.isNullOrEmpty() }
            ?: defaultCharacterId
    }

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
                items = characterItems,
                selectedCharacterId = selectedCharacterId,
                onCharacterSelected = { selectedCharacterId = it }
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> viewModel.updateCharacter(selectedCharacterId)
                        is TrainerOnboardingViewModel -> viewModel.updateCharacter(selectedCharacterId)
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
            Spacer(Modifier.height(30.dp))
        }
    }
}


@Composable
private fun MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
    items: List<CharacterItemViewData>,
    selectedCharacterId: String,
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
                isSelected = selectedCharacterId == item.iconId,
                onClick = { onCharacterSelected(item.iconId) }
            ) {
                Image(
                    painter = SkyFitCharacterIcon.getIconResourcePainter(item.iconId),
                    contentDescription = item.name,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
