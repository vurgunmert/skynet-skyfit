package com.vurgun.skyfit.feature.bodyanalysis.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.data.bodyanalysis.model.PostureType
import com.vurgun.skyfit.data.bodyanalysis.repository.BodyAnalysisRepository
import com.vurgun.skyfit.data.bodyanalysis.repository.BodyTypeAnalysisRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class MobileUserBodyAnalysisState {
    data object Info : MobileUserBodyAnalysisState()
    data object PostureOptions : MobileUserBodyAnalysisState()
    data class CameraPreview(val postureType: PostureType, val showGuide: Boolean = true) : MobileUserBodyAnalysisState()
    data object Scanning : MobileUserBodyAnalysisState()
    data object CaptureResult : MobileUserBodyAnalysisState()
    data object CaptureResultInsight : MobileUserBodyAnalysisState()
    data object CaptureResultExit : MobileUserBodyAnalysisState()
}


class MobileUserBodyAnalysisViewModel : ScreenModel {

    private val _uiState = MutableStateFlow<MobileUserBodyAnalysisState>(MobileUserBodyAnalysisState.Info)
    val uiState: StateFlow<MobileUserBodyAnalysisState> = _uiState.asStateFlow()

    private val _capturedPostures = MutableStateFlow<Set<PostureType>>(emptySet())
    val capturedPostures: StateFlow<Set<PostureType>> = _capturedPostures.asStateFlow()

    private val bodyTypeAnalysisRepository = BodyTypeAnalysisRepository()
    private val bodyAnalysisRepository = BodyAnalysisRepository()

    fun showInfoScreen() {
        _uiState.value = MobileUserBodyAnalysisState.Info
    }

    fun dismissInfoScreen() {
        _uiState.value = MobileUserBodyAnalysisState.PostureOptions
    }

    fun selectPosture(posture: PostureType) {
        _uiState.value = MobileUserBodyAnalysisState.CameraPreview(posture, showGuide = true)
    }

    fun openCamera(posture: PostureType) {
        _uiState.value = MobileUserBodyAnalysisState.CameraPreview(posture, showGuide = true)
    }

    fun toggleGuideVisibility(posture: PostureType, isGuideEnabled: Boolean) {
        _uiState.value = MobileUserBodyAnalysisState.CameraPreview(posture, showGuide = isGuideEnabled)
    }

    fun startScanning(encodedBase64Image: String?) {
        if (encodedBase64Image != null) {
            //TODO: Body Analysis

            screenModelScope.launch {
                val response = bodyAnalysisRepository.sendBase64EncodedImage(encodedBase64Image)
                //TODO: In what format we can handle this -> Ahmet?
                print(response)
                delay(2000)
                _uiState.value = MobileUserBodyAnalysisState.CaptureResult
            }

            //TODO: Body Type Analysis

            screenModelScope.launch {
                //TODO: Can we send base64 instead of a image url -> Ahmet?
//                val predictionResponse = bodyTypeAnalysisRepository.sendImageUrl("https://source.roboflow.com/CECMTkNg4zdmZ0o2pq6HcI85gRj1/xN6r5Nl2vq6t24LQBsKH/thumb.jpg")
//                println(predictionResponse)
                delay(2000)
                _uiState.value = MobileUserBodyAnalysisState.CaptureResult
            }
        } else {
            print("TODO: show error")
        }
        _uiState.value = MobileUserBodyAnalysisState.Scanning
    }

    fun showCaptureResults() {
        _uiState.value = MobileUserBodyAnalysisState.CaptureResult
    }

    fun showCaptureInsights() {
        _uiState.value = MobileUserBodyAnalysisState.CaptureResultInsight
    }

    fun showCaptureExitScreen() {
        _uiState.value = MobileUserBodyAnalysisState.CaptureResultExit
    }

    /** Finds the next posture that hasn't been captured */
    private fun findNextUncapturedPosture(): PostureType {
        return PostureType.entries.firstOrNull { it !in _capturedPostures.value } ?: PostureType.Front
    }
}

