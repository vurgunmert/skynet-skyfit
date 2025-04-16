package com.vurgun.skyfit.data.courses.domain.repository

import com.vurgun.skyfit.data.courses.domain.model.Lesson

interface CourseRepository {
    suspend fun getLessons(gymId: Int, startDate: String, endDate: String? = null): Result<List<Lesson>>
}
