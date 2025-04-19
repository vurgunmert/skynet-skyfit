package com.vurgun.skyfit.feature.profile.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.viewdata.fakePosts
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserProfileUiState(
    val profileData: UserProfileHeaderViewData = UserProfileHeaderViewData(),
    val posts: List<SocialPostItemViewData> = emptyList(),
    val appointments: LessonSessionColumnViewData? = null,
    val showPosts: Boolean = false,
    val showInfoMini: Boolean = false,
    val exercises: LifestyleActionRowViewData? = null,
    val habits: LifestyleActionRowViewData? = null,
    val photoDiary: PhotoGalleryStackViewData? = null
)

class UserProfileOwnerViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts: StateFlow<List<SocialPostItemViewData>> get() = _posts

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> get() = _uiState

    // UI State
    private val _showPosts = MutableStateFlow(false)
    val showPosts: StateFlow<Boolean> get() = _showPosts

    private val _scrollValue = MutableStateFlow(0)
    private val _firstVisibleItemIndex = MutableStateFlow(0)

    // Derived State for Mini Info Card
    val showInfoMini = combine(_scrollValue, _firstVisibleItemIndex) { scrollValue, firstItemIndex ->
        scrollValue > 30 || firstItemIndex > 1
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        val profileViewData = UserProfileHeaderViewData(
            name = user.firstName,
            username = user.username,
            profileImageUrl = user.profileImageUrl,
            backgroundImageUrl = user.backgroundImageUrl,
            height = user.height.toString(),
            weight = user.weight.toString(),
            bodyType = user.bodyType.turkishShort
        )

        loadAppointments()

        val photoDiaryViewData = PhotoGalleryStackViewData()

        val exercisesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.resId, "Şınav"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.resId, "Yoga"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.resId, "Pull up"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.resId, "Mekik"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.resId, "İp atlama")
        )
        val exercisesRowViewData = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.CLOCK.resId,
            title = "Egzersiz Geçmişi",
            items = exercisesViewData
        )

        val habitsViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.resId, "Düzensiz Uyku"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.resId, "Fast Food"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.resId, "Smoking")
        )
        val habitsRowViewData = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.YOGA.resId,
            title = "Alışkanlıklar",
            items = habitsViewData
        )

        loadPosts()

        _uiState.update {
            it.copy(
                profileData = profileViewData,
                exercises = exercisesRowViewData,
                habits = habitsRowViewData,
                photoDiary = photoDiaryViewData
            )
        }
    }

    private fun loadAppointments() {
        viewModelScope.launch {
            courseRepository.getUpcomingAppointmentsByUser(user.normalUserId)
                .map { appointments ->
                    appointments.map {
                        LessonSessionItemViewData(
                            lessonId = it.lessonId,
                            iconId = it.iconId,
                            title = it.title,
                            date = it.startDate.toString(),
                            hours = "${it.startTime} - ${it.endTime}",
                            trainer = it.trainerFullName,
                            facility = it.facilityName,
                            location = it.facilityName,
                            note = it.trainerNote
                        )
                    }
                }
                .fold(
                    onSuccess = { appointments ->
                        if (appointments.isNotEmpty()) {
                            val appointmentsColumnViewData = LessonSessionColumnViewData(
                                iconId = SkyFitAsset.SkyFitIcon.EXERCISES.id,
                                title = "Randevularım",
                                items = appointments
                            )
                            _uiState.update {
                                it.copy(
                                    appointments = appointmentsColumnViewData
                                )
                            }
                        }
                    },
                    onFailure = {
                        print("Failed to get appointments ${it.message}")
                    }
                )
        }
    }

    private fun loadPosts() {
        _posts.value = fakePosts
    }

    fun updateScroll(scrollValue: Int, firstItemIndex: Int) {
        _scrollValue.value = scrollValue
        _firstVisibleItemIndex.value = firstItemIndex
    }

    fun toggleShowPosts(show: Boolean) {
        _showPosts.value = show
    }
}

data class UserProfileHeaderViewData(
    val name: String = "",
    val username: String = "",
    val profileImageUrl: String? = null,
    val backgroundImageUrl: String? = null,
    val height: String = "",
    val weight: String = "",
    val bodyType: String = "",
    val showInfoMini: Boolean = false // Whether to show the mini info card
)