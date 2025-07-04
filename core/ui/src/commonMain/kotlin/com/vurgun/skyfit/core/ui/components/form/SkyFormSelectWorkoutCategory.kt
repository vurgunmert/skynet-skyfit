package com.vurgun.skyfit.core.ui.components.form

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.data.v1.domain.workout.model.WorkoutCategory
import com.vurgun.skyfit.core.ui.components.special.SkyFitAccountSettingsProfileTagItemComponent

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkyFormSelectWorkoutCategory(
    title: String,
    hint: String,
    modifier: Modifier = Modifier,
    availableTags: List<WorkoutCategory>,
    selectedTags: List<WorkoutCategory>,
    onTagsSelected: (List<WorkoutCategory>) -> Unit
) {
    Column(modifier.fillMaxWidth()) {

        SkyFormSelectText(
            title = title,
            hint = hint,
            options = (availableTags - selectedTags).map { it.name },
            selected = null,
            onSelectionChanged = { selectedOption ->
                onTagsSelected(selectedTags + availableTags.first { it.name == selectedOption })
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
                    value = it.name,
                    onClick = { onTagsSelected(selectedTags - it) })
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkyFormSelectLessonCategory(
    title: String,
    hint: String,
    modifier: Modifier = Modifier,
    availableCategories: List<LessonCategory>,
    selectedCategories: List<LessonCategory>,
    onSelectionChanged: (List<LessonCategory>) -> Unit
) {
    Column(modifier.fillMaxWidth()) {

        SkyFormSelectText(
            title = title,
            hint = hint,
            options = (availableCategories - selectedCategories).map { it.name },
            selected = null,
            onSelectionChanged = { selectedOption ->
                onSelectionChanged(selectedCategories + availableCategories.first { it.name == selectedOption })
            }
        )

        if (selectedCategories.isNotEmpty()) {
            Spacer(Modifier.height(12.dp))

            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                selectedCategories.forEach {
                    SkyFitAccountSettingsProfileTagItemComponent(
                        value = it.name,
                        onClick = { onSelectionChanged(selectedCategories - it) })
                }
            }
        }
    }
}