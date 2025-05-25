package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutTag
import com.vurgun.skyfit.core.ui.components.special.SkyFitAccountSettingsProfileTagItemComponent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkyFormSelectWorkoutTag(
    title: String,
    hint: String,
    modifier: Modifier = Modifier,
    availableTags: List<WorkoutTag>,
    selectedTags: List<WorkoutTag>,
    onTagsSelected: (List<WorkoutTag>) -> Unit
) {
    Column(modifier.fillMaxWidth()) {

        SkyFormSelectText(
            title = title,
            hint = hint,
            options = (availableTags - selectedTags).map { it.tagName },
            selected = null,
            onSelectionChanged = { selectedOption ->
                onTagsSelected(selectedTags + availableTags.first { it.tagName == selectedOption })
            }
        )

        Spacer(Modifier.height(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            selectedTags.forEach {
                SkyFitAccountSettingsProfileTagItemComponent(
                    value = it.tagName,
                    onClick = { onTagsSelected(selectedTags - it) })
            }
        }
    }
}