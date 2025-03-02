package com.vurgun.skyfit.feature_profile.ui

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.core.ui.resources.SkyFitAsset
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionColumnViewData
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.LifestyleActionRowViewData
import com.vurgun.skyfit.feature_profile.ui.user.TopBarGroupViewData
import com.vurgun.skyfit.feature_profile.ui.user.UserProfilePreferenceItem
import com.vurgun.skyfit.feature_social.ui.PostViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SkyFitTrainerProfileViewModel : ViewModel() {

    private val _profileData = MutableStateFlow<TopBarGroupViewData?>(null)
    val profileData: StateFlow<TopBarGroupViewData?> get() = _profileData

    private val _specialtiesRowViewData = MutableStateFlow<LifestyleActionRowViewData?>(null)
    val specialtiesRowViewData: StateFlow<LifestyleActionRowViewData?> get() = _specialtiesRowViewData

    private val _posts = MutableStateFlow<List<PostViewData>>(emptyList())
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

val fakePosts: List<PostViewData> = List(6) { index ->
    PostViewData(
        postId = "post_${index + 1}",
        username = listOf("JohnDoe", "FitnessQueen", "MikeTrainer", "EmmaRunner", "DavidGym", "SophiaYoga").random(),
        socialLink = listOf("https://instagram.com/user", "https://twitter.com/user", "https://linkedin.com/user", null).random(),
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