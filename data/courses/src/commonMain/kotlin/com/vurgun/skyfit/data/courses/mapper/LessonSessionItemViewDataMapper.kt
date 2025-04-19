package com.vurgun.skyfit.data.courses.mapper

import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData

class LessonSessionItemViewDataMapper {
    fun map(
        lesson: Lesson,
        location: String? = null
    ): LessonSessionItemViewData {
        return LessonSessionItemViewData(
            iconId = lesson.iconId,
            title = lesson.title,
            date = lesson.startDate.toString(),
            hours = "${lesson.startTime} - ${lesson.endTime}",
            trainer = lesson.trainerFullName,
            facility = lesson.facilityName,
            location = "@${location ?: lesson.facilityName}",
            note = lesson.trainerNote,
            capacityRatio = lesson.capacityRatio,
            isActive = lesson.status == 1,
            lessonId = lesson.lessonId
        )
    }
}
