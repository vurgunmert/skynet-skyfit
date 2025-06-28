package com.vurgun.skyfit.settings.facility.trainer

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.LegacySecondaryMicroButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.add_action
import fiwe.core.ui.generated.resources.ic_plus
import fiwe.core.ui.generated.resources.search_action

internal class FacilityAddTrainerScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityAddTrainerViewModel>()

        LaunchedEffect(Unit) {
            viewModel.refreshPlatformTrainers()
        }

        MobileFacilityAddTrainerScreen(
            goToBack = { navigator.pop() },
            viewModel = viewModel
        )
    }

}

@Composable
private fun MobileFacilityAddTrainerScreen(
    goToBack: () -> Unit,
    viewModel: FacilityAddTrainerViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                CompactTopBar(stringResource(Res.string.add_action), onClickBack = goToBack)

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
                    onClick = {},
                    actionContent = {
                        LegacySecondaryMicroButton(
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
