package com.vurgun.skyfit.profile.user.model

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.humanizeAgo
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.UserAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.repository.MeasurementRepository
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.social.repository.SocialMediaRepository
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.user.model.UserProfileEffect.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed class UserProfileUiState {
    data object Loading : UserProfileUiState()
    data class Error(val message: String) : UserProfileUiState()
    data class Content(
        val profile: UserProfile,
        val destination: ProfileDestination = ProfileDestination.About,
        val isVisiting: Boolean = false,
        val isFollowing: Boolean = false,
        val memberFacilityProfile: FacilityProfile? = null,
        val appointments: List<LessonSessionItemViewData> = emptyList(),
        val posts: List<SocialPostItemViewData> = emptyList(),
        val exercises: List<LifestyleActionItemViewData> = emptyList(),
        val habits: List<LifestyleActionItemViewData> = emptyList(),
        val measurements: List<Measurement> = emptyList(),
    ) : UserProfileUiState() {
        val canNavigateBack: Boolean = isVisiting
    }
}

sealed interface UserProfileAction {
    data object ClickBack : UserProfileAction
    data object ClickAppointments : UserProfileAction
    data object OnClickSettings : UserProfileAction
    data object OnClickNewPost : UserProfileAction
    data object OnClickFollow : UserProfileAction
    data object OnClickUnfollow : UserProfileAction
    data object OnClickPost : UserProfileAction
    data object OnClickCommentPost : UserProfileAction
    data object OnClickLikePost : UserProfileAction
    data object OnClickSharePost : UserProfileAction
    data class OnSendQuickPost(val content: String) : UserProfileAction
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileAction
    data class OnDestinationChanged(val destination: ProfileDestination) : UserProfileAction
}

sealed interface UserProfileEffect {
    data object NavigateBack : UserProfileEffect
    data object NavigateToAppointments : UserProfileEffect
    data object NavigateToSettings : UserProfileEffect
    data object NavigateToCreatePost : UserProfileEffect
    data class NavigateToVisitFacility(val gymId: Int) : UserProfileEffect
}

class UserProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val userRepository: UserRepository,
    private val facilityRepository: FacilityRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val socialMediaRepository: SocialMediaRepository,
    private val measurementRepository: MeasurementRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserProfileUiState>(UserProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserProfileEffect>()
    val effect: SharedFlow<UserProfileEffect> = _effect

    private var activeUserId: Int? = null

    fun onAction(action: UserProfileAction) {
        when (action) {
            UserProfileAction.ClickBack ->
                _effect.emitIn(screenModelScope, NavigateBack)

            UserProfileAction.ClickAppointments ->
                _effect.emitIn(screenModelScope, NavigateToAppointments)

            UserProfileAction.OnClickSettings ->
                _effect.emitIn(screenModelScope, NavigateToSettings)

            UserProfileAction.OnClickNewPost ->
                _effect.emitIn(screenModelScope, NavigateToCreatePost)

            is UserProfileAction.NavigateToVisitFacility ->
                _effect.emitIn(screenModelScope, NavigateToVisitFacility(action.gymId))

            is UserProfileAction.OnDestinationChanged -> {
                updateDestination(action.destination)
            }

            UserProfileAction.OnClickFollow -> {
//                TODO()
            }

            UserProfileAction.OnClickUnfollow -> {
//                TODO()
            }

            UserProfileAction.OnClickCommentPost -> {
//                TODO()
            }

            UserProfileAction.OnClickLikePost -> {
//                TODO()
            }

            UserProfileAction.OnClickPost -> {
//                TODO()
            }

            UserProfileAction.OnClickSharePost -> {
//                TODO()
            }

            is UserProfileAction.OnSendQuickPost -> {
//                TODO()
            }
        }
    }

    fun loadData(userId: Int? = null) {
        val isVisiting = userId != null
        val loadedUserId = userId ?: (userManager.account.value as? UserAccount)?.normalUserId ?: return
        activeUserId = loadedUserId

        screenModelScope.launch {
            _uiState.update(UserProfileUiState.Loading)

            runCatching {
                val profile = userRepository.getUserProfile(loadedUserId).getOrThrow()
                val appointmentsDeferred = async { fetchAppointments(profile.normalUserId) }
                val facilityDeferred = profile.memberGymId?.let { gymId ->
                    async { facilityRepository.getFacilityProfile(gymId).getOrNull() }
                }

                val appointments = appointmentsDeferred.await()
                val memberFacilityProfile = facilityDeferred?.await()

                _uiState.update(
                    UserProfileUiState.Content(
                        destination = ProfileDestination.About,
                        isVisiting = isVisiting,
                        isFollowing = false,
                        profile = profile,
                        memberFacilityProfile = memberFacilityProfile,
                        appointments = appointments,
                    )
                )

                launch {
                    refreshPosts()
                }
            }.onFailure { error ->
                _uiState.update(UserProfileUiState.Error(error.message ?: "Profil yüklenemedi."))
            }
        }
    }

    fun refreshData() {
        activeUserId?.let {
            loadData(it)
        }
    }

    private fun updateDestination(route: ProfileDestination) {
        uiState.value.let { it as? UserProfileUiState.Content }?.let {
            _uiState.update(it.copy(destination = route))
        }
    }

    private suspend fun fetchAppointments(normalUserId: Int): List<LessonSessionItemViewData> {
        return userRepository.getUpcomingAppointmentsByUser(normalUserId, 3)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun fetchPhotoGallery() {
//        val photoDiaryViewData = PhotoGalleryStackViewData()
    }

    private fun fetchExerciseHistory() {

//        val exercisesViewData = listOf(
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Şınav"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.id, "Yoga"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Pull up"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.id, "Mekik"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.id, "İp atlama")
//        )
//        val exercisesRowViewData = LifestyleActionRowViewData(
//            iconId = SkyFitAsset.SkyFitIcon.CLOCK.id,
//            title = "Egzersiz Geçmişi",
//            items = exercisesViewData
//        )
    }

    private fun fetchHabits() {

//        val habitsViewData = listOf(
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.id, "Düzensiz Uyku"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Fast Food"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.id, "Smoking")
//        )
//        val habitsRowViewData = LifestyleActionRowViewData(
//            iconId = SkyFitAsset.SkyFitIcon.YOGA.id,
//            title = "Alışkanlıklar",
//            items = habitsViewData
//        )
    }

    private fun refreshPosts() {
        val content = (uiState.value as? UserProfileUiState.Content) ?: return
        val typeId = AccountRole.User.typeId
        val userId = content.profile.userId


        screenModelScope.launch {
            runCatching {
                val posts = socialMediaRepository.getPostsByUser(userId, typeId)
                    .getOrDefault(emptyList())
                    .sortedByDescending { it.updateDate ?: it.createdDate }
                    .map {
                        val showingDate = it.updateDate ?: it.createdDate
                        SocialPostItemViewData(
                            postId = it.postId,
                            creatorUsername = it.username,
                            creatorName = it.name,
                            creatorImageUrl = it.profileImageUrl,
                            timeAgo = showingDate.humanizeAgo(),
                            content = it.contentText,
                            imageUrl = null,
                            likeCount = it.likeCount,
                            commentCount = it.commentCount,
                            shareCount = it.shareCount
                        )
                    }

                _uiState.update(content.copy(posts = posts))
            }.onFailure { error ->
                print(error.message)
//                _uiState.update(content.copy(posts = emptyList()))
            }
        }
    }
}