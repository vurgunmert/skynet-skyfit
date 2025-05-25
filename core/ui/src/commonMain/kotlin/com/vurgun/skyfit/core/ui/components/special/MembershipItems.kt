package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vurgun.skyfit.core.data.persona.domain.model.Member
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.chip.RectangleChip
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.popup.TextPopupMenuItem
import com.vurgun.skyfit.core.ui.components.progress.RoundedLinearProgress
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.CardFieldIconText
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.model.ServicePackageUiData
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

object SettingsPackageComponent {

    @Composable
    fun PackageCard(
        content: ServicePackageUiData,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SkyFitColor.background.fillTransparentSecondary,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SkyIcon(
                    res = Res.drawable.ic_package,
                    size = SkyIconSize.Large,
                )
                Spacer(Modifier.width(8.dp))
                SkyText(
                    text = content.title,
                    styleType = TextStyleType.BodyMediumSemibold
                )
            }

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = SkyFitColor.background.default,
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(8.dp)
            ) {
                SkyText(
                    text = stringResource(Res.string.course_contents_label),
                    styleType = TextStyleType.BodyMediumSemibold
                )
                Spacer(Modifier.height(8.dp))
                SkyText(
                    text = content.courseList.joinToString("\n") { "- $it" },
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = content.onMembersClick)
                    .fillMaxWidth()
                    .background(SkyFitColor.background.default)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkyIcon(
                    res = Res.drawable.ic_profile,
                    size = SkyIconSize.Normal,
                    tint = SkyIconTint.Secondary
                )
                Spacer(Modifier.width(6.dp))

                SkyText(
                    text = stringResource(Res.string.number_of_member_label, content.memberCount),
                    styleType = TextStyleType.BodyMediumRegular,
                    color = SkyFitColor.text.secondary,
                    modifier = Modifier.weight(1f),
                )

                SkyIcon(
                    res = Res.drawable.ic_chevron_right,
                    size = SkyIconSize.Normal,
                    tint = SkyIconTint.Secondary
                )
            }

            Spacer(Modifier.height(8.dp))

            Row {
                CardFieldIconText(
                    iconRes = Res.drawable.ic_clock,
                    text = stringResource(Res.string.number_of_month_label, content.duration),
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(8.dp))

                CardFieldIconText(
                    iconRes = Res.drawable.ic_lira,
                    text = if (content.price <= 0f) stringResource(Res.string.free_label) else content.price.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
            ) {
                SkyButton(
                    label = stringResource(Res.string.delete_action),
                    variant = SkyButtonVariant.Destructive,
                    size = SkyButtonSize.Micro,
                    leftIcon = painterResource(Res.drawable.ic_delete),
                    onClick = content.onDeleteClick
                )
                SkyButton(
                    label = stringResource(Res.string.edit_action),
                    variant = SkyButtonVariant.Secondary,
                    size = SkyButtonSize.Micro,
                    onClick = content.onEditClick
                )
            }
        }
    }

    @Composable
    fun Standard() {
        RectangleChip(
            "Standart Paket",
            backgroundColor = SkyFitColor.background.surfaceWarning
        )
    }

    @Composable
    fun Special() {
        RectangleChip(
            "Standart Paket",
            backgroundColor = SkyFitColor.background.surfaceWarning
        )
    }

    @Composable
    fun RemainingUsage() {
        RectangleChip(
            "4/16",
            backgroundColor = SkyFitColor.background.fillTransparentSecondary
        )
    }

    @Composable
    fun NoRemainingUsage() {
        RectangleChip(
            "16/16",
            backgroundColor = SkyFitColor.background.fillTransparentSecondary,
            rightIconRes = Res.drawable.ic_warning_diamond,
            rightIconTint = SkyIconTint.Critical
        )
    }
}

@Composable
fun FacilitySettingMemberItem(
    item: Member,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit,
) {
    var isMenuOpen by remember { mutableStateOf<Boolean>(false) }
    var isEditDialogVisible by remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth(),
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
                text = item.fullName,
                styleType = TextStyleType.BodyLargeSemibold
            )
            Spacer(Modifier.height(8.dp))
            Row {
                SettingsPackageComponent.Standard()
                Spacer(Modifier.width(8.dp))
                SettingsPackageComponent.RemainingUsage()
            }
        }
        Spacer(Modifier.width(16.dp))
        Box {
            SkyIcon(
                res = Res.drawable.ic_dots_vertical,
                size = SkyIconSize.Small,
                modifier = Modifier.clickable { isMenuOpen = !isMenuOpen }
            )

            MemberOptionMenu(
                isOpen = isMenuOpen,
                onDismiss = { isMenuOpen = false },
                onEdit = { isEditDialogVisible = true },
                onDelete = onClickDelete
            )
        }
    }


    if (isEditDialogVisible) {
        EditMemberPackageDialog(
            showDialog = isEditDialogVisible,
            onClickSave = { },
            onClickCancel = { isEditDialogVisible = false },
            onClickDelete = { }
        )
    }
}

@Composable
private fun MemberOptionMenu(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    BasicPopupMenu(
        modifier = Modifier.widthIn(min = 224.dp),
        isOpen = isOpen,
        onDismiss = onDismiss
    ) {
        TextPopupMenuItem(
            text = stringResource(Res.string.edit_member_package_action),
            iconRes = Res.drawable.ic_pencil,
            onClick = {
                onEdit()
                onDismiss()
            }
        )

        Divider(color = SkyFitColor.border.default, thickness = 1.dp)

        TextPopupMenuItem(
            text = stringResource(Res.string.delete_membership_action),
            iconRes = Res.drawable.ic_delete,
            onClick = {
                onDelete()
                onDismiss()
            },
            textColor = SkyFitColor.text.criticalOnBgFill,
            iconTint = SkyFitColor.icon.critical
        )
    }
}

@Composable
fun EditMemberPackageDialog(
    showDialog: Boolean,
    onClickSave: (option: String) -> Unit,
    onClickCancel: () -> Unit,
    onClickDelete: () -> Unit,
) {
    val isFull: Boolean = false
    var isPackageListOpen by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Standart") }

    if (showDialog) {
        Dialog(
            onDismissRequest = onClickCancel,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceTertiary)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .background(SkyFitColor.background.default)
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        SkyText(
                            text = stringResource(Res.string.edit_member_package_action),
                            styleType = TextStyleType.BodyLarge
                        )
                        Spacer(Modifier.weight(1f))
                        SkyIcon(
                            res = Res.drawable.ic_close,
                            size = SkyIconSize.Medium,
                            onClick = onClickCancel
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        SkyText(
                            text = stringResource(Res.string.completed_lesson_count_label),
                            styleType = TextStyleType.BodyMediumSemibold
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RoundedLinearProgress(
                                progress = 0.4f,
                                modifier = Modifier.weight(1f),
                            )

                            Spacer(Modifier.width(8.dp))
                            SkyText(
                                text = "4/16",
                                styleType = TextStyleType.BodyMediumRegular
                            )

                            if (isFull) {
                                Spacer(Modifier.width(8.dp))
                                SkyIcon(
                                    res = Res.drawable.ic_warning_diamond,
                                    size = SkyIconSize.Small,
                                    tint = SkyIconTint.Critical
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        SkyText(
                            text = stringResource(Res.string.package_selection_label),
                            styleType = TextStyleType.BodyMediumSemibold
                        )
                        Spacer(Modifier.height(8.dp))

                        SkyText(
                            text = selectedOption,
                            styleType = TextStyleType.BodyLarge,
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .fillMaxWidth()
                                .clickable { isPackageListOpen = !isPackageListOpen }
                                .background(SkyFitColor.background.surfaceSecondary)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            color = SkyFitColor.text.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                    ) {
                        SkyButton(
                            label = stringResource(Res.string.delete_action),
                            leftIcon = painterResource(Res.drawable.ic_delete),
                            variant = SkyButtonVariant.Destructive,
                            onClick = onClickDelete
                        )
                        SkyButton(
                            label = stringResource(Res.string.cancel_action),
                            variant = SkyButtonVariant.Secondary,
                            onClick = onClickCancel
                        )
                        SkyButton(
                            label = stringResource(Res.string.save_action),
                            variant = SkyButtonVariant.Primary,
                            onClick = { onClickSave(selectedOption) }
                        )
                    }
                }
            }
        }
    }

    if (isPackageListOpen) {
        SelectPackagePopupMenu(
            modifier = Modifier.fillMaxWidth(),
            isOpen = isPackageListOpen,
            onDismiss = { isPackageListOpen = false },
            selectedOption = "Standart",
            options = listOf("Standart", "Ozel", "Premium"),
            onSelectionChanged = { selectedOption = it }
        )
    }
}

@Composable
fun SelectPackagePopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedOption: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            options.forEachIndexed { index, value ->
                SelectablePopupMenuItem(
                    selected = value == selectedOption,
                    onSelect = {
                        onSelectionChanged(value)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(value, modifier = Modifier.weight(1f))
                    }
                )
                if (index != options.lastIndex) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}