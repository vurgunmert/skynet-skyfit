package com.vurgun.skyfit.feature.persona.settings.facility.packages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.dialog.DestructiveDialog
import com.vurgun.skyfit.core.ui.components.form.*
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class FacilityPackageEditScreen(
    private val lessonPackage: FacilityLessonPackageDTO? = null
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityPackageEditViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val formState by viewModel.formState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityPackageEditEffect.NavigateToBack ->
                    navigator.pop()
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(lessonPackage)
        }

        when (uiState) {
            FacilityPackageEditUiState.Loading ->
                FullScreenLoaderContent()

            is FacilityPackageEditUiState.Error -> {
                val message = (uiState as FacilityPackageEditUiState.Error).message
                ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }

            is FacilityPackageEditUiState.Content -> {
                val content = (uiState as FacilityPackageEditUiState.Content)
                CreateServicePackageScreen_Content(content, formState, viewModel::onAction)
            }
        }
    }
}

@Composable
private fun CreateServicePackageScreen_Content(
    content: FacilityPackageEditUiState.Content,
    formState: FacilityPackageEditFormState,
    onAction: (FacilityPackageEditAction) -> Unit,
) {
    var showCancelDialog by remember { mutableStateOf(false) }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.create_package_action), onClickBack = {
                if (formState.isReadyToSave) {
                    showCancelDialog = true
                } else {
                    onAction(FacilityPackageEditAction.OnClickBack)
                }
            })
        }
    ) {

        Column(
            modifier = Modifier
                .padding(LocalPadding.current.medium)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            SkyFormTextField(
                title = stringResource(Res.string.package_title_label),
                hint = stringResource(Res.string.package_title_hint),
                value = formState.title,
                onValueChange = { onAction(FacilityPackageEditAction.OnTitleChanged(it)) },
            )

            SkyFormInputNumberText(
                title = stringResource(Res.string.lesson_count_label),
                hint = stringResource(Res.string.lesson_count_input_hint),
                value = formState.lessonCount,
                onValueChange = { onAction(FacilityPackageEditAction.OnLessonCountChanged(it)) },
            )

            SkyFormSelectWorkoutCategory(
                title = stringResource(Res.string.course_contents_label),
                hint = stringResource(Res.string.course_contents_input_hint),
                availableTags = content.categories,
                selectedTags = formState.categories,
                onTagsSelected = { onAction(FacilityPackageEditAction.OnServicesChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
            )

            SkyFormMultilineTextField(
                title = stringResource(Res.string.package_description_title_label),
                hint = stringResource(Res.string.description_hint_add),
                value = formState.description,
                onValueChange = { onAction(FacilityPackageEditAction.OnDescriptionChanged(it)) },
            )

            SkyFormSelectText(
                title = stringResource(Res.string.valid_month_count_label),
                hint = stringResource(Res.string.select_action),
                options = (1..12).map { it.toString() },
                selected = formState.monthCount,
                onSelectionChanged = { onAction(FacilityPackageEditAction.OnDurationMonthChanged(it)) }
            )

            SkyFormSelectText(
                title = stringResource(Res.string.add_branch_action),
                hint = stringResource(Res.string.select_action),
                options = content.branches,
                selected = formState.branch,
                onSelectionChanged = { onAction(FacilityPackageEditAction.OnBranchChanged(it)) }
            )

            SkyFormInputCostText(
                cost = formState.price.toInt(),
                onChanged = { onAction(FacilityPackageEditAction.OnPriceChanged(it.toFloat())) }
            )

            SkyButton(
                label = stringResource(if (formState.isUpdateMode) Res.string.update_package_action else Res.string.create_package_action),
                leftIcon = painterResource(Res.drawable.ic_check),
                variant = SkyButtonVariant.Secondary,
                size = SkyButtonSize.Large,
                onClick = { onAction(FacilityPackageEditAction.OnClickSave) },
                enabled = formState.isReadyToSave,
                modifier = Modifier.fillMaxWidth()
            )
        }

        if (showCancelDialog) {
            DestructiveDialog(
                showDialog = showCancelDialog,
                message = stringResource(Res.string.lesson_edit_cancel_message),
                onClickConfirm = { onAction(FacilityPackageEditAction.OnClickBack) },
                onClickDismiss = { showCancelDialog = false }
            )
        }
    }
}