package com.vurgun.skyfit.core.data.persona.data.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import com.vurgun.skyfit.core.data.persona.domain.model.Member
import com.vurgun.skyfit.core.data.persona.domain.model.Trainer
import com.vurgun.skyfit.core.data.persona.domain.repository.MemberRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.TrainerRepository
import com.vurgun.skyfit.core.data.persona.data.mapper.toMemberDomainList
import com.vurgun.skyfit.core.data.persona.data.mapper.toTrainerDomainList
import com.vurgun.skyfit.core.data.persona.data.model.AddGymMemberRequest
import com.vurgun.skyfit.core.data.persona.data.model.AddGymTrainerRequest
import com.vurgun.skyfit.core.data.persona.data.model.CreateFacilityLessonPackageRequestDTO
import com.vurgun.skyfit.core.data.persona.data.model.DeleteFacilityLessonPackagesRequestDTO
import com.vurgun.skyfit.core.data.persona.data.model.DeleteGymMemberRequest
import com.vurgun.skyfit.core.data.persona.data.model.DeleteGymTrainerRequest
import com.vurgun.skyfit.core.data.persona.data.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.persona.data.model.GetFacilityLessonPackagesRequestDTO
import com.vurgun.skyfit.core.data.persona.data.model.GetGymMembersRequest
import com.vurgun.skyfit.core.data.persona.data.model.GetGymTrainersRequest
import com.vurgun.skyfit.core.data.persona.data.model.GetPlatformMembersRequest
import com.vurgun.skyfit.core.data.persona.data.model.GetPlatformTrainersRequest
import com.vurgun.skyfit.core.data.persona.data.model.UpdateFacilityLessonPackageRequestDTO
import com.vurgun.skyfit.core.data.persona.data.service.SettingsApiService
import com.vurgun.skyfit.core.data.persona.domain.repository.FacilityRepository

class SettingsRepositoryImpl(
    private val apiService: SettingsApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : MemberRepository, TrainerRepository, FacilityRepository {

    override suspend fun addMemberToFacility(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
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

    override suspend fun getFacilityTrainers(gymId: Int): Result<List<Trainer>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetGymTrainersRequest(gymId)
        apiService.getGymTrainers(request, token).mapOrThrow { it.toTrainerDomainList() }
    }

    override suspend fun deleteFacilityTrainer(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteGymTrainerRequest(gymId, userId)
        apiService.deleteGymTrainer(request, token).mapOrThrow { }
    }

    override suspend fun getPlatformTrainers(gymId: Int): Result<List<Trainer>> = ioResult(dispatchers) {
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


}