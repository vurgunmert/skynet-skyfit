package com.vurgun.skyfit.core.data.v1.data.trainer.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonDataMapper.toLessonDomainList
import com.vurgun.skyfit.core.data.v1.data.trainer.TrainerApiService
import com.vurgun.skyfit.core.data.v1.data.trainer.mapper.TrainerDataMapper.toDomainTrainerProfile
import com.vurgun.skyfit.core.data.v1.data.trainer.model.*
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class TrainerRepositoryImpl(
    private val apiService: TrainerApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : TrainerRepository {


    //region Profile
    override suspend fun getTrainerProfile(trainerId: Int): Result<TrainerProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetTrainerProfileRequestDTO(trainerId)
        apiService.getTrainerProfile(request, token).mapOrThrow { it.toDomainTrainerProfile() }
    }

    override suspend fun updateTrainerProfile(
        trainerId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        firstName: String,
        lastName: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateTrainerProfileRequestDTO(
            trainerId = trainerId,
            profilePhoto = profileImageBytes,
            backgroundImage = backgroundImageBytes,
            username = username,
            name = firstName,
            surname = lastName,
            bio = bio,
            profileTags = profileTags
        )
        apiService.updateTrainerProfile(request, token).mapOrThrow { }
    }
    //endregion Profile

    //region Lesson
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

    override suspend fun evaluateParticipants(lessonId: Int, participants: List<LessonParticipant>): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = EvaluateParticipantsRequest(
            lessonId = lessonId,
            participants = participants.map { EvaluateParticipantsRequest.EvaluatedParticipant(it.lpId, it.trainerEvaluation) }
        )
        apiService.evaluateParticipants(request, token).mapOrThrow { }
    }
    //endregion Lesson
}