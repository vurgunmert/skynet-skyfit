package com.vurgun.skyfit.profile.trainer.owner

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.TrainerAccount
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionRowViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.profile.facility.visitor.FacilityProfileVisitorUiState
import com.vurgun.skyfit.profile.model.ProfileDestination
import com.vurgun.skyfit.profile.trainer.owner.TrainerProfileUiEffect.*
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlin.collections.orEmpty

val fakePosts: List<SocialPostItemViewData> = List(6) { index ->
    SocialPostItemViewData(
        postId = "post_${index + 1}",
        username = listOf("JohnDoe", "FitnessQueen", "MikeTrainer", "EmmaRunner", "DavidGym", "SophiaYoga").random(),
        socialLink = listOf(
            "https://instagram.com/user",
            "https://twitter.com/user",
            "https://linkedin.com/user",
            null
        ).random(),
        timeAgo = listOf("5 min ago", "2 hours ago", "1 day ago", "3 days ago", "1 week ago").random(),
        profileImageUrl = listOf(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxNUUshHHqOs2sWxJAZPctGScPNewNivZn-w&s",
            "https://images.squarespace-cdn.com/content/v1/63f59136c5b45330af8a1b13/be8fe8de-0eec-49c6-9140-82a501e0422e/Screen+Shot+2023-04-19+at+3.01.08+PM.png",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxNUUshHHqOs2sWxJAZPctGScPNewNivZn-w&s",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0HX6fpDHt9hoOC5XPVJtHMGgLiXpcICKXfA&s",
            null
        ).random(),
        content = listOf(
            "Just finished an amazing workout! 💪",
            "Morning yoga session done! 🧘‍♀️",
            "Any tips for increasing stamina? 🏃‍♂️",
            "Trying out a new HIIT routine. 🔥",
            "Recovery day with some light stretching.",
            "Nutrition is key! What’s your go-to meal?"
        ).random(),
        imageUrl = listOf(
            "https://www.teatatutoasted.co.nz/cdn/shop/articles/Copy_of_Blog_pics_3_195ecc96-5c21-4363-b9c4-1e9458217175_768x.png?v=1727827333",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPfpidl_dC5LY4ASphW2qVybLkXqW5bI-BXg&s",
            "https://images.squarespace-cdn.com/content/v1/5885cce9e6f2e17ade281ea3/1729523033091-3GQ5CLQPRB5Z056ZKBYU/4.png?format=2500w",
            null,
            null,
            null,
            null
        ).random(),
        favoriteCount = (0..500).random(),
        commentCount = (0..200).random(),
        shareCount = (0..100).random(),
    )
}

sealed interface TrainerProfileUiState {
    data object Loading : TrainerProfileUiState
    data class Error(val message: String) : TrainerProfileUiState
    data class Content(
        val profile: TrainerProfile,
        val destination: ProfileDestination = ProfileDestination.About,
        val isVisiting: Boolean = false,
        val isFollowing: Boolean = false,
        val lessons: List<LessonSessionItemViewData> = emptyList(),
        val gallery: PhotoGalleryStackViewData? = null,
        val specialties: LifestyleActionRowViewData? = null,
        val posts: List<SocialPostItemViewData> = fakePosts
    ) : TrainerProfileUiState
}

sealed interface TrainerProfileUiAction {
    data class OnDestinationChanged(val destination: ProfileDestination) : TrainerProfileUiAction
    data object OnClickBack : TrainerProfileUiAction
    data object OnClickSettings : TrainerProfileUiAction
    data object OnClickNewPost : TrainerProfileUiAction
    data object OnClickToCreatePost : TrainerProfileUiAction
    data object OnClickToAppointments : TrainerProfileUiAction
    data object OnClickBookAppointment : TrainerProfileUiAction
    data object OnClickSendMessage : TrainerProfileUiAction
    data class ChangeDate(val date: LocalDate) : TrainerProfileUiAction
    data object OnClickShowSchedule : TrainerProfileUiAction
}

sealed interface TrainerProfileUiEffect {
    data object NavigateBack : TrainerProfileUiEffect
    data object NavigateToSettings : TrainerProfileUiEffect
    data object NavigateToCreatePost : TrainerProfileUiEffect
    data object NavigateToAppointments : TrainerProfileUiEffect
    data class NavigateToTrainerSchedule(val trainerId: Int) : TrainerProfileUiEffect
    data class NavigateToChatWithTrainer(val trainerId: Int) : TrainerProfileUiEffect
}

class TrainerProfileViewModel(
    private val userManager: ActiveAccountManager,
    private val lessonMapper: LessonSessionItemViewDataMapper,
    private val trainerRepository: TrainerRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<TrainerProfileUiState>(TrainerProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<TrainerProfileUiEffect>()
    val effect: SharedFlow<TrainerProfileUiEffect> = _effect

    private var activeTrainerId: Int? = null

    fun onAction(action: TrainerProfileUiAction) {
        when (action) {
            is TrainerProfileUiAction.OnDestinationChanged -> updateDestination(action.destination)
            is TrainerProfileUiAction.OnClickToCreatePost -> emitEffect(TrainerProfileUiEffect.NavigateToCreatePost)
            is TrainerProfileUiAction.OnClickSettings -> emitEffect(TrainerProfileUiEffect.NavigateToSettings)
            is TrainerProfileUiAction.OnClickToAppointments -> emitEffect(TrainerProfileUiEffect.NavigateToAppointments)
            TrainerProfileUiAction.OnClickBack -> emitEffect(TrainerProfileUiEffect.NavigateBack)
            TrainerProfileUiAction.OnClickNewPost -> emitEffect(TrainerProfileUiEffect.NavigateToCreatePost)
            TrainerProfileUiAction.OnClickBookAppointment ->  emitEffect(NavigateToTrainerSchedule(activeTrainerId!!))
            TrainerProfileUiAction.OnClickSendMessage -> emitEffect(NavigateToChatWithTrainer(activeTrainerId!!))
            is TrainerProfileUiAction.ChangeDate -> updateLessons(action.date)
            TrainerProfileUiAction.OnClickShowSchedule -> emitEffect(NavigateToTrainerSchedule(activeTrainerId!!))
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

    private suspend fun fetchLessons(trainerId: Int, date: LocalDate = LocalDate.now()): List<LessonSessionItemViewData> {
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

    private fun emitEffect(effect: TrainerProfileUiEffect) {
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
}