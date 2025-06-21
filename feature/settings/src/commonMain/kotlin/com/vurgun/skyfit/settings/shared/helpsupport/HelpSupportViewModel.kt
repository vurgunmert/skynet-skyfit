package com.vurgun.skyfit.settings.shared.helpsupport

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.support.model.SupportType
import com.vurgun.skyfit.core.data.v1.domain.support.repository.SupportRepository
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class HelpSupportUiState {
    object Loading : HelpSupportUiState()
    data class Error(val message: String?) : HelpSupportUiState()
    data class Content(
        val email: String,
        val description: String,
        val supportTypes: List<SupportType>,
        val selectedSupportType: SupportType,
    ) : HelpSupportUiState()
}

sealed interface HelpSupportUiAction {
    data object OnClickBack : HelpSupportUiAction
    data object OnClickSend : HelpSupportUiAction
    data class OnChangeSupportType(val type: SupportType) : HelpSupportUiAction
    data class OnChangeDescription(val value: String) : HelpSupportUiAction
    data class OnChangeEmail(val email: String) : HelpSupportUiAction
}

sealed interface HelpSupportUiEffect {
    data object NavigateBack : HelpSupportUiEffect
    data object NotifyRequestSuccess : HelpSupportUiEffect
}

class HelpSupportViewModel(
    private val supportRepository: SupportRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<HelpSupportUiState>(HelpSupportUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = SingleSharedFlow<HelpSupportUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        loadData()
    }

    fun onAction(action: HelpSupportUiAction) {
        when (action) {
            HelpSupportUiAction.OnClickBack ->
                _uiEffect.emitIn(screenModelScope, HelpSupportUiEffect.NavigateBack)

            HelpSupportUiAction.OnClickSend -> submitRequest()
            is HelpSupportUiAction.OnChangeDescription -> {
                (uiState.value as HelpSupportUiState.Content).let { state ->
                    _uiState.update(state.copy(description = action.value))
                }
            }

            is HelpSupportUiAction.OnChangeEmail -> {
                (uiState.value as HelpSupportUiState.Content).let { state ->
                    _uiState.update(state.copy(email = action.email))
                }
            }

            is HelpSupportUiAction.OnChangeSupportType -> {
                (uiState.value as HelpSupportUiState.Content).let { state ->
                    _uiState.update(state.copy(selectedSupportType = action.type))
                }
            }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            runCatching {
                val supportTypes = supportRepository.getSupportTypes().getOrThrow()
                if (supportTypes.isEmpty()) {
                    error("Support types are empty!")
                }

                _uiState.update(
                    HelpSupportUiState.Content(
                        email = "",
                        description = "",
                        supportTypes = supportTypes,
                        selectedSupportType = supportTypes.first(),
                    )
                )
            }.onFailure { error ->
                _uiState.update(HelpSupportUiState.Error(error.message))
            }
        }
    }

    fun submitRequest() {
        val content = uiState.value as? HelpSupportUiState.Content ?: return
        val supportType = content.selectedSupportType
        val email = content.email
        val description = content.description

        screenModelScope.launch {
            runCatching {
                supportRepository.createSupportRequest(supportType.id, email, description)
                _uiEffect.emitIn(screenModelScope, HelpSupportUiEffect.NotifyRequestSuccess)
            }.onFailure { error ->
                _uiState.update(HelpSupportUiState.Error(error.message))
            }
        }
    }
}