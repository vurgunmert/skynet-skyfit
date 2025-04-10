package com.vurgun.skyfit.feature.profile.user.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.social.components.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.components.viewdata.fakePosts
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

data class UserProfileUiState(
    val profileData: TopBarGroupViewData = TopBarGroupViewData(),
    val posts: List<SocialPostItemViewData> = emptyList(),
    val appointments: LessonSessionColumnViewData? = null,
    val showPosts: Boolean = true,
    val showInfoMini: Boolean = false,
    val exercises: LifestyleActionRowViewData? = null,
    val habits: LifestyleActionRowViewData? = null,
    val photoDiary: PhotoGalleryStackViewData? = null
)


class SkyFitUserProfileViewModel : ViewModel() {

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
        val profileViewData = TopBarGroupViewData(
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

        val appointmentsViewData = listOf(
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.id,
                title = "Fonksiyonel hareket ve esneklik geliştirme",
                duration = "60 dakika",
                date = "18/11/2024",
                hours = "08:00 - 09:00",
                location = "@ironstudio",
                trainer = "Micheal Blake"
            ),
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
                title = "Kişisel kuvvet antrenmanı",
                date = "18/11/2024",
                hours = "08:00 - 09:00",
                duration = "60 dakika",
                location = "@ironstudio",
                trainer = "Micheal Blake"
            )
        )

        val appointmentsColumnViewData = LessonSessionColumnViewData(
            iconId = SkyFitAsset.SkyFitIcon.EXERCISES.id,
            title = "Randevularım",
            items = appointmentsViewData
        )

        val photoDiaryViewData = PhotoGalleryStackViewData()

        val exercisesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Şınav"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.YOGA.id, "Yoga"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Pull up"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SIT_UP.id, "Mekik"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.JUMPING_ROPE.id, "İp atlama")
        )
        val exercisesRowViewData = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.CLOCK.id,
            title = "Egzersiz Geçmişi",
            items = exercisesViewData
        )

        val habitsViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SLEEP.id, "Düzensiz Uyku"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Fast Food"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.SMOKING.id, "Smoking")
        )
        val habitsRowViewData = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.YOGA.id,
            title = "Alışkanlıklar",
            items = habitsViewData
        )

        loadPosts()

        _uiState.update {
            it.copy(
                profileData = profileViewData,
                appointments = appointmentsColumnViewData,
                exercises = exercisesRowViewData,
                habits = habitsRowViewData,
                photoDiary = photoDiaryViewData
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