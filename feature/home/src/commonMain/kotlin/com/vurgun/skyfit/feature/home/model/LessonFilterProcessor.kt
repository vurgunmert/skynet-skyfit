package com.vurgun.skyfit.feature.home.model

import androidx.compose.ui.util.fastFilter
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.home.component.LessonFilterData

/**
 * Processor class responsible for filtering lessons based on filter criteria.
 * This class encapsulates the filtering logic to improve separation of concerns.
 */
class LessonFilterProcessor {

    /**
     * Applies filter criteria to a list of lessons and returns the filtered result.
     *
     * @param lessons The list of lessons to filter
     * @param filter The filter criteria to apply
     * @return The filtered list of lessons
     */
    fun applyFilter(
        lessons: List<LessonSessionItemViewData>,
        filter: LessonFilterData
    ): List<LessonSessionItemViewData> {
        var result = lessons

        // Apply text search filter
        filter.query
            ?.takeIf { it.isNotBlank() }
            ?.let { query ->
                result = result.fastFilter { it.title.contains(query, ignoreCase = true) }
            }

        // Apply title filter
        if (filter.selectedTitles.isNotEmpty()) {
            result = result.filter { it.title in filter.selectedTitles }
        }

        // Apply trainer filter
        if (filter.selectedTrainers.isNotEmpty()) {
            result = result.filter { it.trainer in filter.selectedTrainers }
        }

        // Apply hours filter
        if (filter.selectedHours.isNotEmpty()) {
            result = result.filter { it.hours in filter.selectedHours }
        }

        // Apply status filter
        if (filter.selectedStatuses.isNotEmpty()) {
            result = result.filter { it.statusName in filter.selectedStatuses }
        }

        return result
    }
}