package com.vurgun.skyfit.core.data.schedule.data.repository

import com.vurgun.skyfit.core.data.schedule.domain.model.LessonParticipant
import com.vurgun.skyfit.core.data.persona.data.mapper.ProfileMapper.toDomainLessonParticipants
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import com.vurgun.skyfit.core.data.schedule.data.service.CourseApiService
import com.vurgun.skyfit.core.data.schedule.domain.model.Appointment
import com.vurgun.skyfit.core.data.schedule.domain.model.AppointmentDetail
import com.vurgun.skyfit.core.data.schedule.domain.model.CreateAppointmentResponse
import com.vurgun.skyfit.core.data.schedule.domain.model.Lesson
import com.vurgun.skyfit.core.data.schedule.domain.model.LessonCreationInfo
import com.vurgun.skyfit.core.data.schedule.domain.model.LessonUpdateInfo
import com.vurgun.skyfit.core.data.schedule.domain.model.ScheduledLessonDetail
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.schedule.data.mapper.toAppointmentDetailDomain
import com.vurgun.skyfit.core.data.schedule.data.mapper.toCreateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.mapper.toLessonDomain
import com.vurgun.skyfit.core.data.schedule.data.mapper.toLessonDomainList
import com.vurgun.skyfit.core.data.schedule.data.mapper.toScheduledLessonDetail
import com.vurgun.skyfit.core.data.schedule.data.mapper.toUpdateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.ActivateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.CancelUserAppointmentRequest
import com.vurgun.skyfit.core.data.schedule.data.model.CreateUserAppointmentRequest
import com.vurgun.skyfit.core.data.schedule.data.model.DeactivateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.DeleteLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.EvaluateParticipantsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetAppointmentDetailRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetFacilityLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetLessonParticipantsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetScheduledLessonDetailRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetTrainerLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUpcomingFacilityLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUpcomingTrainerLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUpcomingUserAppointmentsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUserAppointmentsRequest
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class CourseRepositoryImpl(
    private val dispatchers: DispatcherProvider,
    private val apiService: CourseApiService,
    private val tokenManager: TokenManager
) : CourseRepository {

    override suspend fun getActiveLessonsByFacility(gymId: Int, startDate: LocalDate, endDate: LocalDate?): Result<List<Lesson>> =
        getActiveLessonsByFacility(gymId, startDate.formatToServerDate(), endDate?.formatToServerDate())

    override suspend fun getActiveLessonsByFacility(gymId: Int, startDate: String, endDate: String?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetFacilityLessonsRequest(gymId, startDate, endDate)
                when (val result = apiService.getActiveLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getAllLessonsByFacility(gymId: Int, startDate: LocalDate, endDate: LocalDate?): Result<List<Lesson>> =
        getAllLessonsByFacility(gymId, startDate.formatToServerDate(), endDate?.formatToServerDate())

    override suspend fun getAllLessonsByFacility(gymId: Int, startDate: String, endDate: String?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetFacilityLessonsRequest(gymId, startDate, endDate)
                when (val result = apiService.getAllLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getUpcomingLessonsByFacility(gymId: Int, limit: Int): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetUpcomingFacilityLessonsRequest(gymId, limit)
                when (val result = apiService.getUpcomingLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getLessonsByTrainer(trainerId: Int, startDate: LocalDate, endDate: LocalDate?): Result<List<Lesson>> =
        getLessonsByTrainer(trainerId, startDate.formatToServerDate(), endDate?.formatToServerDate())

    override suspend fun getLessonsByTrainer(trainerId: Int, startDate: String, endDate: String?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetTrainerLessonsRequest(trainerId, startDate, endDate)
                when (val result = apiService.getLessonsByTrainer(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getUpcomingLessonsByTrainer(trainerId: Int, limit: Int): Result<List<Lesson>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUpcomingTrainerLessonsRequest(trainerId, limit)
        apiService.getUpcomingLessonsByTrainer(request, token).mapOrThrow { it.toLessonDomainList() }
    }

    override suspend fun createLesson(info: LessonCreationInfo): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = info.toCreateLessonRequest()
        apiService.createLesson(request, token).mapOrThrow { }
    }

    override suspend fun getScheduledLessonDetail(lessonId: Int): Result<ScheduledLessonDetail> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetScheduledLessonDetailRequest(lessonId)
        apiService.getScheduledLessonDetail(request, token).mapOrThrow { it.toScheduledLessonDetail() }
    }

    override suspend fun updateLesson(info: LessonUpdateInfo): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = info.toUpdateLessonRequest()
        apiService.updateLesson(request, token).mapOrThrow { }
    }

    override suspend fun activateLesson(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = ActivateLessonRequest(lessonId)
        apiService.activateLesson(request, token).mapOrThrow { }
    }

    override suspend fun deactivateLesson(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeactivateLessonRequest(lessonId)
        apiService.deactivateLesson(request, token).mapOrThrow { }
    }

    override suspend fun deleteLesson(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteLessonRequest(lessonId)
        apiService.deleteLesson(request, token).mapOrThrow { }
    }

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

    override suspend fun getUpcomingAppointmentsByUser(nmId: Int, limit: Int): Result<List<Appointment>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUpcomingUserAppointmentsRequest(nmId, limit)
        apiService.getUpcomingAppointmentsByUser(request, token).mapOrThrow { it.toLessonDomain() }
    }

    override suspend fun bookAppointment(lessonId: Int): Result<CreateAppointmentResponse> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CreateUserAppointmentRequest(lessonId)
        apiService.createUserAppointment(request, token).mapOrThrow { CreateAppointmentResponse(it.lpId) }
    }

    override suspend fun cancelAppointment(lessonId: Int, lpId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CancelUserAppointmentRequest(lessonId, lpId)
        apiService.cancelUserAppointment(request, token).mapOrThrow { }
    }

    override suspend fun getLessonParticipants(lessonId: Int): Result<List<LessonParticipant>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetLessonParticipantsRequest(lessonId)
        apiService.getLessonParticipants(request, token).mapOrThrow { it.toDomainLessonParticipants() }
    }

    override suspend fun evaluateParticipants(lessonId: Int, participants: List<LessonParticipant>): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = EvaluateParticipantsRequest(
            lessonId = lessonId,
            participants = participants.map { EvaluateParticipantsRequest.EvaluatedParticipant(it.lpId, it.trainerEvaluation) }
        )
        apiService.evaluateParticipants(request, token).mapOrThrow { }
    }
}