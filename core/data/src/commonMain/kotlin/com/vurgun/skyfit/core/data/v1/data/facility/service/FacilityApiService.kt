package com.vurgun.skyfit.core.data.v1.data.facility.service

import com.vurgun.skyfit.core.data.v1.data.facility.model.*
import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.FacilityTrainerProfileDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.GetFacilityTrainerProfilesRequestDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.TrainerDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.request.url
import io.ktor.http.*

class FacilityApiService(private val apiClient: ApiClient) {

    private object Endpoints {
        const val GET_FACILITY_PROFILE = "profile/get/gym"
        const val GET_FACILITY_TRAINER_PROFILES = "profile/gym/trainers"
        const val UPDATE_FACILITY_PROFILE = "profile/update/gym"
        const val DELETE_PROFILE_TAG = "profile/delete/tag"

        const val ADD_GYM_USER = "add/gym/user"
        const val GET_GYM_MEMBERS = "get/gym/members"
        const val GET_ALL_PLATFORM_MEMBERS = "get/all/platform/members"
        const val DELETE_GYM_MEMBER = "delete/gym/member"

        const val ADD_GYM_TRAINER = "add/gym/trainer"
        const val GET_GYM_TRAINERS = "get/gym/trainers"
        const val GET_ALL_PLATFORM_TRAINERS = "get/all/platform/trainers"
        const val DELETE_GYM_TRAINER = "delete/gym/trainer"

        const val CREATE_GYM_PACKAGE = "create/gym/package"
        const val UPDATE_GYM_PACKAGE = "update/gym/package"
        const val GET_GYM_PACKAGE = "get/gym/packages"
        const val DELETE_GYM_PACKAGE = "delete/gym/package"


        const val CREATE_LESSON = "create/lesson"
        const val UPDATE_LESSON = "update/lesson"
        const val ACTIVATE_LESSON = "activate/lesson"
        const val DEACTIVATE_LESSON = "deactivate/lesson"
        const val DELETE_LESSON = "delete/lesson"
        const val GET_ACTIVE_LESSONS_BY_GYM = "get/lessons/gym"
        const val GET_ALL_LESSONS_BY_GYM = "get/all/lessons/gym"
        const val GET_UPCOMING_LESSONS_BY_GYM = "get/close/lessons/gym"
    }

    //region Profile
    suspend fun getFacilityProfile(request: GetFacilityProfileRequest, token: String): ApiResult<FacilityProfileDTO> {
        return apiClient.safeApiCall<FacilityProfileDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_FACILITY_PROFILE)
            setBody(request)
        }
    }

    suspend fun getFacilityTrainerProfiles(
        request: GetFacilityTrainerProfilesRequestDTO,
        token: String
    ): ApiResult<List<FacilityTrainerProfileDTO>> {
        return apiClient.safeApiCall<List<FacilityTrainerProfileDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_FACILITY_TRAINER_PROFILES)
            setBody(request)
        }
    }

    suspend fun updateFacilityProfile(request: UpdateFacilityProfileRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_FACILITY_PROFILE)
            setBody(buildFacilityProfileFormData(request))
        }
    }

    suspend fun deleteProfileTag(request: DeleteProfileTagRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.DELETE_PROFILE_TAG)
            setBody(request)
        }
    }

    private fun buildFacilityProfileFormData(request: UpdateFacilityProfileRequest): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append("gymName", request.name)
                append("gymAdress", request.address)
                append("bio", request.bio)
                append("profileTags", request.profileTags.toString())

                request.backgroundImage?.let { bytes ->
                    append(
                        "backgroundImage", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.gymId}-background-image.png")
                        )
                    )
                }
            }
        )
    }
    //endregion Profile

    //region Member
    suspend fun addGymMember(request: AddGymMemberRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.ADD_GYM_USER)
            setBody(request)
        }
    }

    suspend fun getGymMembers(request: GetGymMembersRequest, token: String): ApiResult<List<FacilityMemberDTO>> {
        return apiClient.safeApiCall<List<FacilityMemberDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_MEMBERS)
            setBody(request)
        }
    }

    suspend fun getPlatformMembers(
        request: GetPlatformMembersRequest,
        token: String
    ): ApiResult<List<FacilityMemberDTO>> {
        return apiClient.safeApiCall<List<FacilityMemberDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_PLATFORM_MEMBERS)
            setBody(request)
        }
    }

    suspend fun deleteGymMember(request: DeleteGymMemberRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_MEMBER)
            setBody(request)
        }
    }
    //endregion Member

    //region Trainer
    suspend fun addGymTrainer(request: AddGymTrainerRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.ADD_GYM_TRAINER)
            setBody(request)
        }
    }

    suspend fun getGymTrainers(request: GetGymTrainersRequest, token: String): ApiResult<List<TrainerDTO>> {
        return apiClient.safeApiCall<List<TrainerDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_TRAINERS)
            setBody(request)
        }
    }

    suspend fun getPlatformTrainers(request: GetPlatformTrainersRequest, token: String): ApiResult<List<TrainerDTO>> {
        return apiClient.safeApiCall<List<TrainerDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_PLATFORM_TRAINERS)
            setBody(request)
        }
    }

    suspend fun deleteGymTrainer(request: DeleteGymTrainerRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_TRAINER)
            setBody(request)
        }
    }
    //endregion Trainer

    //region Lesson Package
    suspend fun createFacilityLessonPackage(
        request: CreateFacilityLessonPackageRequestDTO,
        token: String
    ): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.CREATE_GYM_PACKAGE)
            setBody(request)
        }
    }

    suspend fun updateFacilityLessonPackage(
        request: UpdateFacilityLessonPackageRequestDTO,
        token: String
    ): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_GYM_PACKAGE)
            setBody(request)
        }
    }

    suspend fun getFacilityLessonPackages(
        request: GetFacilityLessonPackagesRequestDTO,
        token: String
    ): ApiResult<List<FacilityLessonPackageDTO>> {
        return apiClient.safeApiCall<List<FacilityLessonPackageDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_PACKAGE)
            setBody(request)
        }
    }

    suspend fun deleteFacilityLessonPackage(
        request: DeleteFacilityLessonPackagesRequestDTO,
        token: String
    ): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Companion.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_PACKAGE)
            setBody(request)
        }
    }
    //endregion Lesson Package

    //region Lesson

    internal suspend fun createLesson(request: CreateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CREATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun updateLesson(request: UpdateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun deactivateLesson(request: DeactivateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.DEACTIVATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun activateLesson(request: ActivateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.ACTIVATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun deleteLesson(request: DeleteLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun getAllLessonsByFacility(
        request: GetFacilityLessonsRequestDTO,
        token: String
    ): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_LESSONS_BY_GYM)
            setBody(request)
        }
    }

    internal suspend fun getActiveLessonsByFacility(
        request: GetFacilityLessonsRequestDTO,
        token: String
    ): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ACTIVE_LESSONS_BY_GYM)
            setBody(request)
        }
    }

    internal suspend fun getUpcomingLessonsByFacility(
        request: GetUpcomingFacilityLessonsRequestDTO,
        token: String
    ): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_UPCOMING_LESSONS_BY_GYM)
            setBody(request)
        }
    }
    //endregion Lesson
}