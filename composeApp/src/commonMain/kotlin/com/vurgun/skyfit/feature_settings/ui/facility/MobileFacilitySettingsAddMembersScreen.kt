package com.vurgun.skyfit.feature_settings.ui.facility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.SkyFitSearchTextInputComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_plus

@Composable
fun MobileFacilitySettingsAddMembersScreen(navigator: Navigator) {

    val viewModel = remember { FacilityAddMembersViewModel() }
    val skyFitUsers by viewModel.skyFitUsers.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                SkyFitScreenHeader("Ekle", onClickBack = { navigator.popBackStack() })

                SkyFitSearchTextInputComponent(
                    hint = "Ara",
                    value = searchQuery,
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
            items(skyFitUsers) {
                MobileFacilityMemberItemComponent(
                    item = it,
                    onClick = {},
                    actionContent = {
                        SkyFitButtonComponent(
                            text = "Ekle",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = { viewModel.addMember(it.memberId) },
                            variant = ButtonVariant.Secondary,
                            size = ButtonSize.Micro,
                            state = ButtonState.Rest,
                            leftIconPainter = painterResource(Res.drawable.ic_plus)
                        )
                    }
                )
            }
        }
    }
}