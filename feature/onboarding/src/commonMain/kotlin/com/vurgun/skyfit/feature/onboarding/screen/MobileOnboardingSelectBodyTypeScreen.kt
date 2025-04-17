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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.core.domain.model.GenderType
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.feature.onboarding.component.OnboardingActionGroupComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingStepProgressComponent
import com.vurgun.skyfit.feature.onboarding.component.OnboardingTitleGroupComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitSelectableCardComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import com.vurgun.skyfit.ui.core.viewdata.BodyTypeViewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.onboarding_body_type_message
import skyfit.ui.core.generated.resources.onboarding_body_type_title

@Composable
internal fun MobileOnboardingBodyTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    goToGoalSelection: () -> Unit,
    goToEnterTrainerProfile: () -> Unit,
    goToFacilityDetail: () -> Unit
) {
    val gender = viewModel.uiState.collectAsState().value.gender ?: GenderType.MALE
    val bodyTypeItems = gender.let { BodyTypeViewData.from(it) }

    val selectedBodyType = viewModel.uiState.collectAsState().value.bodyType

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
                    when (viewModel.uiState.value.userRole) {
                        UserRole.User -> goToGoalSelection()
                        UserRole.Trainer -> goToEnterTrainerProfile()
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
                SkyFitSelectableCardComponent(
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
