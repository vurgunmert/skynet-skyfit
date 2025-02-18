package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItemRowComponent
import com.vurgun.skyfit.presentation.shared.features.facility.FacilityClassesViewModel
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitIcon
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_check_circle
import skyfit.composeapp.generated.resources.ic_clock
import skyfit.composeapp.generated.resources.ic_close_circle
import skyfit.composeapp.generated.resources.ic_dashboard
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_dots_vertical
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.ic_profile_fill

@Composable
fun MobileFacilityClassesScreen(navigator: Navigator) {
    val viewModel = remember { FacilityClassesViewModel() }
    val activeClasses by viewModel.activeClasses.collectAsState()
    val inactiveClasses by viewModel.inactiveClasses.collectAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Dersler", onClickBack = { navigator.popBackStack() })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header for Active Classes
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Aktif", style = SkyFitTypography.heading5)
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "+ Yeni",
                        style = SkyFitTypography.bodyMediumRegular,
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.clickable { navigator.jumpAndStay(SkyFitNavigationRoute.FacilityClassEdit) }
                    )
                }
            }

            // Active Classes List
            items(activeClasses, key = { it.classId }) { item ->
                SkyFitClassItemComponent(
                    item = item,
                    onEdit = { navigator.jumpAndStay(SkyFitNavigationRoute.FacilityClassEdit) },
                    onDeactivate = { viewModel.toggleClassStatus(item.classId) },
                    onActivate = { viewModel.toggleClassStatus(item.classId) },
                    onDelete = { viewModel.deleteClass(item.classId) }
                )
            }

            // Header for Inactive Classes
            item {
                Spacer(Modifier.height(24.dp)) // Adds space before inactive section
                Text(text = "Kullanım dışı", style = SkyFitTypography.heading5)
            }

            // Inactive Classes List
            items(inactiveClasses, key = { it.classId }) { item ->
                SkyFitClassItemComponent(
                    item = item,
                    onEdit = { navigator.jumpAndStay(SkyFitNavigationRoute.FacilityClassEdit) },
                    onDeactivate = { viewModel.toggleClassStatus(item.classId) },
                    onActivate = { viewModel.toggleClassStatus(item.classId) },
                    onDelete = { viewModel.deleteClass(item.classId) }
                )
            }
        }
    }
}


@Composable
private fun SkyFitClassItemComponent(
    item: SkyFitClassCalendarCardItem,
    onEdit: (SkyFitClassCalendarCardItem) -> Unit,
    onActivate: (SkyFitClassCalendarCardItem) -> Unit,
    onDeactivate: (SkyFitClassCalendarCardItem) -> Unit,
    onDelete: (SkyFitClassCalendarCardItem) -> Unit
) {
    val textColor = if (item.enabled) SkyFitColor.text.default else SkyFitColor.text.disabled
    val subTextColor = if (item.enabled) SkyFitColor.text.secondary else SkyFitColor.text.disabled
    val iconColor = if (item.enabled) SkyFitColor.icon.default else SkyFitColor.icon.disabled
    var isMenuOpen by remember { mutableStateOf(false) }

    Box(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .then(
                if (item.selected) {
                    Modifier.border(
                        width = 1.dp,
                        color = SkyFitColor.border.secondaryButton,
                        shape = RoundedCornerShape(16.dp)
                    )
                } else Modifier
            )
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = SkyFitIcon.getIconResourcePainter(item.iconId, defaultRes = Res.drawable.ic_exercises),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = iconColor
                )
                Text(
                    text = item.title,
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.padding(start = 8.dp),
                    color = textColor
                )
                Spacer(Modifier.weight(1f))

                Box {
                    Icon(
                        painter = painterResource(Res.drawable.ic_dots_vertical),
                        contentDescription = "Options",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(16.dp).clickable { isMenuOpen = true }
                    )

                    if (isMenuOpen && item.enabled) {
                        FacilityClassOptionsMenuPopup(
                            isOpen = isMenuOpen,
                            onDismiss = { isMenuOpen = false },
                            onDeactivate = { onDeactivate(item) },
                            onEdit = { onEdit(item) },
                            onDelete = { onDelete(item) }
                        )
                    } else if (isMenuOpen && !item.enabled) {
                        FacilityClassOptionsMenuPopup(
                            isOpen = isMenuOpen,
                            onDismiss = { isMenuOpen = false },
                            onActivate = { onActivate(item) },
                            onEdit = { onEdit(item) },
                            onDelete = { onDelete(item) }
                        )
                    }
                }
            }

            item.hours?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_clock, subTextColor, iconColor)
            }

            item.trainer?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_profile_fill, subTextColor, iconColor)
            }

            item.category?.let {
                Spacer(Modifier.height(8.dp))
                SkyFitClassCalendarCardItemRowComponent(it, iconRes = Res.drawable.ic_dashboard, subTextColor, iconColor)
            }
        }
    }
}


@Composable
private fun FacilityClassOptionsMenuPopup(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onActivate: (() -> Unit)? = null,
    onDeactivate: (() -> Unit)? = null,
) {
    if (isOpen) {
        MaterialTheme(
            colors = MaterialTheme.colors.copy(surface = SkyFitColor.background.surfaceSecondary),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))
        ) {
            DropdownMenu(
                expanded = isOpen,
                onDismissRequest = { onDismiss() },
                modifier = Modifier
                    .width(160.dp)
                    .background(Color.Transparent) // Prevents overriding the rounded shape
            ) {
                Surface(elevation = 8.dp) {
                    Column {
                        if (onDeactivate != null) {
                            // Deactivate Option
                            DropdownMenuItem(
                                onClick = {
                                    onDeactivate()
                                    onDismiss()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Devre dışı bırak",
                                        style = SkyFitTypography.bodyMediumRegular
                                    )
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_close_circle),
                                        contentDescription = "Add Event",
                                        tint = SkyFitColor.icon.default,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Divider(
                                color = SkyFitColor.border.default,
                                thickness = 1.dp
                            )
                        }

                        if (onActivate != null) {
                            // Deactivate Option
                            DropdownMenuItem(
                                onClick = {
                                    onActivate()
                                    onDismiss()
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Aktifleştir",
                                        style = SkyFitTypography.bodyMediumRegular
                                    )
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_check_circle),
                                        contentDescription = "Activate",
                                        tint = SkyFitColor.icon.default,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Divider(
                                color = SkyFitColor.border.default,
                                thickness = 1.dp
                            )
                        }

                        // Edit Option
                        DropdownMenuItem(
                            onClick = {
                                onEdit()
                                onDismiss()
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Düzenle",
                                    style = SkyFitTypography.bodyMediumRegular
                                )
                                Icon(
                                    painter = painterResource(Res.drawable.ic_pencil),
                                    contentDescription = "Edit",
                                    tint = SkyFitColor.icon.default,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Divider(
                            color = SkyFitColor.border.default,
                            thickness = 1.dp
                        )

                        // "Delete" Option
                        DropdownMenuItem(
                            onClick = {
                                onDelete()
                                onDismiss()
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Sil",
                                    style = SkyFitTypography.bodyMediumRegular,
                                    color = SkyFitColor.text.criticalOnBgFill
                                )
                                Icon(
                                    painter = painterResource(Res.drawable.ic_delete),
                                    contentDescription = "Delete",
                                    tint = SkyFitColor.icon.critical,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
