package com.vurgun.skyfit.feature_lessons.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.now
import com.vurgun.skyfit.feature_lessons.ui.components.LessonSessionColumnItem
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionItemViewData
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.activate
import skyfit.composeapp.generated.resources.delete
import skyfit.composeapp.generated.resources.disable
import skyfit.composeapp.generated.resources.edit
import skyfit.composeapp.generated.resources.ic_check_circle
import skyfit.composeapp.generated.resources.ic_close_circle
import skyfit.composeapp.generated.resources.ic_delete
import skyfit.composeapp.generated.resources.ic_pencil
import skyfit.composeapp.generated.resources.lessons
import skyfit.composeapp.generated.resources.out_of_use

@Composable
fun MobileFacilityLessonsScreen(navigator: Navigator) {
    val viewModel = remember { FacilityClassesViewModel() }
    val activeClasses by viewModel.activeClasses.collectAsState()
    val inactiveClasses by viewModel.inactiveClasses.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadData(LocalDate.now())
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.lessons), onClickBack = { navigator.popBackStack() })
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            ActiveSessionItemsColumn(
                items = activeClasses,
                onClickNew = { navigator.jumpAndStay(NavigationRoute.FacilityClassEdit) },
                onEdit = { navigator.jumpAndStay(NavigationRoute.FacilityClassEdit) },
                onDeactivate = { viewModel.toggleClassStatus(it.sessionId) },
                onDelete = { viewModel.deleteClass(it.sessionId) }
            )

            Spacer(Modifier.height(40.dp))

            InactiveSessionItemsColumn(
                items = inactiveClasses,
                onClickNew = { navigator.jumpAndStay(NavigationRoute.FacilityClassEdit) },
                onEdit = { navigator.jumpAndStay(NavigationRoute.FacilityClassEdit) },
                onActivate = { viewModel.toggleClassStatus(it.sessionId) },
                onDelete = { viewModel.deleteClass(it.sessionId) }
            )
        }
    }
}

@Composable
private fun ActiveSessionItemsColumn(
    items: List<LessonSessionItemViewData>,
    onClickNew: () -> Unit,
    onDeactivate: (LessonSessionItemViewData) -> Unit,
    onEdit: (LessonSessionItemViewData) -> Unit,
    onDelete: (LessonSessionItemViewData) -> Unit
) {
    var openMenuItemId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
                modifier = Modifier.clickable(onClick = onClickNew)
            )
        }

        items.forEach { item ->
            LessonSessionColumnItem(
                item = item,
                isMenuOpen = openMenuItemId == item.sessionId,
                onMenuToggle = { isOpen -> openMenuItemId = if (isOpen) item.sessionId else null },
                onClickItem = {},
                menuContent = {
                    FacilityClassOptionsMenuPopup(
                        isOpen = openMenuItemId == item.sessionId,
                        onDismiss = { openMenuItemId = null },
                        onDeactivate = { onDeactivate(item) },
                        onEdit = { onEdit(item) },
                        onDelete = { onDelete(item) }
                    )
                }
            )
        }
    }
}

@Composable
private fun InactiveSessionItemsColumn(
    items: List<LessonSessionItemViewData>,
    onClickNew: () -> Unit,
    onActivate: (LessonSessionItemViewData) -> Unit,
    onEdit: (LessonSessionItemViewData) -> Unit,
    onDelete: (LessonSessionItemViewData) -> Unit
) {
    var openMenuItemId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(Res.string.out_of_use), style = SkyFitTypography.heading5)
            Spacer(Modifier.weight(1f))
        }

        items.forEach { item ->
            LessonSessionColumnItem(
                item = item,
                isMenuOpen = openMenuItemId == item.sessionId,
                onMenuToggle = { isOpen -> openMenuItemId = if (isOpen) item.sessionId else null },
                onClickItem = {},
                menuContent = {
                    FacilityClassOptionsMenuPopup(
                        isOpen = openMenuItemId == item.sessionId,
                        onDismiss = { openMenuItemId = null },
                        onActivate = { onActivate(item) },
                        onEdit = { onEdit(item) },
                        onDelete = { onDelete(item) }
                    )
                }
            )
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
                                        text = stringResource(Res.string.disable),
                                        style = SkyFitTypography.bodyMediumRegular
                                    )
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_close_circle),
                                        contentDescription = stringResource(Res.string.disable),
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
                                        text = stringResource(Res.string.activate),
                                        style = SkyFitTypography.bodyMediumRegular
                                    )
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_check_circle),
                                        contentDescription = stringResource(Res.string.activate),
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
                                    text = stringResource(Res.string.edit),
                                    style = SkyFitTypography.bodyMediumRegular
                                )
                                Icon(
                                    painter = painterResource(Res.drawable.ic_pencil),
                                    contentDescription = stringResource(Res.string.edit),
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
                                    text = stringResource(Res.string.delete),
                                    style = SkyFitTypography.bodyMediumRegular,
                                    color = SkyFitColor.text.criticalOnBgFill
                                )
                                Icon(
                                    painter = painterResource(Res.drawable.ic_delete),
                                    contentDescription = stringResource(Res.string.delete),
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
