package com.vurgun.skyfit.feature.profile.trainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.TrainerDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.user.domain.TrainerProfile
import com.vurgun.skyfit.data.user.repository.ProfileRepository
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.profile.user.UserProfileHeaderViewData
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainerProfileOwnerViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val lessonSessionItemViewDataMapper: LessonSessionItemViewDataMapper,
    private val profileRepository: ProfileRepository
) : ViewModel() {

    private val trainerUser: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ User is not a Trainer")

    private val _profileData = MutableStateFlow<UserProfileHeaderViewData?>(null)
    val profileData: StateFlow<UserProfileHeaderViewData?> get() = _profileData

    private val _trainerProfile = MutableStateFlow<TrainerProfile?>(null)
    val trainerProfile: StateFlow<TrainerProfile?> get() = _trainerProfile

    private val _specialtiesRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val specialtiesRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _specialtiesRowViewData

    private val _postsVisible = MutableStateFlow(false)
    val postsVisible: StateFlow<Boolean> get() = _postsVisible

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _lessonsColumViewData = MutableStateFlow<LessonSessionColumnViewData?>(null)
    val lessonsColumViewData: StateFlow<LessonSessionColumnViewData?> get() = _lessonsColumViewData

    init {
        loadData()
    }

    fun loadData() {
        _profileData.value = UserProfileHeaderViewData(
            name = trainerUser.firstName,
            username = "@${trainerUser.username}",
            profileImageUrl = trainerUser.profileImageUrl,
            backgroundImageUrl = trainerUser.backgroundImageUrl,
            height = trainerUser.height.toString(),
            weight = trainerUser.weight.toString(),
            bodyType = trainerUser.bodyType.turkishShort
        )

        viewModelScope.launch {
            courseRepository.getUpcomingLessonsByTrainer(trainerId = trainerUser.trainerId)
                .map { lessons ->
                    lessons.map { lessonSessionItemViewDataMapper.map(it) }
                }
                .fold(
                    onSuccess = {
                        _lessonsColumViewData.value = LessonSessionColumnViewData(
                            iconId = SkyFitAsset.SkyFitIcon.EXERCISES.id,
                            title = "Dersler",
                            items = it
                        )
                    },
                    onFailure = { error ->
                        println("❌ CANNOT GET LESSONS: $error")
                    }
                )

        }

//        val specialtiesViewData = listOf(
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Fonksiyonel Antrenman"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id, "Kuvvet ve Kondisyon"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Beslenme Danışmanlığı"),
//            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Atletik Performans Geliştirme")
//        )
//
//        _specialtiesRowViewData.value = LifestyleActionRowViewData(
//            iconId = SkyFitAsset.SkyFitIcon.TROPHY.id, // Medal Icon for Specialties
//            title = "Uzmanlık Alanları",
//            items = specialtiesViewData,
//            iconSizePx = 48
//        )

    }

    fun togglePostTab(isVisible: Boolean) {
        _postsVisible.value = isVisible
    }

}