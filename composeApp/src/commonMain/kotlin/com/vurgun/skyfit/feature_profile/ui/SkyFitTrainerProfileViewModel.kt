package com.vurgun.skyfit.feature_profile.ui

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.core.ui.resources.SkyFitAsset
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionColumnViewData
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature_profile.ui.user.TopBarGroupViewData
import com.vurgun.skyfit.feature_profile.ui.user.UserProfilePreferenceItem
import com.vurgun.skyfit.feature_social.ui.components.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature_social.ui.components.viewdata.fakePosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SkyFitTrainerProfileViewModel : ViewModel() {

    private val _profileData = MutableStateFlow<TopBarGroupViewData?>(null)
    val profileData: StateFlow<TopBarGroupViewData?> get() = _profileData

    private val _specialtiesRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val specialtiesRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _specialtiesRowViewData

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts = _posts.asStateFlow()

    private val _lessonsColumViewData = MutableStateFlow<LessonSessionColumnViewData?>(null)
    val lessonsColumViewData: StateFlow<LessonSessionColumnViewData?> get() = _lessonsColumViewData

    fun loadData() {
        loadProfileData()
        _posts.value = fakePosts

        val specialtiesViewData = listOf(
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PUSH_UP.id, "Fonksiyonel Antrenman"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id, "Kuvvet ve Kondisyon"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.FAST_FOOD.id, "Beslenme Danışmanlığı"),
            LifestyleActionItemViewData(SkyFitAsset.SkyFitIcon.PULL_UP_BAR.id, "Atletik Performans Geliştirme")
        )

        _specialtiesRowViewData.value = LifestyleActionRowViewData(
            iconId = SkyFitAsset.SkyFitIcon.TROPHY.id, // Medal Icon for Specialties
            title = "Uzmanlık Alanları",
            items = specialtiesViewData,
            iconSizePx = 48
        )

        val privateLessonsViewData = listOf(
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.id,
                title = "Shoulders and Abs",
                hours = "08:00 - 09:00",
                trainer = "Micheal Blake",
                category = "Group Fitness"
            ),
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.HIGH_INTENSITY_TRAINING.id,
                title = "Reformer Pilates",
                hours = "09:00 - 10:00",
                category = "Pilates"
            ),
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
                title = "Fitness",
                hours = "10:00 - 11:00",
                trainer = "Sarah L.",
                category = "PT",
                note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."
            )
        )

        _lessonsColumViewData.value = LessonSessionColumnViewData(
            iconId = SkyFitAsset.SkyFitIcon.EXERCISES.id,
            title = "Özel Dersler",
            items = privateLessonsViewData
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
}