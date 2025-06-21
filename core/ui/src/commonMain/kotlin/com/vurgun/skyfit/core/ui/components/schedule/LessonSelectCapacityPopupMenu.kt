package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.popup.BasicPopupMenu
import com.vurgun.skyfit.core.ui.components.popup.SelectablePopupMenuItem
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun LessonSelectCapacityPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedCapacity: Int = 1,
    maxCapacity: Int = 50,
    onSelectionChanged: (Int) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {
            (1..maxCapacity).forEach { value ->
                SelectablePopupMenuItem(
                    selected = value == selectedCapacity,
                    onSelect = {
                        onSelectionChanged(value)
                        onDismiss()
                    },
                    content = {
                        BodyMediumRegularText(value.toString(), modifier = Modifier.weight(1f))
                    }
                )
                if (value != maxCapacity) {
                    Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                }
            }
        }
    }
}

@Composable
fun LessonSelectCategoryPopupMenu(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onAddNew: () -> Unit,
    allCategories: Set<LessonCategory>,
    selectedCategories: Set<LessonCategory>,
    onSelectionChanged: (Set<LessonCategory>) -> Unit
) {
    BoxWithConstraints(modifier.fillMaxWidth()) {
        BasicPopupMenu(
            modifier = Modifier.width(this.maxWidth),
            isOpen = isOpen,
            onDismiss = onDismiss
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                SkyButton(
                    label = "Kategori Ekle",
                    size = SkyButtonSize.Micro,
                    onClick = onAddNew
                )
            }

            allCategories.forEach { category ->
                val isSelected = selectedCategories.contains(category)

                SelectablePopupMenuItem(
                    selected = isSelected,
                    onSelect = {
                        val newSelection = when {
                            isSelected && selectedCategories.size > 1 -> {
                                selectedCategories - category
                            }

                            !isSelected -> {
                                selectedCategories + category
                            }

                            else -> {
                                // Prevent deselecting the last remaining item
                                selectedCategories
                            }
                        }

                        onSelectionChanged(newSelection)
                    },
                    content = {
                        SkyText(
                            text = category.name,
                            styleType = TextStyleType.BodyMediumRegular,
                            modifier = Modifier.weight(1f)
                        )
                    }
                )

                Divider(
                    Modifier.fillMaxWidth(),
                    color = SkyFitColor.border.default
                )
            }
        }
    }
}


