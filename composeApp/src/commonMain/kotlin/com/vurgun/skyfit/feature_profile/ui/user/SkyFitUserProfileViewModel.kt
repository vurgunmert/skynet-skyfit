package com.vurgun.skyfit.feature_profile.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.ui.resources.SkyFitAsset
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionColumnViewData
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature_social.ui.components.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature_social.ui.components.viewdata.fakePosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SkyFitUserProfileViewModel : ViewModel() {

    // User Profile Data
    private val _profileData = MutableStateFlow<TopBarGroupViewData>(TopBarGroupViewData())
    val profileData: StateFlow<TopBarGroupViewData> get() = _profileData

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts: StateFlow<List<SocialPostItemViewData>> get() = _posts

    private val _appointmentsColumViewData = MutableStateFlow<LessonSessionColumnViewData?>(null)
    val appointmentsColumViewData: StateFlow<LessonSessionColumnViewData?> get() = _appointmentsColumViewData

    private val _exercisesRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val exercisesRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _exercisesRowViewData

    private val _habitsRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val habitsRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _habitsRowViewData

    private val _photoDiary = MutableStateFlow<PhotoGalleryStackViewData?>(null)
    val photoDiary: StateFlow<PhotoGalleryStackViewData?> get() = _photoDiary

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
        loadProfileData()
        loadPosts()
        loadAppointments()
        _photoDiary.value = PhotoGalleryStackViewData()

        val exercisesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Şınav"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.id, "Yoga"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Pull up"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.id, "Mekik"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.id, "İp atlama")
        )
        _exercisesRowViewData.value = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.CLOCK.id,
            title = "Egzersiz Geçmişi",
            items = exercisesViewData
        )

        val habitsViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.id, "Düzensiz Uyku"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Fast Food"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.id, "Smoking")
        )
        _habitsRowViewData.value = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.YOGA.id,
            title = "Alışkanlıklar",
            items = habitsViewData
        )
    }

    private fun loadProfileData() {
        _profileData.value = TopBarGroupViewData(
            name = "Dexter Moore",
            social = "@dexteretymo",
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
            preferences = listOf(
                UserProfilePreferenceItem(iconId = "ic_height_outline", "Boy", "175"),
                UserProfilePreferenceItem(iconId = "ic_dna_outline", "Kilo", "75"),
                UserProfilePreferenceItem(iconId = "ic_overweight", "Vücut Tipi", "Ecto")
            ),
            showInfoMini = false
        )
    }

    private fun loadPosts() {
        _posts.value = fakePosts
    }

    private fun loadAppointments() {
        val appointmentsViewData = listOf(
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.id,
                title = "Fonksiyonel hareket ve esneklik geliştirme",
                duration = "60 dakika",
                location = "@ironstudio",
                trainer = "Micheal Blake"
            ),
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
                title = "Kişisel kuvvet antrenmanı",
                duration = "60 dakika",
                location = "@ironstudio",
                trainer = "Micheal Blake"
            )
        )

        _appointmentsColumViewData.value = LessonSessionColumnViewData(
            iconId = SkyFitAsset.SkyFitIcon.EXERCISES.id,
            title = "Randevularım",
            items = appointmentsViewData
        )
    }

    fun updateScroll(scrollValue: Int, firstItemIndex: Int) {
        _scrollValue.value = scrollValue
        _firstVisibleItemIndex.value = firstItemIndex
    }

    fun toggleShowPosts(show: Boolean) {
        _showPosts.value = show
    }
}

data class UserProfilePreferenceItem(
    val iconId: String,
    val title: String,
    val subtitle: String
)

data class TopBarGroupViewData(
    val name: String = "",
    val social: String = "",
    val imageUrl: String = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
    val preferences: List<UserProfilePreferenceItem> = emptyList(),
    val showInfoMini: Boolean = false // Whether to show the mini info card
)