package com.vurgun.skyfit.profile.trainer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.*
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionRowViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.social.repository.SocialMediaRepository
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.profile.model.ProfileDestination
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface TrainerProfileUiState {
    data object Loading : TrainerProfileUiState
    data class Error(val message: String) : TrainerProfileUiState
    data class Content(
        val profile: TrainerProfile,
        val destination: ProfileDestination,
        val isVisiting: Boolean = false,
        val isFollowing: Boolean = false,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val gallery: PhotoGalleryStackViewData? = null,
        val specialties: LifestyleActionRowViewData? = null,
        val posts: List<SocialPostItemViewData> = emptyList()
    ) : TrainerProfileUiState
}

sealed interface TrainerProfileUiAction {
    data class OnDestinationChanged(val destination: ProfileDestination) : TrainerProfileUiAction
    data object OnClickBack : TrainerProfileUiAction
    data object OnClickSettings : TrainerProfileUiAction
    data object OnClickNewPost : TrainerProfileUiAction
    data object OnClickToCreatePost : TrainerProfileUiAction
    data object OnClickShowAllLessons : TrainerProfileUiAction
    data class OnClickToUpcomingLesson(val lessonId: Int) : TrainerProfileUiAction
    data object OnClickBookAppointment : TrainerProfileUiAction
    data object OnClickSendMessage : TrainerProfileUiAction
    data class ChangeDate(val date: LocalDate) : TrainerProfileUiAction
    data object OnClickShowSchedule : TrainerProfileUiAction
    data object OnClickPost : TrainerProfileUiAction
    data object OnClickCommentPost : TrainerProfileUiAction
    data object OnClickLikePost : TrainerProfileUiAction
    data object OnClickSharePost : TrainerProfileUiAction
    data class OnSendQuickPost(val content: String) : TrainerProfileUiAction
}

sealed interface TrainerProfileUiEvent {
    data object NavigateBack : TrainerProfileUiEvent
    data object NavigateToSettings : TrainerProfileUiEvent
    data object NavigateToCreatePost : TrainerProfileUiEvent
    data object NavigateToAppointments : TrainerProfileUiEvent
    data class NavigateToTrainerSchedule(val trainerId: Int) : TrainerProfileUiEvent
    data class NavigateToChatWithTrainer(val trainerId: Int) : TrainerProfileUiEvent
    data class NavigateToLessonDetail(val lessonId: Int) : TrainerProfileUiEvent
}

class TrainerProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val trainerRepository: TrainerRepository,
    private val socialMediaRepository: SocialMediaRepository,
) : ScreenModel {

    private val _uiState = UiStateDelegate<TrainerProfileUiState>(TrainerProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<TrainerProfileUiEvent>()
    val effect: SharedFlow<TrainerProfileUiEvent> = _effect

    private val _event = UiEventDelegate<TrainerProfileUiEvent>()
    val event = _event.eventFlow.stateIn(screenModelScope, SharingStarted.Lazily, null)

    private var activeTrainerId: Int? = null

    fun onAction(action: TrainerProfileUiAction) {
        when (action) {
            is TrainerProfileUiAction.OnDestinationChanged -> updateDestination(action.destination)
            is TrainerProfileUiAction.OnClickToCreatePost -> emitEffect(TrainerProfileUiEvent.NavigateToCreatePost)
            is TrainerProfileUiAction.OnClickSettings -> emitEffect(TrainerProfileUiEvent.NavigateToSettings)
            is TrainerProfileUiAction.OnClickShowAllLessons -> emitEffect(TrainerProfileUiEvent.NavigateToAppointments)
            is TrainerProfileUiAction.OnClickToUpcomingLesson -> emitEffect(TrainerProfileUiEvent.NavigateToLessonDetail(action.lessonId))
            TrainerProfileUiAction.OnClickBack -> emitEffect(TrainerProfileUiEvent.NavigateBack)
            TrainerProfileUiAction.OnClickNewPost -> emitEffect(TrainerProfileUiEvent.NavigateToCreatePost)
            TrainerProfileUiAction.OnClickBookAppointment -> emitEffect(
                TrainerProfileUiEvent.NavigateToTrainerSchedule(
                    activeTrainerId!!
                )
            )
            TrainerProfileUiAction.OnClickSendMessage -> emitEffect(
                TrainerProfileUiEvent.NavigateToChatWithTrainer(
                    activeTrainerId!!
                )
            )
            is TrainerProfileUiAction.ChangeDate -> updateLessons(action.date)
            TrainerProfileUiAction.OnClickShowSchedule -> emitEffect(
                TrainerProfileUiEvent.NavigateToTrainerSchedule(
                    activeTrainerId!!
                )
            )
            TrainerProfileUiAction.OnClickCommentPost -> {
//                TODO()
            }
            TrainerProfileUiAction.OnClickLikePost -> {
//                TODO()
            }
            TrainerProfileUiAction.OnClickPost -> {
//                TODO()
            }
            TrainerProfileUiAction.OnClickSharePost -> {
//                TODO()
            }
            is TrainerProfileUiAction.OnSendQuickPost -> {
//                TODO()
            }
        }
    }

    fun loadData(trainerId: Int? = null) {
        _uiState.update(TrainerProfileUiState.Loading)

        val isVisiting = trainerId != null
        val loadedTrainerId = trainerId ?: (userManager.account.value as? TrainerAccount)?.trainerId ?: return
        activeTrainerId = loadedTrainerId

        screenModelScope.launch {
            runCatching {
                val profileDeferred = async { trainerRepository.getTrainerProfile(loadedTrainerId).getOrThrow() }
                val lessons = if (isVisiting) fetchLessons(loadedTrainerId) else fetchUpcomingLessons(loadedTrainerId)

                _uiState.update(
                    TrainerProfileUiState.Content(
                        destination = ProfileDestination.About,
                        isVisiting = isVisiting,
                        isFollowing = false,
                        profile = profileDeferred.await(),
                        lessons = lessons
                    )
                )

                launch {
                    refreshPosts()
                }
            }.onFailure { error ->
                _uiState.update(TrainerProfileUiState.Error(error.message ?: "Error loading profile"))
            }
        }
    }

    fun refreshData() {
        activeTrainerId?.let { trainerId ->
            loadData(trainerId)
        }
    }

    fun fetchSpecialities(): LifestyleActionRowViewData {
        val specialtiesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Fonksiyonel Antrenman"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id, "Kuvvet ve Kondisyon"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Beslenme Danışmanlığı"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Atletik Performans Geliştirme")
        )

        return LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.TROPHY.id, // Medal Icon for Specialties
            title = "Uzmanlık Alanları",
            items = specialtiesViewData,
            iconSizePx = 48
        )
    }

    private suspend fun fetchUpcomingLessons(trainerId: Int): List<LessonSessionItemViewData> {
        return trainerRepository.getUpcomingLessonsByTrainer(trainerId)
            .getOrNull()?.let { list -> list.map { lessonMapper.map(it) } }.orEmpty()
    }

    private suspend fun fetchLessons(
        trainerId: Int,
        date: LocalDate = LocalDate.now()
    ): List<LessonSessionItemViewData> {
        return trainerRepository.getLessonsByTrainer(
            trainerId = trainerId,
            startDate = date,
            endDate = null
        ).getOrNull()?.let { list -> list.map { lessonMapper.map(it) } }.orEmpty()
    }

    private fun updateDestination(destination: ProfileDestination) {
        uiState.value.let { it as? TrainerProfileUiState.Content }?.let {
            _uiState.update(it.copy(destination = destination))
        }
    }

    private fun emitEffect(effect: TrainerProfileUiEvent) {
        screenModelScope.launch {
            _effect.emitOrNull(effect)
        }
    }

    private fun updateLessons(date: LocalDate = LocalDate.now()) {
        val id = activeTrainerId ?: return
        val currentState = uiState.value as? TrainerProfileUiState.Content ?: return
        screenModelScope.launch {
            runCatching {
                val newLessons = fetchLessons(id, date)
                _uiState.update(currentState.copy(lessons = newLessons))
            }.onFailure { error ->
                _uiState.update(TrainerProfileUiState.Error("Failed to update lessons: ${error.message}"))
            }
        }
    }

    private fun refreshPosts() {
        val content = (uiState.value as? TrainerProfileUiState.Content) ?: return
        val typeId = AccountRole.Trainer.typeId
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
            }
        }
    }
}