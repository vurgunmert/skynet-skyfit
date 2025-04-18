package com.vurgun.skyfit.data.courses.repository

import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.storage.Storage
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
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(
    storage: Storage,
    private val dispatchers: DispatcherProvider,
    private val apiService: CourseApiService
) : CourseRepository {

    private val userToken = storage.getAsFlow(UserRepository.UserAuthToken)

    private suspend fun requireToken(): String = userToken.firstOrNull() ?: throw MissingTokenException

    override suspend fun getLessonsByFacility(gymId: Int, startDate: String, endDate: String?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = requireToken()
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
                val token = requireToken()
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
                val token = requireToken()
                val request = GetTrainerLessonsRequest(trainerId, startDate, endDate)
                when (val result = apiService.getLessonsByTrainer(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getUpcomingLessonsByTrainer(trainerId: Int, limit: Int): Result<List<Lesson>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetUpcomingTrainerLessonsRequest(trainerId, limit)
        apiService.getUpcomingLessonsByTrainer(request, token).mapOrThrow { it.toLessonDomainList() }
    }

    override suspend fun createLesson(info: LessonCreationInfo): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = info.toCreateLessonRequest()
        apiService.createLesson(request, token).mapOrThrow { }
    }

    override suspend fun updateLesson(info: LessonUpdateInfo): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = info.toUpdateLessonRequest()
        apiService.updateLesson(request, token).mapOrThrow { }
    }

    override suspend fun deactivateLesson(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = DeactivateLessonRequest(lessonId)
        apiService.deactivateLesson(request, token).mapOrThrow { }
    }

    override suspend fun deleteLesson(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = DeleteLessonRequest(lessonId)
        apiService.deleteLesson(request, token).mapOrThrow { }
    }

    override suspend fun getAppointmentsByUser(userId: Int): Result<List<Appointment>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetUserAppointmentsRequest(userId)
        apiService.getAppointmentsByUser(request, token).mapOrThrow { it.toLessonDomain() }
    }

    override suspend fun getUpcomingAppointmentsByUser(userId: Int, limit: Int): Result<List<Appointment>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetUpcomingUserAppointmentsRequest(userId, limit)
        apiService.getUpcomingAppointmentsByUser(request, token).mapOrThrow { it.toLessonDomain() }
    }

    override suspend fun joinLesson(lessonId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = CreateUserAppointmentRequest(lessonId)
        apiService.createUserAppointment(request, token).mapOrThrow { }
    }
}