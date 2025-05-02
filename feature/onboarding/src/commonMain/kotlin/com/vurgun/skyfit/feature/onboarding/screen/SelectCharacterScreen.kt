package com.vurgun.skyfit.feature.onboarding.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SelectableCardComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.viewdata.CharacterTypeViewData
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.onboarding_character_message
import skyfit.core.ui.generated.resources.onboarding_character_title

internal class SelectCharacterScreen(private val viewModel: OnboardingViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingCharacterSelectionScreen(
            viewModel = viewModel,
            goToBirthdaySelection = { navigator.push(SelectBirthdayScreen(viewModel)) }
        )
    }
}

@Composable
internal fun MobileOnboardingCharacterSelectionScreen(
    viewModel: OnboardingViewModel,
    goToBirthdaySelection: () -> Unit
) {
    val characterItems = remember { viewModel.characterTypes }

    val defaultCharacter = viewModel.uiState.collectAsState().value.character ?: CharacterTypeViewData.Carrot

    var selectedCharacter: CharacterTypeViewData by remember { mutableStateOf(defaultCharacter) }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                    goToBirthdaySelection()
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
            SelectableCardComponent(
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
