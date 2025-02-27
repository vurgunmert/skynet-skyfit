package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableCardComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitBodyTypeIcon
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_select_body_type_message
import skyfit.composeapp.generated.resources.onboarding_select_body_type_title

private data class BodyTypeItemViewData(
    val name: String,
    val iconId: String
)

@Composable
fun MobileOnboardingBodyTypeSelectionScreen(
    viewModel: BaseOnboardingViewModel,
    navigator: Navigator
) {
    fun getBodyTypeItems(gender: String?): List<BodyTypeItemViewData> {
        return when (gender) {
            "male" -> listOf(
                BodyTypeItemViewData(name = "Ectomorph", iconId = "ic_male_body_type_ecto"),
                BodyTypeItemViewData(name = "Mesomorph", iconId = "ic_male_body_type_meso"),
                BodyTypeItemViewData(name = "Endomorph", iconId = "ic_male_body_type_endo"),
            )
            "female" -> listOf(
                BodyTypeItemViewData(name = "Ectomorph", iconId = "ic_female_body_type_ecto"),
                BodyTypeItemViewData(name = "Mesomorph", iconId = "ic_female_body_type_meso"),
                BodyTypeItemViewData(name = "Endomorph", iconId = "ic_female_body_type_endo"),
            )
            else -> emptyList()
        }
    }

    val bodyTypeItems: List<BodyTypeItemViewData> = when (viewModel) {
        is UserOnboardingViewModel -> getBodyTypeItems(viewModel.state.value.gender)
        is TrainerOnboardingViewModel -> getBodyTypeItems(viewModel.state.value.gender)
        else -> emptyList()
    }

    val cachedBodyType by remember(viewModel) {
        derivedStateOf {
            when (viewModel) {
                is UserOnboardingViewModel -> bodyTypeItems.find { it.name == viewModel.state.value.bodyType }
                is TrainerOnboardingViewModel -> bodyTypeItems.find { it.name == viewModel.state.value.bodyType }
                else -> null
            }
        }
    }

    var selectedBodyType by remember { mutableStateOf<BodyTypeItemViewData?>(null) }

    LaunchedEffect(cachedBodyType, bodyTypeItems) {
        if (selectedBodyType == null) {
            selectedBodyType = cachedBodyType ?: bodyTypeItems.firstOrNull()
        }
    }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 7, currentStep = 6)

            Spacer(Modifier.height(120.dp))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_select_body_type_title),
                subtitle = stringResource(Res.string.onboarding_select_body_type_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingBodyTypeSelectionScreenSelectableCardGridComponent(
                items = bodyTypeItems,
                selectedBodyType = selectedBodyType,
                onBodyTypeSelected = { selectedBodyType = it }
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(
                onClickContinue = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> {
                            viewModel.updateBodyType(selectedBodyType?.name ?: "")
                            navigator.jumpAndStay(NavigationRoute.OnboardingUserGoalSelection)
                        }
                        is TrainerOnboardingViewModel -> {
                            viewModel.updateBodyType(selectedBodyType?.name ?: "")
                            navigator.jumpAndStay(NavigationRoute.OnboardingTrainerDetails)
                        }
                    }
                },
                onClickSkip = {
                    when (viewModel) {
                        is UserOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingBodyTypeSelection,
                            NavigationRoute.OnboardingCompleted
                        )

                        is TrainerOnboardingViewModel -> navigator.jumpAndTakeover(
                            NavigationRoute.OnboardingBodyTypeSelection,
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
private fun MobileOnboardingBodyTypeSelectionScreenSelectableCardGridComponent(
    items: List<BodyTypeItemViewData>,
    selectedBodyType: BodyTypeItemViewData?,
    onBodyTypeSelected: (BodyTypeItemViewData) -> Unit
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SkyFitSelectableCardComponent(
                    isSelected = selectedBodyType == item,
                    onClick = { onBodyTypeSelected(item) }
                ) {
                    Image(
                        painter = SkyFitBodyTypeIcon.getIconResourcePainter(item.iconId),
                        contentDescription = item.name,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    text = item.name,
                    style = SkyFitTypography.bodyMediumRegular
                )
            }
        }
    }
}
