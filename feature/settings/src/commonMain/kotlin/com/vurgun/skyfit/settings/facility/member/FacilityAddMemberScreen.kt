package com.vurgun.skyfit.settings.facility.member

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
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
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.ExpandedTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.add_action
import fiwe.core.ui.generated.resources.ic_plus
import fiwe.core.ui.generated.resources.search_action

class FacilityAddMemberScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityAddMembersViewModel>()

        LaunchedEffect(Unit) {
            viewModel.refreshPlatformMembers()
        }

        MobileFacilityAddMemberScreen(
            goToBack = { navigator.pop() },
            onSelectMember = { navigator.push(SharedScreen.UserProfile(it)) },
            viewModel = viewModel
        )
    }
}


@Composable
internal fun MobileFacilityAddMemberScreen(
    goToBack: () -> Unit,
    onSelectMember: (normalUserId: Int) -> Unit,
    viewModel: FacilityAddMembersViewModel
) {
    val windowSize = LocalWindowSize.current
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {

                if (windowSize == WindowSize.EXPANDED) {
                    CompactTopBar(stringResource(Res.string.add_action), onClickBack = goToBack)
                } else {
                    ExpandedTopBar(stringResource(Res.string.add_action), onClickBack = goToBack)
                }

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
                MobileFacilityMemberItemComponent(
                    item = it,
                    onClick = { onSelectMember(it.normalUserId) },
                    actionContent = {
                        SkyButton(
                            label = stringResource(Res.string.add_action),
                            leftIcon = painterResource(Res.drawable.ic_plus),
                            size = SkyButtonSize.Micro,
                            variant = SkyButtonVariant.Secondary,
                            onClick = { viewModel.addMember(it.userId) },
                        )
                    }
                )
            }
        }
    }
}