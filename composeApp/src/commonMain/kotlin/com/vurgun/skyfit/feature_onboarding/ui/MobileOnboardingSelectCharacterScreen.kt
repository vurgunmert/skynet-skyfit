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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableCardComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.viewdata.CharacterTypeViewData
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_character_message
import skyfit.composeapp.generated.resources.onboarding_character_title

@Composable
fun MobileOnboardingCharacterSelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val characterItems = remember { viewModel.characterTypes }

    val defaultCharacter = viewModel.uiState.collectAsState().value.character ?: CharacterTypeViewData.Carrot

    var selectedCharacter: CharacterTypeViewData by remember { mutableStateOf(defaultCharacter) }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 1)

            Spacer(Modifier.height(120.dp))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_character_title),
                subtitle = stringResource(Res.string.onboarding_character_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
                items = characterItems,
                selectedCharacter = selectedCharacter,
                onCharacterSelected = { selectedCharacter = it }
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(
                onClickContinue = {
                    viewModel.updateCharacter(selectedCharacter)
                    navigator.jumpAndStay(MobileNavRoute.OnboardingBirthYearSelection)
                }
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}


@Composable
private fun MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
    items: List<CharacterTypeViewData>,
    selectedCharacter: CharacterTypeViewData,
    onCharacterSelected: (CharacterTypeViewData) -> Unit
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
                    painter = painterResource(item.icon.res),
                    contentDescription = item.icon.name,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
