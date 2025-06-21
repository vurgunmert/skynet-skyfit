package com.vurgun.skyfit.core.data.v1.data.facility.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toCreateLessonRequest
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toDomainFacilityProfile
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toFacilityTrainerProfiles
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toMemberDomainList
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toUpdateLessonRequest
import com.vurgun.skyfit.core.data.v1.data.facility.model.*
import com.vurgun.skyfit.core.data.v1.data.facility.service.FacilityApiService
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonDataMapper.toDomainCategories
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonDataMapper.toLessonDomainList
import com.vurgun.skyfit.core.data.v1.data.lesson.model.AddLessonCategoryRequestDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.DeleteLessonCategoryRequestDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.GetLessonCategoriesRequestDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.UpdateLessonCategoryRequestDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.mapper.TrainerDataMapper.toTrainerDomainList
import com.vurgun.skyfit.core.data.v1.data.trainer.model.GetFacilityTrainerProfilesRequestDTO
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCreationInfo
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonUpdateInfo
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class FacilityRepositoryImpl(
    private val apiService: FacilityApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : FacilityRepository {

    //region Profile
    override suspend fun getFacilityProfile(facilityId: Int): Result<FacilityProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetFacilityProfileRequest(facilityId)
        apiService.getFacilityProfile(request, token).mapOrThrow { it.toDomainFacilityProfile() }
    }

    override suspend fun getFacilityTrainerProfiles(facilityId: Int): Result<List<FacilityTrainerProfile>> =
        ioResult(dispatchers) {
            val token = tokenManager.getTokenOrThrow()
            val request = GetFacilityTrainerProfilesRequestDTO(facilityId)
            apiService.getFacilityTrainerProfiles(request, token).mapOrThrow { it.toFacilityTrainerProfiles() }
        }

    override suspend fun updateFacilityProfile(
        gymId: Int,
        backgroundImageBytes: ByteArray?,
        name: String,
        address: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateFacilityProfileRequest(
            gymId = gymId,
            backgroundImage = backgroundImageBytes,
            name = name,
            bio = bio,
            address = address,
            profileTags = profileTags
        )
        apiService.updateFacilityProfile(request, token).mapOrThrow { }
    }
    //endregion Profile

    override suspend fun deleteMemberPackage(userId: Int, packageId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteMemberPackage(userId, packageId)
        apiService.deleteMemberPackage(request, token).mapOrThrow { }
    }
    override suspend fun addPackageToMember(
        userId: Int,
        packageId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
        lessonCount: Int?
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = AddPackageToMember(userId, packageId, startDate.toString(), endDate.toString(), lessonCount)
        apiService.addPackageToMember(request, token).mapOrThrow { }
    }

    override suspend fun updateMemberPackage(userId: Int, gymId: Int, packageId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateMemberPackage(userId, gymId,packageId)
        apiService.updateMemberPackage(request, token).mapOrThrow { }
    }

    override suspend fun addMemberToFacility(userId: Int, gymId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = AddGymMemberRequest(gymId, userId)
        apiService.addGymMember(request, token).mapOrThrow { }
    }

    override suspend fun getFacilityMembers(gymId: Int): Result<List<Member>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetGymMembersRequest(gymId)
        apiService.getGymMembers(request, token).mapOrThrow { it.toMemberDomainList() }
    }

    override suspend fun deleteFacilityMember(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteGymMemberRequest(gymId, userId)
        apiService.deleteGymMember(request, token).mapOrThrow { }
    }

    override suspend fun getPlatformMembers(gymId: Int): Result<List<Member>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetPlatformMembersRequest(gymId)
        apiService.getPlatformMembers(request, token).mapOrThrow { it.toMemberDomainList() }
    }

    override suspend fun addFacilityTrainer(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = AddGymTrainerRequest(gymId, userId)
        apiService.addGymTrainer(request, token).mapOrThrow { }
    }

    override suspend fun getFacilityTrainers(gymId: Int): Result<List<TrainerPreview>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetGymTrainersRequest(gymId)
        apiService.getGymTrainers(request, token).mapOrThrow { it.toTrainerDomainList() }
    }

    override suspend fun deleteFacilityTrainer(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteGymTrainerRequest(gymId, userId)
        apiService.deleteGymTrainer(request, token).mapOrThrow { }
    }

    override suspend fun getPlatformTrainers(gymId: Int): Result<List<TrainerPreview>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetPlatformTrainersRequest(gymId)
        apiService.getPlatformTrainers(request, token).mapOrThrow { it.toTrainerDomainList() }
    }

    override suspend fun createLessonPackage(
        gymId: Int,
        title: String,
        contentIds: List<Int>,
        description: String,
        lessonCount: Int,
        duration: Int,
        price: Float
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = CreateFacilityLessonPackageRequestDTO(
            gymId = gymId,
            title = title,
            contents = contentIds,
            description = description,
            lessonCount = lessonCount,
            duration = duration,
            price = price
        )
        apiService.createFacilityLessonPackage(request, token).mapOrThrow { }
    }

    override suspend fun updateLessonPackage(
        packageId: Int,
        gymId: Int,
        title: String,
        contentIds: List<Int>,
        description: String,
        lessonCount: Int,
        duration: Int,
        price: Float
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateFacilityLessonPackageRequestDTO(
            packageId = packageId,
            title = title,
            contents = contentIds,
            description = description,
            lessonCount = lessonCount,
            duration = duration,
            price = price
        )
        apiService.updateFacilityLessonPackage(request, token).mapOrThrow { }
    }

    override suspend fun getFacilityLessonPackages(
        gymId: Int
    ): Result<List<FacilityLessonPackageDTO>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetFacilityLessonPackagesRequestDTO(gymId = gymId)
        apiService.getFacilityLessonPackages(request, token).mapOrThrow { it }
    }

    override suspend fun deleteFacilityLessonPackage(
        packageId: Int
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteFacilityLessonPackagesRequestDTO(packageId)
        apiService.deleteFacilityLessonPackage(request, token).mapOrThrow { }
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

    override suspend fun addLessonCategory(name: String, gymId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = AddLessonCategoryRequestDTO(categoryName = name, gymId = gymId)
        apiService.addFacilityLessonCategory(request, token).mapOrThrow { }
    }

    override suspend fun updateLessonCategory(categoryId: Int, categoryName: String, gymId: Int): Result<Unit> =
        ioResult(dispatchers) {
            val token = tokenManager.getTokenOrThrow()
            val request = UpdateLessonCategoryRequestDTO(categoryId, categoryName, gymId)
            apiService.updateFacilityLessonCategory(request, token).mapOrThrow { }
        }

    override suspend fun deleteLessonCategory(categoryId: Int, gymId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteLessonCategoryRequestDTO(categoryId, gymId)
        apiService.deleteFacilityLessonCategory(request, token).mapOrThrow { }
    }

    override suspend fun getLessonCategories(gymId: Int): Result<List<LessonCategory>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetLessonCategoriesRequestDTO(gymId)
        apiService.getFacilityLessonCategories(request, token).mapOrThrow { it.toDomainCategories() }
    }

    override suspend fun getUpcomingLessonsByFacility(gymId: Int, limit: Int): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetUpcomingFacilityLessonsRequestDTO(gymId, limit)
                when (val result = apiService.getUpcomingLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getActiveLessonsByFacility(
        gymId: Int,
        startDate: LocalDate,
        endDate: LocalDate?
    ): Result<List<Lesson>> =
        getActiveLessonsByFacility(gymId, startDate.formatToServerDate(), endDate?.formatToServerDate())

    override suspend fun getActiveLessonsByFacility(
        gymId: Int,
        startDate: String,
        endDate: String?
    ): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetFacilityLessonsRequestDTO(gymId, startDate, endDate)
                when (val result = apiService.getActiveLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }

    override suspend fun getAllLessonsByFacility(
        gymId: Int,
        startDate: LocalDate,
        endDate: LocalDate?
    ): Result<List<Lesson>> =
        getAllLessonsByFacility(gymId, startDate.formatToServerDate(), endDate?.formatToServerDate())

    override suspend fun getAllLessonsByFacility(
        gymId: Int,
        startDate: String,
        endDate: String?
    ): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = tokenManager.getTokenOrThrow()
                val request = GetFacilityLessonsRequestDTO(gymId, startDate, endDate)
                when (val result = apiService.getAllLessonsByFacility(request, token)) {
                    is ApiResult.Success -> result.data.toLessonDomainList()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }
}