package com.vurgun.skyfit.feature.bodyanalysis.screen

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.result.ImageCaptureResult
import com.kashif.imagesaverplugin.ImageSaverPlugin
import com.vurgun.skyfit.data.bodyanalysis.model.BackPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.FrontPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.LeftPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.PostureType
import com.vurgun.skyfit.data.bodyanalysis.model.RightPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.repository.PostureAnalysisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

sealed class PostureAnalysisResult {
    data class Front(val data: FrontPostureResponse) : PostureAnalysisResult()
    data class Back(val data: BackPostureResponse) : PostureAnalysisResult()
    data class Left(val data: LeftPostureResponse) : PostureAnalysisResult()
    data class Right(val data: RightPostureResponse) : PostureAnalysisResult()
}

data class PostureState(
    val type: PostureType,
    val byteArray: ByteArray? = null,
    val result: PostureAnalysisResult? = null
) {
    val completed get() = result != null
}

data class PostureAnalysisUIState(
    val mode: Mode = Mode.Info,
    val currentPosture: PostureType? = null,
    val lastCapturedImage: ImageBitmap? = null,
    val postureStates: List<PostureState> = PostureType.entries.map { PostureState(it) },
    val showGrid: Boolean = true,
    val showGuideOverlay: Boolean = true,
    val isCaptureLoading: Boolean = false
) {
    enum class Mode {
        Info,
        Options,
        Camera,
        Scanning,
        Result
    }
}

class PostureAnalysisViewModel(
    private val postureAnalysisRepository: PostureAnalysisRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostureAnalysisUIState())
    val uiState: StateFlow<PostureAnalysisUIState> = _uiState

    fun toggleInfo() {
        _uiState.update {
            val infoMode = when (it.mode) {
                PostureAnalysisUIState.Mode.Info -> PostureAnalysisUIState.Mode.Options
                else -> PostureAnalysisUIState.Mode.Info
            }
            it.copy(mode = infoMode)
        }
    }

    fun selectPosture(type: PostureType) {
        _uiState.update {
            it.copy(
                currentPosture = type,
                mode = PostureAnalysisUIState.Mode.Camera
            )
        }
    }

    fun resetAll() {
        _uiState.update {
            it.copy(
                postureStates = PostureType.entries.map { PostureState(it) },
                mode = PostureAnalysisUIState.Mode.Options,
                currentPosture = null
            )
        }
    }

    fun toggleGrid() = _uiState.update { it.copy(showGrid = !it.showGrid) }

    fun toggleGuideOverlay() = _uiState.update { it.copy(showGuideOverlay = !it.showGuideOverlay) }

    @OptIn(ExperimentalResourceApi::class, ExperimentalUuidApi::class)
    fun handleImageCapture(
        cameraController: CameraController,
        imageSaverPlugin: ImageSaverPlugin
    ) {
        viewModelScope.launch {
            val postureType = uiState.value.currentPosture ?: return@launch

            _uiState.update { it.copy(isCaptureLoading = true) }

            when (val result = cameraController.takePicture()) {
                is ImageCaptureResult.Success -> {
                    val byteArray = result.byteArray
                    val bitmap = byteArray.decodeToImageBitmap()

                    proceedWithCapturedImage(byteArray, bitmap, postureType)

                    if (!imageSaverPlugin.config.isAutoSave) {
                        val customName = "posture_${Uuid.random().toHexString()}"
                        imageSaverPlugin.saveImage(byteArray, customName)
                    }
                }

                is ImageCaptureResult.Error -> {
                    println("Image Capture Error: ${result.exception.message}")
                    _uiState.update { it.copy(isCaptureLoading = false) }
                }
            }
        }
    }

    fun handleImagePicked(
        byteArray: ByteArray,
        bitmap: ImageBitmap
    ) {
        val postureType = uiState.value.currentPosture ?: return
        proceedWithCapturedImage(byteArray, bitmap, postureType)
    }

    private fun proceedWithCapturedImage(
        byteArray: ByteArray,
        bitmap: ImageBitmap,
        postureType: PostureType
    ) {
        _uiState.update {
            it.copy(
                isCaptureLoading = false,
                lastCapturedImage = bitmap,
                mode = PostureAnalysisUIState.Mode.Scanning
            )
        }

        analyzeImage(byteArray, postureType)
    }

    private fun analyzeImage(byteArray: ByteArray, type: PostureType) {
        viewModelScope.launch {
            try {
                //TODO: HERE WE NEED TO STORE RESULTS SOMWHERE UNTIL ALL ARE READY
                val result: PostureAnalysisResult = when (type) {
                    PostureType.Front -> PostureAnalysisResult.Front(postureAnalysisRepository.analyzeFront(byteArray))
                    PostureType.Back -> PostureAnalysisResult.Back(postureAnalysisRepository.analyzeBack(byteArray))
                    PostureType.Left -> PostureAnalysisResult.Left(postureAnalysisRepository.analyzeLeft(byteArray))
                    PostureType.Right -> PostureAnalysisResult.Right(postureAnalysisRepository.analyzeRight(byteArray))
                }

                _uiState.update { currentState ->
                    val updatedStates = currentState.postureStates.map {
                        if (it.type == type) it.copy(byteArray = byteArray, result = result)
                        else it
                    }

                    val allCompleted = updatedStates.all { it.completed }
                    val next = updatedStates.firstOrNull { !it.completed }?.type

                    currentState.copy(
                        postureStates = updatedStates,
                        currentPosture = next,
                        mode = if (allCompleted) PostureAnalysisUIState.Mode.Result else PostureAnalysisUIState.Mode.Options
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()

                _uiState.update {
                    it.copy(
                        isCaptureLoading = false,
                        lastCapturedImage = null,
                        mode = PostureAnalysisUIState.Mode.Options
                    )
                }
            }
        }
    }
}
