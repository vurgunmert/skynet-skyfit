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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.GenderType
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitSelectableCardComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.ui.viewdata.BodyTypeViewData
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.onboarding_body_type_message
import skyfit.composeapp.generated.resources.onboarding_body_type_title

@Composable
fun MobileOnboardingBodyTypeSelectionScreen(
    viewModel: OnboardingViewModel,
    navigator: Navigator
) {
    val gender = viewModel.uiState.collectAsState().value.gender ?: GenderType.MALE
    val bodyTypeItems = gender.let { BodyTypeViewData.from(it) }

    val selectedBodyType = viewModel.uiState.collectAsState().value.bodyType

    SkyFitScaffold {
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
                    when (viewModel.uiState.value.userType) {
                        UserType.User -> navigator.jumpAndStay(MobileNavRoute.OnboardingUserGoalSelection)
                        UserType.Trainer -> navigator.jumpAndStay(MobileNavRoute.OnboardingEnterProfile)
                        else -> navigator.jumpAndStay(MobileNavRoute.FacilityClassDetail)
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
