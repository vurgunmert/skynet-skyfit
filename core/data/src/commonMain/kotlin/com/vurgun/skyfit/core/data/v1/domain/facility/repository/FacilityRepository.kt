package com.vurgun.skyfit.core.data.v1.domain.facility.repository

import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityLessonPackageDTO
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCreationInfo
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonUpdateInfo
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

interface FacilityRepository {

    //Profile
    suspend fun getFacilityProfile(facilityId: Int): Result<FacilityProfile>
    suspend fun getFacilityTrainerProfiles(facilityId: Int): Result<List<FacilityTrainerProfile>>
    suspend fun updateFacilityProfile(
        gymId: Int,
        backgroundImageBytes: ByteArray?,
        name: String,
        address: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit>

    //Member
    suspend fun addMemberToFacility(userId: Int, gymId: Int): Result<Unit>
    suspend fun getFacilityMembers(gymId: Int): Result<List<Member>>
    suspend fun deleteFacilityMember(gymId: Int, userId: Int): Result<Unit>
    suspend fun getPlatformMembers(gymId: Int): Result<List<Member>>

    //Trainer
    suspend fun addFacilityTrainer(gymId: Int, userId: Int): Result<Unit>
    suspend fun getFacilityTrainers(gymId: Int): Result<List<TrainerPreview>>
    suspend fun deleteFacilityTrainer(gymId: Int, userId: Int): Result<Unit>
    suspend fun getPlatformTrainers(gymId: Int): Result<List<TrainerPreview>>

    //Package
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

    //Lesson
    suspend fun createLesson(info: LessonCreationInfo): Result<Unit>
    suspend fun updateLesson(info: LessonUpdateInfo): Result<Unit>
    suspend fun activateLesson(lessonId: Int): Result<Unit>
    suspend fun deactivateLesson(lessonId: Int): Result<Unit>
    suspend fun deleteLesson(lessonId: Int): Result<Unit>
    suspend fun getUpcomingLessonsByFacility(gymId: Int, limit: Int = 3): Result<List<Lesson>>
    suspend fun getAllLessonsByFacility(gymId: Int, startDate: String, endDate: String?): Result<List<Lesson>>
    suspend fun getAllLessonsByFacility(gymId: Int, startDate: LocalDate, endDate: LocalDate?): Result<List<Lesson>>
    suspend fun getActiveLessonsByFacility(
        gymId: Int,
        startDate: LocalDate,
        endDate: LocalDate? = null
    ): Result<List<Lesson>>

    suspend fun getActiveLessonsByFacility(gymId: Int, startDate: String, endDate: String? = null): Result<List<Lesson>>

    suspend fun addLessonCategory(name: String, gymId: Int): Result<Unit>
    suspend fun updateLessonCategory(categoryId: Int, categoryName: String, gymId: Int): Result<Unit>
    suspend fun deleteLessonCategory(categoryId: Int, gymId: Int): Result<Unit>
    suspend fun getLessonCategories(gymId: Int): Result<List<LessonCategory>>
    suspend fun deleteMemberPackage(userId: Int, packageId: Int): Result<Unit>
    suspend fun updateMemberPackage(userId: Int, gymId: Int, packageId: Int): Result<Unit>
    suspend fun addPackageToMember(
        userId: Int,
        packageId: Int,
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate = LocalDate.now().plus(1, DateTimeUnit.YEAR),
        lessonCount: Int? = null
    ): Result<Unit>
}