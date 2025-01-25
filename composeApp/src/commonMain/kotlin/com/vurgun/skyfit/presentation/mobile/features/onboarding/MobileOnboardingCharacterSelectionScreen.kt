package com.vurgun.skyfit.presentation.mobile.features.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitSelectableCardComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingActionGroupComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingStepProgressComponent
import com.vurgun.skyfit.presentation.shared.features.onboarding.OnboardingTitleGroupComponent
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileOnboardingCharacterSelectionScreen(
    onSkip: () -> Unit,
    onNext: () -> Unit
) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 6, currentStep = 2)
            Spacer(Modifier.height(120.dp))
            OnboardingTitleGroupComponent(
                title = "Karakter Seçiniz",
                subtitle = "Seçtiğiniz karakter ana ekranda görüntülenecek"
            )
            Spacer(Modifier.height(16.dp))
            MobileOnboardingCharacterSelectionScreenSelectableCardsComponent()
            Spacer(Modifier.weight(1f))
            OnboardingActionGroupComponent(
                onClickContinue = onNext,
                onClickSkip = onSkip
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileOnboardingCharacterSelectionScreenSelectableCardsComponent() {
    val items = listOf("Carrot", "Pizza", "Koala") // Replace with real data
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
        items = items,
        selectedIndex = selectedIndex,
        onCardSelected = { selectedIndex = it },
        cardContent = {
            Image(
                painter = painterResource(Res.drawable.logo_skyfit), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}


@Composable
private fun MobileOnboardingCharacterSelectionScreenSelectableCardGridComponent(
    items: List<String>,
    selectedIndex: Int?,
    onCardSelected: (Int) -> Unit,
    cardContent: @Composable BoxScope.(Int) -> Unit // Shared content for all cards
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
        itemsIndexed(items) { index, _ ->
            SkyFitSelectableCardComponent(
                isSelected = selectedIndex == index,
                onClick = { onCardSelected(index) }
            ) {
                cardContent(index)
            }
        }
    }
}
