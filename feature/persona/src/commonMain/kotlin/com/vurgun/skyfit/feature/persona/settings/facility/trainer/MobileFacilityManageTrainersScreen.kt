package com.vurgun.skyfit.feature.persona.settings.facility.trainer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.add_action
import skyfit.core.ui.generated.resources.delete_action
import skyfit.core.ui.generated.resources.search_action
import skyfit.core.ui.generated.resources.trainers_label

class FacilityManageTrainersScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityManageTrainersViewModel>()

        MobileFacilityManageTrainersScreen(
            goToBack = { navigator.pop() },
            goToAddTrainer = { navigator.push(FacilityAddTrainerScreen()) },
            viewModel = viewModel
        )
    }
}


@Composable
internal fun MobileFacilityManageTrainersScreen(
    goToBack: () -> Unit,
    goToAddTrainer: () -> Unit,
    viewModel: FacilityManageTrainersViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshGymTrainers()
    }

    SkyFitMobileScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                MobileFacilitySettingsSearchTrainerToolbarComponent(
                    title = stringResource(Res.string.trainers_label),
                    onClickBack = goToBack,
                    onClickAdd = goToAddTrainer
                )

                SkyFitSearchTextInputComponent(
                    hint = stringResource(Res.string.search_action),
                    value = uiState.query,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.filtered) {
                MobileFacilityTrainerItemComponent(
                    item = it,
                    onClick = {},
                    actionContent = {
                        SkyFitButtonComponent(
                            text = stringResource(Res.string.delete_action),
                            modifier = Modifier.wrapContentWidth(),
                            onClick = { viewModel.deleteTrainer(it.userId) },
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Micro,
                            state = ButtonState.Rest
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MobileFacilitySettingsSearchTrainerToolbarComponent(
    title: String,
    onClickBack: () -> Unit,
    onClickAdd: () -> Unit
) {
    Box(Modifier.fillMaxWidth().padding(vertical = 12.dp), contentAlignment = Alignment.CenterEnd) {
        SkyFitScreenHeader(title, onClickBack = onClickBack)

        SkyFitButtonComponent(
            modifier = Modifier
                .padding(end = 24.dp)
                .wrapContentWidth(),
            text = stringResource(Res.string.add_action),
            onClick = onClickAdd,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    }
}

@Composable
fun MobileFacilityTrainerItemComponent(
    item: TrainerPreview,
    onClick: () -> Unit,
    actionContent: @Composable () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            imageUrl = item.profileImageUrl,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = item.username,
                style = SkyFitTypography.bodyLargeSemibold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = item.fullName,
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary
            )
        }
        Spacer(Modifier.width(24.dp))
        actionContent()
    }
}
