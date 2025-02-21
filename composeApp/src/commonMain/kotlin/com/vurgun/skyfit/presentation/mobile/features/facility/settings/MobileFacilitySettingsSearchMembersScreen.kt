package com.vurgun.skyfit.presentation.mobile.features.facility.settings

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilitySettingsSearchMembersScreen(navigator: Navigator) {

    val viewModel = remember { FacilitySearchMembersViewModel() }
    val members by viewModel.members.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                MobileFacilitySettingsSearchUserToolbarComponent(
                    title = "Uyeler",
                    onClickBack = { navigator.popBackStack() },
                    onClickAdd = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsAddMembers) }
                )

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
            items(members) {
                MobileFacilityMemberItemComponent(
                    item = it,
                    onClick = {},
                    actionContent = {
                        SkyFitButtonComponent(
                            text = "Sil",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = { viewModel.deleteMember(it.memberId) },
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
fun MobileFacilitySettingsSearchUserToolbarComponent(
    title: String,
    onClickBack: () -> Unit,
    onClickAdd: () -> Unit
) {
    Box(Modifier.fillMaxWidth().padding(vertical = 12.dp), contentAlignment = Alignment.CenterEnd) {
        SkyFitScreenHeader(title, onClickBack = onClickBack)

        SkyFitButtonComponent(
            modifier = Modifier
                .padding(end = 24.dp)
                .wrapContentWidth(), text = "Ekle",
            onClick = onClickAdd,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    }
}

@Composable
fun MobileFacilityMemberItemComponent(
    item: FacilitySettingsMemberViewData,
    onClick: () -> Unit,
    actionContent: @Composable () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.profileImageUrl,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = item.userName,
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