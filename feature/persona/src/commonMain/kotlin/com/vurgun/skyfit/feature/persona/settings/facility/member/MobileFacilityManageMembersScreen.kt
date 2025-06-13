package com.vurgun.skyfit.feature.persona.settings.facility.member

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.add_action
import skyfit.core.ui.generated.resources.members_label
import skyfit.core.ui.generated.resources.search_action

class FacilityMemberSettingsScreen(private val trainerId: Int? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityManageMembersViewModel>()


        LaunchedEffect(Unit) {
            viewModel.refreshGymMembers()
        }

        MobileFacilityManageMembersScreen(
            goToBack = { navigator.pop() },
            goToAddMember = { navigator.push(FacilityAddMemberScreen()) },
            viewModel = viewModel
        )
    }
}

@Composable
internal fun MobileFacilityManageMembersScreen(
    goToBack: () -> Unit,
    goToAddMember: () -> Unit,
    viewModel: FacilityManageMembersViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

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
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(uiState.filtered) {

                FacilitySettingMemberItem(
                    item = it,
                    onClickEdit = {

                    },
                    onClickDelete = { viewModel.deleteMember(it.userId) }
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
        SkyImage(
            url = item.profileImageUrl,
            shape = SkyImageShape.Circle,
            size = SkyImageSize.Size60
        )

        Spacer(Modifier.width(16.dp))

        Column(Modifier.weight(1f)) {
            SkyText(
                text = item.username,
                styleType = TextStyleType.BodyLargeSemibold
            )
            Spacer(Modifier.height(4.dp))
            SkyText(
                text = item.fullName,
                styleType = TextStyleType.BodyMediumRegular,
                color = SkyFitColor.text.secondary
            )
        }
        Spacer(Modifier.width(24.dp))
        actionContent()
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