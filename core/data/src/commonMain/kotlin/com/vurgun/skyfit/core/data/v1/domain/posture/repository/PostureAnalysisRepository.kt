package com.vurgun.skyfit.core.data.v1.domain.posture.repository

import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureTypeDTO
import com.vurgun.skyfit.core.data.v1.data.posture.service.PostureAnalysisApiService
import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureFinding
import com.vurgun.skyfit.core.data.v1.domain.posture.model.toPostureFindingList

class PostureAnalysisRepository(
    private val apiService: PostureAnalysisApiService
) {

    suspend fun getPostureFindingList(
        imageBytes: ByteArray,
        postureType: PostureTypeDTO
    ): List<PostureFinding> {
        val json = apiService.sendPosture(imageBytes, postureType.orientation)
        return json.toPostureFindingList()
    }
}
