package com.vurgun.skyfit.feature.profile.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.data.user.domain.UserProfile
import com.vurgun.skyfit.data.user.repository.ProfileRepository
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.viewdata.fakePosts
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserProfileUiState(
//    val profileData: UserProfileHeaderViewData = UserProfileHeaderViewData(),
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
    private val courseRepository: CourseRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> get() = _uiState

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts: StateFlow<List<SocialPostItemViewData>> get() = _posts

    private val _appointments = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val appointments: StateFlow<List<LessonSessionItemViewData>> get() = _appointments

    // UI State
    private val _showPosts = MutableStateFlow(false)
    val showPosts: StateFlow<Boolean> get() = _showPosts

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> get() = _userProfile

    private val _memberFacilityProfile = MutableStateFlow<FacilityProfile?>(null)
    val memberFacilityProfile: StateFlow<FacilityProfile?> get() = _memberFacilityProfile

    fun loadData() {
        viewModelScope.launch {
            try {
                val userProfile = profileRepository.getUserProfile(user.normalUserId).getOrThrow()
                _userProfile.value = userProfile

                userProfile.memberGymId?.let { gymId ->
                    _memberFacilityProfile.value = profileRepository.getFacilityProfile(gymId).getOrNull()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        loadAppointments()
    }

    private fun loadPhotoGallery() {
        val photoDiaryViewData = PhotoGalleryStackViewData()
    }

    private fun loadExerciseHistory() {

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
    }

    private fun loadHabits() {

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
                            note = it.trainerNote,
                            capacityRatio = it.quotaInfo
                        )
                    }
                }
                .fold(
                    onSuccess = { appointments ->
                        _appointments.value = appointments
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