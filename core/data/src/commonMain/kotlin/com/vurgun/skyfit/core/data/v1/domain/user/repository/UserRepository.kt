package com.vurgun.skyfit.core.data.v1.domain.user.repository

import com.vurgun.skyfit.core.data.v1.domain.user.model.Appointment
import com.vurgun.skyfit.core.data.v1.domain.user.model.AppointmentDetail
import com.vurgun.skyfit.core.data.v1.data.user.model.CreateAppointmentResponseDTO
import com.vurgun.skyfit.core.data.v1.domain.user.model.CalendarEvent
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutEvent

interface UserRepository {
    suspend fun getUserProfile(normalUserId: Int): Result<UserProfile>
    suspend fun updateUserProfile(
        normalUserId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        bodyTypeId: Int
    ): Result<Unit>

    suspend fun getAppointmentsByUser(nmId: Int): Result<List<Appointment>>
    suspend fun getAppointmentDetail(lpId: Int): Result<AppointmentDetail>
    suspend fun getUpcomingAppointmentsByUser(nmId: Int, limit: Int): Result<List<Appointment>>
    suspend fun bookAppointment(lessonId: Int): Result<CreateAppointmentResponseDTO>
    suspend fun cancelAppointment(lessonId: Int, lpId: Int): Result<Unit>

    suspend fun addCalendarEvents(workoutId: Int?, eventName: String, startDate: String, endDate: String): Result<Unit>
    suspend fun getCalendarEvents(startDate: String? = null, endDate: String? = null): Result<List<CalendarEvent>>
}