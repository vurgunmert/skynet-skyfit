package com.vurgun.skyfit.settings.facility.member

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.EditMemberPackageDialog
import com.vurgun.skyfit.core.ui.components.special.FacilitySettingMemberItem
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.components.special.SkyPageScaffold
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

class FacilityMemberSettingsScreen(private val facilityId: Int? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityMemberSettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadData(facilityId)
        }

        when (val state = uiState) {
            is FacilityMemberSettingsUiState.Error -> {
                ErrorScreen(message = state.message, onConfirm = { navigator.pop() })
            }

            FacilityMemberSettingsUiState.Loading -> {
                FullScreenLoaderContent()
            }

            is FacilityMemberSettingsUiState.Content -> {
                ManageMembersContent(
                    goToBack = { navigator.pop() },
                    goToAddMember = { navigator.push(FacilityAddMemberScreen()) },
                    deleteMember = viewModel::deleteMember,
                    updateSearchQuery = viewModel::updateSearchQuery,
                    updateMemberPackage = viewModel::updateMemberPackage,
                    deleteMemberPackage = viewModel::deleteMemberPackage,
                    onSelectMember = { navigator.push(SharedScreen.UserProfile(it)) },
                    content = state
                )
            }
        }
    }
}

@Composable
private fun ManageMembersContent(
    goToBack: () -> Unit,
    goToAddMember: () -> Unit,
    deleteMember: (userId: Int) -> Unit,
    updateSearchQuery: (query: String) -> Unit,
    updateMemberPackage: (userId: Int, packageId: Int) -> Unit,
    deleteMemberPackage: (userId: Int, packageId: Int) -> Unit,
    onSelectMember: (normalUserId: Int) -> Unit,
    content: FacilityMemberSettingsUiState.Content
) {
    var editedMember by remember { mutableStateOf<Member?>(null) }

    SkyPageScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                MemberSettingsTopBar(
                    title = stringResource(Res.string.members_label),
                    onClickBack = goToBack,
                    onClickAdd = goToAddMember
                )

                SkyFitSearchTextInputComponent(
                    hint = stringResource(Res.string.search_action),
                    value = content.query,
                    onValueChange = { updateSearchQuery(it) },
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
            items(content.members) { member ->

                FacilitySettingMemberItem(
                    item = member,
                    onClickItem = { onSelectMember(member.normalUserId) },
                    onClickEdit = { editedMember = member },
                    onClickDelete = { deleteMember(member.userId) }
                )
            }
        }
    }

    editedMember?.let { member ->
        EditMemberPackageDialog(
            showDialog = editedMember != null,
            packages = content.packages,
            member = member,
            onClickSave = { updateMemberPackage(member.userId, it) },
            onClickDelete = { deleteMemberPackage(member.userId, it) },
            onClickCancel = { editedMember = null }
        )
    }

}

@Composable
private fun MemberSettingsTopBar(
    title: String,
    onClickBack: () -> Unit,
    onClickAdd: () -> Unit
) {
    val windowSize = LocalWindowSize.current

    Box(Modifier.fillMaxWidth().padding(vertical = 12.dp), contentAlignment = Alignment.CenterEnd) {

        if (windowSize == WindowSize.EXPANDED) {
            CompactTopBar(title, onClickBack = null)
        } else {
            CompactTopBar(title, onClickBack = onClickBack)
        }

        SkyButton(
            label = stringResource(Res.string.add_action),
            size = SkyButtonSize.Micro,
            onClick = onClickAdd,
            modifier = Modifier.padding(end = 24.dp)
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
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkyImage(
            url = item.profileImageUrl,
            shape = SkyImageShape.Circle,
            size = SkyImageSize.Size60,
            error = Res.drawable.ic_profile
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