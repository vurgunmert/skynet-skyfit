package com.vurgun.skyfit.core.data.persona.data.service

import com.vurgun.skyfit.core.data.shared.data.model.EmptyDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
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
import com.vurgun.skyfit.core.data.persona.data.model.MemberDTO
import com.vurgun.skyfit.core.data.persona.data.model.TrainerDTO
import com.vurgun.skyfit.core.data.persona.data.model.UpdateFacilityLessonPackageRequestDTO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class SettingsApiService(private val apiClient: ApiClient) {

    private object Endpoints {
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
    }

    suspend fun addGymMember(request: AddGymMemberRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.ADD_GYM_USER)
            setBody(request)
        }
    }

    suspend fun getGymMembers(request: GetGymMembersRequest, token: String): ApiResult<List<MemberDTO>> {
        return apiClient.safeApiCall<List<MemberDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_MEMBERS)
            setBody(request)
        }
    }

    suspend fun getPlatformMembers(request: GetPlatformMembersRequest, token: String): ApiResult<List<MemberDTO>> {
        return apiClient.safeApiCall<List<MemberDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_PLATFORM_MEMBERS)
            setBody(request)
        }
    }

    suspend fun deleteGymMember(request: DeleteGymMemberRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_MEMBER)
            setBody(request)
        }
    }

    suspend fun addGymTrainer(request: AddGymTrainerRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.ADD_GYM_TRAINER)
            setBody(request)
        }
    }

    suspend fun getGymTrainers(request: GetGymTrainersRequest, token: String): ApiResult<List<TrainerDTO>> {
        return apiClient.safeApiCall<List<TrainerDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_TRAINERS)
            setBody(request)
        }
    }

    suspend fun getPlatformTrainers(request: GetPlatformTrainersRequest, token: String): ApiResult<List<TrainerDTO>> {
        return apiClient.safeApiCall<List<TrainerDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ALL_PLATFORM_TRAINERS)
            setBody(request)
        }
    }

    suspend fun deleteGymTrainer(request: DeleteGymTrainerRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_TRAINER)
            setBody(request)
        }
    }

    suspend fun createFacilityLessonPackage(request: CreateFacilityLessonPackageRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CREATE_GYM_PACKAGE)
            setBody(request)
        }
    }

    suspend fun updateFacilityLessonPackage(request: UpdateFacilityLessonPackageRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_GYM_PACKAGE)
            setBody(request)
        }
    }

    suspend fun getFacilityLessonPackages(request: GetFacilityLessonPackagesRequestDTO, token: String): ApiResult<List<FacilityLessonPackageDTO>> {
        return apiClient.safeApiCall<List<FacilityLessonPackageDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_GYM_PACKAGE)
            setBody(request)
        }
    }

    suspend fun deleteFacilityLessonPackage(request: DeleteFacilityLessonPackagesRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_GYM_PACKAGE)
            setBody(request)
        }
    }
}