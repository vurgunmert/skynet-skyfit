package com.vurgun.skyfit.feature.connect.social

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.social.repository.SocialMediaRepository
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

sealed class NewPostUiState {
    data object Loading : NewPostUiState()
    data class Content(
        val photoImageUrl: String? = null
    ) : NewPostUiState()

    data class Error(val message: String?) : NewPostUiState()
}

sealed interface NewPostUiAction {
    data object OnClickCancel : NewPostUiAction
    data class OnClickSend(val text: String) : NewPostUiAction
}

sealed interface NewPostUiEffect {
    data object NavigateBack : NewPostUiEffect
}

class NewPostViewModel(
    private val accountManager: ActiveAccountManager,
    private val socialMediaRepository: SocialMediaRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<NewPostUiState>(NewPostUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = SingleSharedFlow<NewPostUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun onAction(action: NewPostUiAction) {
        when (action) {
            NewPostUiAction.OnClickCancel ->
                _uiEffect.emitIn(screenModelScope, NewPostUiEffect.NavigateBack)

            is NewPostUiAction.OnClickSend ->
                sendPost(action.text)
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val accountType = accountManager.accountType.firstOrNull() ?: error("Account type is null")

                _uiState.update(
                    NewPostUiState.Content(
                        photoImageUrl = accountType.photoImageUrl
                    )
                )
            }.onFailure {
                _uiState.update(NewPostUiState.Error(it.message))
            }
        }
    }

    fun sendPost(text: String) {
        screenModelScope.launch {
            runCatching {
                socialMediaRepository.createPost(text)
                _uiEffect.emitIn(screenModelScope, NewPostUiEffect.NavigateBack)
            }.onFailure { error ->
                _uiState.update(NewPostUiState.Error(error.message))
            }
        }
    }
}