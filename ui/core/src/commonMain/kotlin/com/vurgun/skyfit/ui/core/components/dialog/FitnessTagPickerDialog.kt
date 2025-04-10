package com.vurgun.skyfit.ui.core.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.data.core.domain.model.FitnessTagType
import com.vurgun.skyfit.ui.core.components.special.SkyFitAccountSettingsProfileTagItemComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FitnessTagPickerDialog(
    initialTags: List<FitnessTagType> = listOf(),
    availableTags: List<FitnessTagType> = FitnessTagType.getAllTags(),
    onDismiss: () -> Unit,
    onTagsSelected: (List<FitnessTagType>) -> Unit
) {
    var selectedTags by remember { mutableStateOf(initialTags) }
    var unselectedTags by remember { mutableStateOf(availableTags - initialTags.toSet()) }

    fun toggleTagSelection(tag: FitnessTagType) {
        if (selectedTags.contains(tag)) {
            selectedTags = selectedTags - tag
            unselectedTags = (unselectedTags + tag)
        } else {
            if (selectedTags.size >= 5) {
                val removedTag = selectedTags.first()
                selectedTags = selectedTags.drop(1) + tag
                unselectedTags = (unselectedTags - tag + removedTag)
            } else {
                selectedTags = selectedTags + tag
                unselectedTags = unselectedTags - tag
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
                .fillMaxHeight(0.9f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Etiket Seçimi",
                    style = SkyFitTypography.bodyMediumRegular
                )
                Spacer(Modifier.height(12.dp))

                Text("Seçilen Etiketler", style = SkyFitTypography.bodyMediumSemibold)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedTags.forEach { tag ->
                        SkyFitAccountSettingsProfileTagItemComponent(
                            value = tag.label,
                            enabled = true,
                            showClose = false,
                            onClick = { toggleTagSelection(tag) }
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))

                Text("Mevcut Etiketler", style = SkyFitTypography.bodyMediumSemibold)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    unselectedTags.forEach { tag ->
                        SkyFitAccountSettingsProfileTagItemComponent(
                            value = tag.label,
                            enabled = true,
                            onClick = { toggleTagSelection(tag) }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal", style = SkyFitTypography.bodyMediumSemibold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onTagsSelected(selectedTags)
                            onDismiss()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SkyFitColor.specialty.buttonBgRest
                        )
                    ) {
                        Text(
                            text = "Onayla",
                            style = SkyFitTypography.bodyMediumSemibold,
                            color = SkyFitColor.text.inverse
                        )
                    }
                }
            }
        }
    }
}