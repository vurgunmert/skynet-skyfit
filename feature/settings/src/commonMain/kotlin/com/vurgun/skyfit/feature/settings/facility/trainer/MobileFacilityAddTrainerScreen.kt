package com.vurgun.skyfit.feature.settings.facility.trainer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.core.ui.components.button.SecondaryMicroButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.add_action
import skyfit.core.ui.generated.resources.ic_plus
import skyfit.core.ui.generated.resources.search_action

@Composable
internal fun MobileFacilityAddTrainerScreen(
    goToBack: () -> Unit,
    viewModel: FacilityAddTrainerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refreshPlatformTrainers()
    }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                SkyFitScreenHeader(stringResource(Res.string.add_action), onClickBack = goToBack)

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
                        SecondaryMicroButton(
                            text = stringResource(Res.string.add_action),
                            modifier = Modifier.wrapContentWidth(),
                            onClick = { viewModel.addTrainer(it.userId) },
                            leftIconPainter = painterResource(Res.drawable.ic_plus)
                        )
                    }
                )
            }
        }
    }
}
