package com.vurgun.skyfit.feature.persona.settings.user.profile

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.HeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.global.model.WeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.core.network.RemoteImageDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UserEditProfileUiState {
    data object Loading : UserEditProfileUiState
    data class Error(val message: String) : UserEditProfileUiState
    data class Content(val form: UserEditProfileFormState) : UserEditProfileUiState
}

data class UserEditProfileFormState(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val height: Int,
    val heightUnit: HeightUnitType = HeightUnitType.CM,
    val weight: Int,
    val weightUnit: WeightUnitType = WeightUnitType.KG,
    val bodyType: BodyType,
    val profileImageUrl: String? = null,
    val profileImageBytes: ByteArray? = null,
    val backgroundImageUrl: String? = null,
    val backgroundImageBytes: ByteArray? = null,
    val isUpdated: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserEditProfileFormState) return false

        return userName == other.userName &&
                firstName == other.firstName &&
                lastName == other.lastName &&
                height == other.height &&
                heightUnit == other.heightUnit &&
                weight == other.weight &&
                weightUnit == other.weightUnit &&
                bodyType == other.bodyType &&
                profileImageUrl == other.profileImageUrl &&
                profileImageBytes contentEquals other.profileImageBytes &&
                backgroundImageUrl == other.backgroundImageUrl &&
                backgroundImageBytes contentEquals other.backgroundImageBytes &&
                isUpdated == other.isUpdated
    }

    override fun hashCode(): Int {
        var result = userName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + height
        result = 31 * result + heightUnit.hashCode()
        result = 31 * result + weight
        result = 31 * result + weightUnit.hashCode()
        result = 31 * result + bodyType.hashCode()
        result = 31 * result + (profileImageUrl?.hashCode() ?: 0)
        result = 31 * result + (profileImageBytes?.contentHashCode() ?: 0)
        result = 31 * result + (backgroundImageUrl?.hashCode() ?: 0)
        result = 31 * result + (backgroundImageBytes?.contentHashCode() ?: 0)
        result = 31 * result + isUpdated.hashCode()
        return result
    }
}

sealed class UserEditProfileAction {
    data object NavigateToBack : UserEditProfileAction()
    data object SaveChanges : UserEditProfileAction()
    data class UpdateProfileImage(val value: ByteArray?) : UserEditProfileAction()
    data class UpdateBackgroundImage(val value: ByteArray?) : UserEditProfileAction()
    data object DeleteProfileImage : UserEditProfileAction()
    data object DeleteBackgroundImage : UserEditProfileAction()
    data class UpdateUserName(val value: String) : UserEditProfileAction()
    data class UpdateFirstName(val value: String) : UserEditProfileAction()
    data class UpdateLastName(val value: String) : UserEditProfileAction()
    data class UpdateHeight(val value: Int) : UserEditProfileAction()
    data class UpdateWeight(val value: Int) : UserEditProfileAction()
    data class UpdateBodyType(val value: BodyType) : UserEditProfileAction()
}

sealed class UserEditProfileEffect {
    data object NavigateToBack : UserEditProfileEffect()
    data class ShowSaveError(val message: String) : UserEditProfileEffect()
}

class UserSettingsEditProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val userRepository: UserRepository,
    private val remoteImageDataSource: RemoteImageDataSource
) : ScreenModel {

    private val _uiState = MutableStateFlow<UserEditProfileUiState>(UserEditProfileUiState.Loading)
    val uiState: StateFlow<UserEditProfileUiState> = _uiState

    private val _effect = SingleSharedFlow<UserEditProfileEffect>()
    val effect: SharedFlow<UserEditProfileEffect> = _effect

    private val user: UserAccount
        get() = userManager.user.value as? UserAccount
            ?: error("❌ current account is not user")

    private var initialState: UserEditProfileFormState? = null

    fun onAction(action: UserEditProfileAction) {
        when (action) {
            UserEditProfileAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, UserEditProfileEffect.NavigateToBack)

            UserEditProfileAction.SaveChanges -> saveChanges()
            is UserEditProfileAction.UpdateProfileImage ->
                updateForm { copy(profileImageBytes = action.value) }
            is UserEditProfileAction.DeleteProfileImage ->
                updateForm { copy(profileImageBytes = null, profileImageUrl = null) }

            is UserEditProfileAction.UpdateBackgroundImage ->
                updateForm { copy(backgroundImageBytes = action.value) }
            is UserEditProfileAction.DeleteBackgroundImage ->
                updateForm { copy(backgroundImageBytes = null, backgroundImageUrl = null) }

            is UserEditProfileAction.UpdateUserName -> updateForm { copy(userName = action.value) }
            is UserEditProfileAction.UpdateFirstName -> updateForm { copy(firstName = action.value) }
            is UserEditProfileAction.UpdateLastName -> updateForm { copy(lastName = action.value) }
            is UserEditProfileAction.UpdateHeight -> updateForm { copy(height = action.value) }
            is UserEditProfileAction.UpdateWeight -> updateForm { copy(weight = action.value) }
            is UserEditProfileAction.UpdateBodyType -> updateForm { copy(bodyType = action.value) }
        }
    }

    fun loadData() {
        screenModelScope.launch {
            try {
                val userProfile = userRepository.getUserProfile(user.normalUserId).getOrThrow()

                val profileImageUrl = userProfile.profileImageUrl
                val backgroundImageUrl = userProfile.backgroundImageUrl

                // Load image bytes in parallel
                val profileImageDeferred = profileImageUrl?.let { async { remoteImageDataSource.getImageBytes(it) } }
                val backgroundImageDeferred = backgroundImageUrl?.let { async { remoteImageDataSource.getImageBytes(it) } }

                val formState = UserEditProfileFormState(
                    userName = userProfile.username,
                    firstName = userProfile.firstName,
                    lastName = userProfile.lastName,
                    height = userProfile.height,
                    weight = userProfile.weight,
                    bodyType = userProfile.bodyType,
                    profileImageUrl = profileImageUrl,
                    profileImageBytes = profileImageDeferred?.await(),
                    backgroundImageUrl = backgroundImageUrl,
                    backgroundImageBytes = backgroundImageDeferred?.await()
                )

                initialState = formState
                _uiState.value = UserEditProfileUiState.Content(formState)

            } catch (e: Exception) {
                _uiState.value = UserEditProfileUiState.Error(e.message ?: "Profil getirme hatası")
            }
        }
    }

    private fun updateForm(update: UserEditProfileFormState.() -> UserEditProfileFormState) {
        val current = (_uiState.value as? UserEditProfileUiState.Content)?.form ?: return
        val newForm = update(current)
        val updated = newForm.copy(isUpdated = newForm != initialState)
        _uiState.value = UserEditProfileUiState.Content(updated)
    }

    private fun saveChanges() {
        val form = (_uiState.value as? UserEditProfileUiState.Content)?.form ?: return

        screenModelScope.launch {
            _uiState.value = UserEditProfileUiState.Loading

            try {
                userRepository.updateUserProfile(
                    normalUserId = user.normalUserId,
                    username = form.userName,
                    profileImageBytes = form.profileImageBytes,
                    backgroundImageBytes = form.backgroundImageBytes,
                    name = form.firstName,
                    surname = form.lastName,
                    height = form.height,
                    weight = form.weight,
                    bodyTypeId = form.bodyType.id
                )

                initialState = form.copy(isUpdated = false)
                _uiState.value = UserEditProfileUiState.Content(initialState!!)
                _effect.emitIn(screenModelScope, UserEditProfileEffect.NavigateToBack)

            } catch (e: Exception) {
                _effect.emitIn(screenModelScope, UserEditProfileEffect.ShowSaveError(e.message ?: "Kaydetme hatasi"))
            }
        }
    }
}
