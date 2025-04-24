package com.vurgun.skyfit.data.courses.repository

import com.vurgun.skyfit.data.core.storage.TokenManager
import com.vurgun.skyfit.data.courses.CourseApiService
import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.model.LessonCreationInfo
import com.vurgun.skyfit.data.courses.domain.model.LessonUpdateInfo
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.toCreateLessonRequest
import com.vurgun.skyfit.data.courses.mapper.toLessonDomain
import com.vurgun.skyfit.data.courses.mapper.toLessonDomainList
import com.vurgun.skyfit.data.courses.mapper.toUpdateLessonRequest
import com.vurgun.skyfit.data.courses.model.ActivateLessonRequest
import com.vurgun.skyfit.data.courses.model.CancelUserAppointmentRequest
import com.vurgun.skyfit.data.courses.model.CreateUserAppointmentRequest
import com.vurgun.skyfit.data.courses.model.DeactivateLessonRequest
import com.vurgun.skyfit.data.courses.model.DeleteLessonRequest
import com.vurgun.skyfit.data.courses.model.GetFacilityLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetTrainerLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetUpcomingFacilityLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetUpcomingTrainerLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetUpcomingUserAppointmentsRequest
import com.vurgun.skyfit.data.courses.model.GetUserAppointmentsRequest
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.network.utils.ioResult
import com.vurgun.skyfit.data.network.utils.mapOrThrow
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class CourseRepositoryImpl(
    private val dispatchers: DispatcherProvider,
    private val apiService: CourseApiService,
    private val tokenManager: TokenManager
) : CourseRepository {

    override suspend fun getLessonsByFacility(gymId: Int, startDate: LocalDate, endDate: LocalDate?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            val startDateString = startDate.toString()
            val endDateString = endDate?.toString()
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetFacilityLessonsRequest(gymId, startDateString, endDateString)
                when (val result = apiService.getLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getLessonsByFacility(gymId: Int, startDate: String, endDate: String?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetFacilityLessonsRequest(gymId, startDate, endDate)
                when (val result = apiService.getLessonsByFacility(request, token)) {
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

    override suspend fun getUpcomingAppointmentsByUser(nmId: Int, limit: Int): Result<List<Appointment>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUpcomingUserAppointmentsRequest(nmId, limit)
        apiService.getUpcomingAppointmentsByUser(request, token).mapOrThrow { it.toLessonDomain() }
    }

    override suspend fun bookAppointment(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CreateUserAppointmentRequest(lessonId)
        apiService.createUserAppointment(request, token).mapOrThrow { }
    }

    override suspend fun cancelAppointment(lessonId: Int, lpId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CancelUserAppointmentRequest(lessonId, lpId)
        apiService.cancelUserAppointment(request, token).mapOrThrow { }
    }
}