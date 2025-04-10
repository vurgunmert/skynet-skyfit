package com.vurgun.skyfit.feature.settings.facility.member

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.data.settings.model.Member
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonState
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.add_action
import skyfit.ui.core.generated.resources.delete_action
import skyfit.ui.core.generated.resources.members_label
import skyfit.ui.core.generated.resources.search_action

@Composable
internal fun MobileFacilityManageMembersScreen(
    goToBack: () -> Unit,
    goToAddMember: () -> Unit,
    viewModel: FacilityManageMembersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.refreshGymMembers()
    }

    SkyFitMobileScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                MobileFacilitySettingsSearchUserToolbarComponent(
                    title = stringResource(Res.string.members_label),
                    onClickBack = goToBack,
                    onClickAdd = goToAddMember
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
                MobileFacilityMemberItemComponent(
                    item = it,
                    onClick = {},
                    actionContent = {
                        SkyFitButtonComponent(
                            text = stringResource(Res.string.delete_action),
                            modifier = Modifier.wrapContentWidth(),
                            onClick = { viewModel.deleteMember(it.userId) },
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
                .wrapContentWidth(), text = stringResource(Res.string.add_action),
            onClick = onClickAdd,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    }
}

@Composable
fun MobileFacilityMemberItemComponent(
    item: Member,
    onClick: () -> Unit,
    actionContent: @Composable () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            imageUrl = item.profilePhoto,
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
                text = "${item.name} ${item.surname}",
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary
            )
        }
        Spacer(Modifier.width(24.dp))
        actionContent()
    }
}