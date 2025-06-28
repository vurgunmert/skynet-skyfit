package com.vurgun.skyfit.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.ui.components.special.SelectableCardComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.model.BodyTypeViewData
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.onboarding.model.OnboardingViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.onboarding_body_type_message
import fiwe.core.ui.generated.resources.onboarding_body_type_title

internal class SelectBodyTypeScreen(private val viewModel: OnboardingViewModel) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileOnboardingBodyTypeSelectionScreen(
            viewModel = viewModel,
            goToGoalSelection = { navigator.push(SelectGoalsScreen(viewModel)) },
            goToEnterTrainerProfile = { navigator.push(EnterProfileScreen(viewModel)) },
            goToFacilityDetail = { navigator.push(EnterFacilityDetailScreen(viewModel)) }
        )
    }

}

@Composable
internal fun MobileOnboardingBodyTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    goToGoalSelection: () -> Unit,
    goToEnterTrainerProfile: () -> Unit,
    goToFacilityDetail: () -> Unit
) {
    val gender = viewModel.uiState.collectAsState().value.gender
    val bodyTypeItems = gender.let { BodyTypeViewData.from(it) }

    val selectedBodyType = viewModel.uiState.collectAsState().value.bodyType

    LaunchedEffect(viewModel) {
        viewModel.updateBodyType(bodyTypeItems.first())
    }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OnboardingStepProgressComponent(totalSteps = 8, currentStep = 6)

            Spacer(Modifier.height(120.dp))

            OnboardingTitleGroupComponent(
                title = stringResource(Res.string.onboarding_body_type_title),
                subtitle = stringResource(Res.string.onboarding_body_type_message)
            )
            Spacer(Modifier.height(16.dp))

            MobileOnboardingBodyTypeSelectionScreenSelectableCardGridComponent(
                items = bodyTypeItems,
                selectedBodyType = selectedBodyType,
                onBodyTypeSelected = viewModel::updateBodyType
            )

            Spacer(Modifier.weight(1f))

            OnboardingActionGroupComponent(
                onClickContinue = {
                    viewModel.updateBodyType(selectedBodyType)
                    when (viewModel.uiState.value.accountRole) {
                        AccountRole.User -> goToGoalSelection()
                        AccountRole.Trainer -> goToEnterTrainerProfile()
                        else -> goToFacilityDetail()
                    }
                }
            )

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun MobileOnboardingBodyTypeSelectionScreenSelectableCardGridComponent(
    items: List<BodyTypeViewData>,
    selectedBodyType: BodyTypeViewData?,
    onBodyTypeSelected: (BodyTypeViewData) -> Unit
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
                SelectableCardComponent(
                    isSelected = selectedBodyType == item,
                    onClick = { onBodyTypeSelected(item) }
                ) {
                    Image(
                        painter = painterResource(item.icon.res),
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
