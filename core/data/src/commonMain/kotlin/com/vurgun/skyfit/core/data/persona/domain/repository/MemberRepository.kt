package com.vurgun.skyfit.core.data.persona.domain.repository

import com.vurgun.skyfit.core.data.persona.data.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.persona.domain.model.Member
import com.vurgun.skyfit.core.data.persona.domain.model.Trainer

interface MemberRepository {
    suspend fun addMemberToFacility(gymId: Int, userId: Int): Result<Unit>
    suspend fun getFacilityMembers(gymId: Int): Result<List<Member>>
    suspend fun deleteFacilityMember(gymId: Int, userId: Int): Result<Unit>
    suspend fun getPlatformMembers(gymId: Int): Result<List<Member>>
}

interface TrainerRepository {
    suspend fun addFacilityTrainer(gymId: Int, userId: Int): Result<Unit>
    suspend fun getFacilityTrainers(gymId: Int): Result<List<Trainer>>
    suspend fun deleteFacilityTrainer(gymId: Int, userId: Int): Result<Unit>
    suspend fun getPlatformTrainers(gymId: Int): Result<List<Trainer>>
}

interface FacilityRepository {
    suspend fun createLessonPackage(
        gymId: Int,
        title: String,
        contentIds: List<Int>,
        description: String,
        lessonCount: Int,
        duration: Int,
        price: Float
    ): Result<Unit>

    suspend fun updateLessonPackage(
        packageId: Int,
        gymId: Int,
        title: String,
        contentIds: List<Int>,
        description: String,
        lessonCount: Int,
        duration: Int,
        price: Float
    ): Result<Unit>

    suspend fun getFacilityLessonPackages(gymId: Int): Result<List<FacilityLessonPackageDTO>>
    suspend fun deleteFacilityLessonPackage(packageId: Int): Result<Unit>
}

