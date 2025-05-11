package com.vurgun.skyfit.core.data.schedule.data.mapper

import com.vurgun.skyfit.core.data.schedule.domain.model.Appointment
import com.vurgun.skyfit.core.data.schedule.domain.model.Lesson
import com.vurgun.skyfit.core.data.schedule.data.model.LessonSessionItemViewData

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

    fun map(
        appointment: Appointment
    ): LessonSessionItemViewData {
        return LessonSessionItemViewData(
            lessonId = appointment.lessonId,
            iconId = appointment.iconId,
            title = appointment.title,
            date = appointment.startDate.toString(),
            hours = "${appointment.startTime} - ${appointment.endTime}",
            trainer = appointment.trainerFullName,
            facility = appointment.facilityName,
            location = appointment.facilityName,
            note = appointment.trainerNote,
            capacityRatio = appointment.quotaInfo
        )
    }
}
