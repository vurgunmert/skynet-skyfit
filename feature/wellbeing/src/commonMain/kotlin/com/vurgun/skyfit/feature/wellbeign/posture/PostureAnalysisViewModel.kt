package com.vurgun.skyfit.feature.wellbeign.posture

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.kashif.cameraK.controller.CameraController
import com.kashif.cameraK.result.ImageCaptureResult
import com.kashif.imagesaverplugin.ImageSaverPlugin
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.model.PostureFinding
import com.vurgun.skyfit.core.data.model.PostureType
import com.vurgun.skyfit.core.data.repository.PostureAnalysisRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class PostureOptionState(
    val type: PostureType,
    val byteArray: ByteArray? = null,
    val bitmap: ImageBitmap? = null,
    val findings: List<PostureFinding>? = null,
    val error: String? = null
) {
    val isCaptured get() = bitmap != null
    val isReported get() = findings != null
    val isFailed get() = isCaptured && error != null
    val isCompleted get() = isCaptured && findings != null
    val isPending get() = isCaptured && findings == null && error == null
}

data class PostureAnalysisHeaderState(
    val gridGuideEnabled: Boolean = true,
    val bodyGuideEnabled: Boolean = true,
    val infoEnabled: Boolean = true
)

sealed class PostureAnalysisContentState {
    data object Instructions : PostureAnalysisContentState()
    data class PostureOptions(
        val states: List<PostureOptionState>
    ) : PostureAnalysisContentState()

    data class CameraPreview(
        val postureType: PostureType,
        val isExist: Boolean
    ) : PostureAnalysisContentState()

    data class ImageScanner(
        val image: ImageBitmap
    ) : PostureAnalysisContentState()

    data class Report(
        val optionStates: List<PostureOptionState>
    ) : PostureAnalysisContentState()
}

sealed class PostureAnalysisAction {
    data object NavigateToBack : PostureAnalysisAction()

    data object DismissReport : PostureAnalysisAction()
    data object ToggleInfo : PostureAnalysisAction()
    data object ToggleGrid : PostureAnalysisAction()
    data object ToggleBodyGuide : PostureAnalysisAction()

    data object Reset : PostureAnalysisAction()
    data object RetakeOption : PostureAnalysisAction()

    data class SelectOption(val type: PostureType) : PostureAnalysisAction()
    data class CaptureFromGallery(
        val byteArray: ByteArray,
        val bitmap: ImageBitmap
    ) : PostureAnalysisAction()

    data class CaptureFromCamera(
        val cameraController: CameraController,
        val imageSaverPlugin: ImageSaverPlugin
    ) : PostureAnalysisAction()
}

sealed class PostureAnalysisEffect {
    data object Exit : PostureAnalysisEffect()
    data object ShowExitDialog : PostureAnalysisEffect()
}

class PostureAnalysisViewModel(
    private val postureAnalysisRepository: PostureAnalysisRepository
) : ScreenModel {

    private val _effect = SingleSharedFlow<PostureAnalysisEffect>()
    val effect: SharedFlow<PostureAnalysisEffect> = _effect

    private val _headerState = MutableStateFlow(PostureAnalysisHeaderState())
    val headerState: StateFlow<PostureAnalysisHeaderState> = _headerState

    private val _contentState = MutableStateFlow<PostureAnalysisContentState>(PostureAnalysisContentState.Instructions)
    val contentState: StateFlow<PostureAnalysisContentState> = _contentState

    private val _postureStates = MutableStateFlow<Map<PostureType, PostureOptionState>>(
        mapOf(
            PostureType.Front to PostureOptionState(PostureType.Front),
            PostureType.Back to PostureOptionState(PostureType.Back),
            PostureType.Right to PostureOptionState(PostureType.Right),
            PostureType.Left to PostureOptionState(PostureType.Left)
        )
    )
    val postureStates: StateFlow<Map<PostureType, PostureOptionState>> = _postureStates

    fun onAction(action: PostureAnalysisAction) {
        when (action) {
            PostureAnalysisAction.NavigateToBack -> handleBack()
            PostureAnalysisAction.ToggleGrid -> toggleGridGuide()
            PostureAnalysisAction.ToggleBodyGuide -> toggleBodyGuide()
            PostureAnalysisAction.ToggleInfo -> toggleInstructions()
            PostureAnalysisAction.RetakeOption -> resetCurrent()
            PostureAnalysisAction.Reset -> resetAll()
            is PostureAnalysisAction.SelectOption -> selectPostureOption(action.type)
            is PostureAnalysisAction.CaptureFromCamera -> captureCameraPhoto(action.cameraController, action.imageSaverPlugin)
            is PostureAnalysisAction.CaptureFromGallery -> captureGalleryPhoto(action.byteArray, action.bitmap)
            PostureAnalysisAction.DismissReport -> handleBack()
        }
    }

    private fun handleBack() {
        when (_contentState.value) {
            is PostureAnalysisContentState.PostureOptions -> {
                if (postureStates.value.values.any { it.isCaptured }) {
                    _effect.emitIn(screenModelScope, PostureAnalysisEffect.ShowExitDialog)
                } else {
                    _effect.emitIn(screenModelScope, PostureAnalysisEffect.Exit)
                }
            }

            is PostureAnalysisContentState.CameraPreview -> {
                showPostureOptions()
            }

            is PostureAnalysisContentState.Report -> {
                showPostureOptions()
            }

            else -> {
                _effect.emitIn(screenModelScope, PostureAnalysisEffect.Exit)
            }
        }
    }

    // UNUSED
    private fun resetCurrent() {
    }

    // UNUSED
    private fun resetAll() {
        _postureStates.value = mapOf(
            PostureType.Front to PostureOptionState(PostureType.Front),
            PostureType.Back to PostureOptionState(PostureType.Back),
            PostureType.Right to PostureOptionState(PostureType.Right),
            PostureType.Left to PostureOptionState(PostureType.Left),
        )
    }

    private fun toggleGridGuide() {
        _headerState.value = _headerState.value.copy(
            gridGuideEnabled = !_headerState.value.gridGuideEnabled
        )
    }

    private fun toggleBodyGuide() {
        _headerState.value = _headerState.value.copy(
            bodyGuideEnabled = !_headerState.value.bodyGuideEnabled
        )
    }

    private fun toggleInstructions() {
        val infoEnabled = !_headerState.value.infoEnabled
        _headerState.value = _headerState.value.copy(infoEnabled = infoEnabled)

        if (infoEnabled) {
            _contentState.value = PostureAnalysisContentState.Instructions
        } else {
            showPostureOptions()
        }
    }

    private fun showScanner() {
        val lastCapturedImage = _postureStates.value.values
            .lastOrNull { it.bitmap != null }
            ?.bitmap ?: return

        _contentState.value = PostureAnalysisContentState.ImageScanner(lastCapturedImage)
    }

    private fun showPostureOptions() {
        _contentState.value = PostureAnalysisContentState.PostureOptions(
            _postureStates.value.values.toList()
        )
    }

    private fun selectPostureOption(type: PostureType) {
        val currentState = _postureStates.value[type]
        val isCompleted = currentState?.isCompleted == true

        _contentState.value = PostureAnalysisContentState.CameraPreview(
            postureType = type,
            isExist = isCompleted
        )
    }

    private fun moveToNextPostureIfAny() {
        val nextType = _postureStates.value.entries
            .firstOrNull { !it.value.isCaptured }
            ?.key

        if (nextType != null) {
            selectPostureOption(nextType)
        } else {
            showScanner()
        }
    }

    private fun moveToReportIfReady() {
        val currentStates = _postureStates.value

        if (currentStates.values.all { it.isReported }) {
            _contentState.value = PostureAnalysisContentState.Report(currentStates.values.toList())
        }
    }

    private fun updatePostureState(type: PostureType, updated: PostureOptionState) {
        _postureStates.value = _postureStates.value.toMutableMap().apply {
            this[type] = updated
        }
    }

    private fun analyzeImage(postureType: PostureType) {
        val currentPosture = _postureStates.value[postureType] ?: return
        val bytes = currentPosture.byteArray ?: return

        screenModelScope.launch {
            try {
                val findings = postureAnalysisRepository.getPostureFindingList(bytes, postureType)
                updatePostureState(postureType, currentPosture.copy(findings = findings))

                moveToReportIfReady()
            } catch (e: Exception) {
                updatePostureState(postureType, currentPosture.copy(error = e.message))

                showPostureOptions()
            }
        }
    }

    private fun captureGalleryPhoto(
        byteArray: ByteArray,
        bitmap: ImageBitmap,
    ) {
        val postureType = (_contentState.value as? PostureAnalysisContentState.CameraPreview)?.postureType ?: return

        updatePostureState(
            postureType,
            PostureOptionState(
                type = postureType,
                byteArray = byteArray,
                bitmap = bitmap
            )
        )

        analyzeImage(postureType)

        moveToNextPostureIfAny()
    }

    @OptIn(ExperimentalResourceApi::class, ExperimentalUuidApi::class)
    private fun captureCameraPhoto(
        cameraController: CameraController,
        imageSaverPlugin: ImageSaverPlugin
    ) {
        val postureType = (_contentState.value as? PostureAnalysisContentState.CameraPreview)?.postureType ?: return

        screenModelScope.launch {

            when (val result = cameraController.takePicture()) {
                is ImageCaptureResult.Success -> {
                    val byteArray = result.byteArray
                    val bitmap = byteArray.decodeToImageBitmap()

                    updatePostureState(
                        postureType,
                        PostureOptionState(
                            type = postureType,
                            byteArray = byteArray,
                            bitmap = bitmap
                        )
                    )

                    analyzeImage(postureType)

                    if (!imageSaverPlugin.config.isAutoSave) {
                        val customName = "posture_${Uuid.random().toHexString()}"
                        imageSaverPlugin.saveImage(byteArray, customName)
                    }
                }

                is ImageCaptureResult.Error -> {
                    updatePostureState(
                        postureType,
                        PostureOptionState(
                            type = postureType,
                            error = result.exception.message
                        )
                    )

                }
            }

            moveToNextPostureIfAny()
        }
    }

}
