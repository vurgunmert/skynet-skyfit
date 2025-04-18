package com.vurgun.skyfit.data.settings.repository

import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.network.utils.ioResult
import com.vurgun.skyfit.data.network.utils.mapOrThrow
import com.vurgun.skyfit.data.settings.domain.model.Member
import com.vurgun.skyfit.data.settings.domain.model.Trainer
import com.vurgun.skyfit.data.settings.domain.repository.MemberRepository
import com.vurgun.skyfit.data.settings.domain.repository.TrainerRepository
import com.vurgun.skyfit.data.settings.mapper.toMemberDomainList
import com.vurgun.skyfit.data.settings.mapper.toTrainerDomainList
import com.vurgun.skyfit.data.settings.model.AddGymMemberRequest
import com.vurgun.skyfit.data.settings.model.AddGymTrainerRequest
import com.vurgun.skyfit.data.settings.model.DeleteGymMemberRequest
import com.vurgun.skyfit.data.settings.model.DeleteGymTrainerRequest
import com.vurgun.skyfit.data.settings.model.GetGymMembersRequest
import com.vurgun.skyfit.data.settings.model.GetGymTrainersRequest
import com.vurgun.skyfit.data.settings.model.GetPlatformMembersRequest
import com.vurgun.skyfit.data.settings.model.GetPlatformTrainersRequest
import com.vurgun.skyfit.data.user.repository.UserRepository

class SettingsRepositoryImpl(
    private val apiService: SettingsApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage,
) : MemberRepository, TrainerRepository {

    private suspend fun requireToken(): String {
        return storage.get(UserRepository.UserAuthToken) ?: throw MissingTokenException
    }

    override suspend fun addMemberToFacility(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = AddGymMemberRequest(gymId, userId)
        apiService.addGymMember(request, token).mapOrThrow { }
    }

    override suspend fun getFacilityMembers(gymId: Int): Result<List<Member>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetGymMembersRequest(gymId)
        apiService.getGymMembers(request, token).mapOrThrow { it.toMemberDomainList() }
    }

    override suspend fun deleteFacilityMember(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = DeleteGymMemberRequest(gymId, userId)
        apiService.deleteGymMember(request, token).mapOrThrow { }
    }

    override suspend fun getPlatformMembers(gymId: Int): Result<List<Member>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetPlatformMembersRequest(gymId)
        apiService.getPlatformMembers(request, token).mapOrThrow { it.toMemberDomainList() }
    }

    override suspend fun addFacilityTrainer(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = AddGymTrainerRequest(gymId, userId)
        apiService.addGymTrainer(request, token).mapOrThrow { }
    }

    override suspend fun getFacilityTrainers(gymId: Int): Result<List<Trainer>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetGymTrainersRequest(gymId)
        apiService.getGymTrainers(request, token).mapOrThrow { it.toTrainerDomainList() }
    }

    override suspend fun deleteFacilityTrainer(gymId: Int, userId: Int): Result<Unit> = ioResult(dispatchers) {
        val token = requireToken()
        val request = DeleteGymTrainerRequest(gymId, userId)
        apiService.deleteGymTrainer(request, token).mapOrThrow { }
    }

    override suspend fun getPlatformTrainers(gymId: Int): Result<List<Trainer>> = ioResult(dispatchers) {
        val token = requireToken()
        val request = GetPlatformTrainersRequest(gymId)
        apiService.getPlatformTrainers(request, token).mapOrThrow { it.toTrainerDomainList() }
    }

}