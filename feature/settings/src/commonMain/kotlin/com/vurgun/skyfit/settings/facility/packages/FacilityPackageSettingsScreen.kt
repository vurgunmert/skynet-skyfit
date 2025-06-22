package com.vurgun.skyfit.settings.facility.packages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SettingsPackageComponent.PackageCard
import com.vurgun.skyfit.core.ui.components.topbar.CompactTopBar
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.screen.UnderDevelopmentScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.add_action
import skyfit.core.ui.generated.resources.packages_label

class FacilityPackageSettingsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityPackageListingViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityPackageListingEffect.NavigateToBack -> {
                    navigator.pop()
                }

                FacilityPackageListingEffect.NavigateToNewPackage ->
                    navigator.push(FacilityPackageEditScreen())

                FacilityPackageListingEffect.NavigateToMembers ->
                    navigator.push(UnderDevelopmentScreen())

                is FacilityPackageListingEffect.NavigateToEditPackage -> {
                    navigator.push(FacilityPackageEditScreen(effect.lessonPackage))
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            FacilityPackageListingUiState.Loading ->
                FullScreenLoaderContent()

            is FacilityPackageListingUiState.Error -> {
                val message = (uiState as FacilityPackageListingUiState.Error).message
                ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }

            is FacilityPackageListingUiState.Content -> {
                val content = (uiState as FacilityPackageListingUiState.Content)
                ScreenContent(content, viewModel::onAction)
            }
        }
    }

    @Composable
    private fun ScreenContent(
        content: FacilityPackageListingUiState.Content,
        onAction: (FacilityPackageListingAction) -> Unit
    ) {
        val windowSize = LocalWindowSize.current

        Scaffold(
            topBar = {
                if (windowSize == WindowSize.EXPANDED) { //TODO: Move logic repeating actions
                    CompactTopBar.TopbarWithEndAction(
                        title = stringResource(Res.string.packages_label),
                        onClickBack = null,
                        actionLabel = stringResource(Res.string.add_action),
                        onClickAction = { onAction(FacilityPackageListingAction.OnClickNew) }
                    )
                } else {
                    CompactTopBar.TopbarWithEndAction(
                        title = stringResource(Res.string.packages_label),
                        onClickBack = { onAction(FacilityPackageListingAction.OnClickBack) },
                        actionLabel = stringResource(Res.string.add_action),
                        onClickAction = { onAction(FacilityPackageListingAction.OnClickNew) }
                    )
                }
            }
        ) {

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(content.packages) { pkg ->
                    PackageCard(pkg)
                }
            }
        }
    }
}