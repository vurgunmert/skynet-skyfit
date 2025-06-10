package com.vurgun.skyfit.core.data.v1.data.user.repository

import com.vurgun.skyfit.core.data.v1.domain.user.model.Appointment
import com.vurgun.skyfit.core.data.v1.domain.user.model.AppointmentDetail
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.user.mapper.UserDataMapper.toAppointmentDetailDomain
import com.vurgun.skyfit.core.data.v1.data.user.mapper.UserDataMapper.toDomainCalendarEvents
import com.vurgun.skyfit.core.data.v1.data.user.mapper.UserDataMapper.toDomainUserProfile
import com.vurgun.skyfit.core.data.v1.data.user.mapper.UserDataMapper.toDomainWorkoutEvents
import com.vurgun.skyfit.core.data.v1.data.user.mapper.UserDataMapper.toLessonDomain
import com.vurgun.skyfit.core.data.v1.data.user.model.*
import com.vurgun.skyfit.core.data.v1.data.user.service.UserApiService
import com.vurgun.skyfit.core.data.v1.domain.user.model.CalendarEvent
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutEvent
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class UserRepositoryImpl(
    private val apiService: UserApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : UserRepository {

    //region Profile
    override suspend fun getUserProfile(normalUserId: Int): Result<UserProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUserProfileRequestDTO(normalUserId)
        apiService.getUserProfile(request, token).mapOrThrow { it.toDomainUserProfile() }
    }

    override suspend fun updateUserProfile(
        normalUserId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        bodyTypeId: Int
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateUserProfileRequestDTO(
            userId = normalUserId,
            profilePhoto = profileImageBytes,
            backgroundImage = backgroundImageBytes,
            username = username,
            name = name,
            surname = surname,
            height = height,
            weight = weight,
            bodyTypeId = bodyTypeId
        )
        apiService.updateUserProfile(request, token).mapOrThrow { }
    }
    //endregion Profile

    //region Appointments
    override suspend fun getAppointmentsByUser(nmId: Int): Result<List<Appointment>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUserAppointmentsRequest(nmId)
        apiService.getAppointmentsByUser(request, token).mapOrThrow { it.toLessonDomain() }
    }

    override suspend fun getAppointmentDetail(lpId: Int): Result<AppointmentDetail> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetAppointmentDetailRequest(lpId)
        apiService.getAppointmentDetail(request, token).mapOrThrow { it.toAppointmentDetailDomain() }
    }

    override suspend fun getUpcomingAppointmentsByUser(nmId: Int, limit: Int): Result<List<Appointment>> =
        ioResult(dispatchers) {
            val token = tokenManager.getTokenOrThrow()
            val request = GetUpcomingUserAppointmentsRequest(nmId, limit)
            apiService.getUpcomingAppointmentsByUser(request, token).mapOrThrow { it.toLessonDomain() }
        }

    override suspend fun bookAppointment(lessonId: Int): Result<CreateAppointmentResponseDTO> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CreateUserAppointmentRequest(lessonId)
        apiService.createUserAppointment(request, token).mapOrThrow { CreateAppointmentResponseDTO(it.lpId) }
    }

    override suspend fun cancelAppointment(lessonId: Int, lpId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CancelUserAppointmentRequest(lessonId, lpId)
        apiService.cancelUserAppointment(request, token).mapOrThrow { }
    }
    //endregion Appointments

    //region Calendar
    override suspend fun addCalendarEvents(
        workoutId: Int?,
        eventName: String,
        startDate: String,
        endDate: String
    ): Result<Unit> = ioResult(dispatchers) {
        val calendarRequest = AddCalendarEventRequestDTO(workoutId, eventName, startDate, endDate)
        val token = tokenManager.getTokenOrThrow()
        apiService.addCalendarEvents(calendarRequest, token).mapOrThrow { }
    }

    override suspend fun getCalendarEvents(
        startDate: String?,
        endDate: String?
    ): Result<List<CalendarEvent>> = ioResult(dispatchers) {
        val calendarRequest = GetCalendarEventsRequestDTO(startDate, endDate)
        val token = tokenManager.getTokenOrThrow()
        apiService.getCalendarEvents(calendarRequest, token)
            .mapOrThrow { it.toDomainCalendarEvents() }
    }

    //TODO MOVE TO GLOBAL
    override suspend fun getWorkoutEvents(): Result<List<WorkoutEvent>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getWorkoutEvents(token).mapOrThrow { it.toDomainWorkoutEvents() }
    }
    //endregion Calendar
}