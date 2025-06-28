package com.vurgun.skyfit.settings.facility.trainer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
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
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.components.special.SkyPageScaffold
import com.vurgun.skyfit.core.ui.components.topbar.CompactTopBar
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

class FacilityTrainerSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityTrainerSettingsViewModel>()
        val compactOverlayController = LocalCompactOverlayController.current

        MobileFacilityManageTrainersScreen(
            goToBack = {
                compactOverlayController?.invoke(null)
                navigator.pop()
            },
            goToAddTrainer = { navigator.push(FacilityAddTrainerScreen()) },
            onSelectTrainer = { navigator.push(SharedScreen.TrainerProfile(it)) },
            viewModel = viewModel
        )
    }
}


@Composable
internal fun MobileFacilityManageTrainersScreen(
    goToBack: () -> Unit,
    goToAddTrainer: () -> Unit,
    onSelectTrainer: (trainerId: Int) -> Unit,
    viewModel: FacilityTrainerSettingsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.refreshGymTrainers()
    }

    SkyPageScaffold(
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
                    trainer = it,
                    onClick = onSelectTrainer,
                    actionContent = {
                        SkyButton(
                            label = stringResource(Res.string.delete_action),
                            onClick = { viewModel.deleteTrainer(it.userId) },
                            size = SkyButtonSize.Micro,
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
    val windowSize = LocalWindowSize.current

    if (windowSize == WindowSize.EXPANDED) { //TODO: Move logic repeating actions
        CompactTopBar.TopbarWithEndAction(
            title = title,
            onClickBack = null,
            actionLabel = stringResource(Res.string.add_action),
            onClickAction = onClickAdd
        )
    } else {
        CompactTopBar.TopbarWithEndAction(
            title = title,
            onClickBack = onClickBack,
            actionLabel = stringResource(Res.string.add_action),
            onClickAction = onClickAdd
        )
    }
}

@Composable
fun MobileFacilityTrainerItemComponent(
    trainer: TrainerPreview,
    onClick: (trainerId: Int) -> Unit,
    actionContent: @Composable () -> Unit
) {
    Row(
        Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .clickable(onClick = { onClick(trainer.trainerId) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            imageUrl = trainer.profileImageUrl,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = trainer.username,
                style = SkyFitTypography.bodyLargeSemibold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = trainer.fullName,
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary
            )
        }
        Spacer(Modifier.width(24.dp))
        actionContent()
    }
}
