package com.vurgun.skyfit.feature.profile.facility.viewmodel

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.TrainerProfileCardItemViewData
import com.vurgun.skyfit.feature.social.components.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.components.viewdata.fakePosts
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class FacilityProfileInfoViewData(
    val backgroundUrl: String,
    val name: String,
    val bio: String,
    val address: String,
    val socialLink: String,
    val memberCount: Int,
    val trainerCount: Int,
    val rating: Double,
    val isFollowed: Boolean = false,
    val isRated: Boolean = false
)

data class FacilityProfileState(
    val infoViewData: FacilityProfileInfoViewData? = null,
    val galleryStackViewData: PhotoGalleryStackViewData? = null,
    val lessonSessionColumnViewData: LessonSessionColumnViewData? = null,
    val trainers: List<TrainerProfileCardItemViewData> = emptyList(),
)

class FacilityProfileViewModel: ViewModel() {

    private val _profileState = MutableStateFlow<FacilityProfileState>(FacilityProfileState())
    val profileState: StateFlow<FacilityProfileState> get() = _profileState

    private val _postsVisible = MutableStateFlow(false)
    val postsVisible: StateFlow<Boolean> get() = _postsVisible

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts: StateFlow<List<SocialPostItemViewData>> get() = _posts

    fun loadData() {
        val infoViewData = FacilityProfileInfoViewData(
            backgroundUrl = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
            name = "Ironstudio",
            bio = "At IronStudio Fitness, we’re all about building strength, confidence, and a community of like-minded individuals. Our expert trainers offer personalized programs in strength training, functional fitness, and overall wellness. Let's forge your fitness together!",
            address = "1425 Maplewood Avenue, Apt 3B, Brookfield, IL 60513, USA",
            socialLink = "@ironstudio",
            memberCount = 2564,
            trainerCount = 15,
            rating = 4.3
        )

        val privateLessonsViewData = listOf(
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.id,
                title = "Shoulders and Abs",
                trainer = "Micheal Blake",
                category = "Group Fitness"
            ),
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.HIGH_INTENSITY_TRAINING.id,
                title = "Reformer Pilates",
                trainer = "Sarah L.",
                category = "Pilates"
            ),
            LessonSessionItemViewData(
                iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
                title = "Fitness",
                trainer = "Sarah L.",
                category = "PT"
            )
        )

        val lessonsColumViewData = LessonSessionColumnViewData(
            iconId = SkyFitAsset.SkyFitIcon.EXERCISES.id,
            title = "Özel Dersler",
            items = privateLessonsViewData
        )

        val galleryStackViewData = PhotoGalleryStackViewData(
            title = "Salonu Keşfet", message = "8 fotoğraf, 1 video", imageUrls = listOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
                "https://ik.imagekit.io/skynet2skyfit/fake_facility_gym.png?updatedAt=1739637015082",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s"
            )
        )

        _profileState.value = FacilityProfileState(
            infoViewData = infoViewData,
            galleryStackViewData = galleryStackViewData,
            lessonSessionColumnViewData = lessonsColumViewData,
            trainers = fakeTrainers
        )

        _posts.value = fakePosts
    }

    fun followFacility() {
        // TODO: ("Not yet implemented")
        _profileState.value = _profileState.value.copy(
            infoViewData = _profileState.value.infoViewData?.copy(isFollowed = true)
        )
    }

    fun unfollowFacility() {
        // TODO: ("Not yet implemented")
        _profileState.value = _profileState.value.copy(
            infoViewData = _profileState.value.infoViewData?.copy(isFollowed = false)
        )
    }

    fun togglePostTab(isVisible: Boolean) {
        _postsVisible.value = isVisible
    }
}

val fakeTrainers = listOf(
    TrainerProfileCardItemViewData("https://basebangkok.com/app/uploads/2021/12/Matt-WP.jpg", "Lucas Bennett", 1800, 13, 32, 4.8),
    TrainerProfileCardItemViewData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTDhR7E86BCe_50BUZK3KU8N2Vafx9hnBy4mzuQ40v1MXYQ5dsTY8Zbn_kZ__Z0L3KBOA&usqp=CAU", "Olivia Hayes", 1500, 10, 20, 4.5),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/856797530/photo/portrait-of-a-beautiful-woman-at-the-gym.jpg?s=612x612&w=0&k=20&c=0wMa1MYxt6HHamjd66d5__XGAKbJFDFQyu9LCloRsYU=", "Mason Reed", 2000, 15, 40, 4.9),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/852401732/photo/happy-personal-trainer-working-at-the-gym.jpg?s=612x612&w=0&k=20&c=m4Wk3lVvjEFIHbiAfUuFNBwEhvvSgf4Vv5ib9JUsrJk=", "Sophia Hill", 1700, 12, 28, 4.7),
    TrainerProfileCardItemViewData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqjbihw0jL4P6DvYHu4fI4oKlGqRSTL_gxjZ6LUoI4ZOU23m1VpHP9G__wigT5_4q-C1c&usqp=CAU", "Emma Johnson", 1600, 11, 25, 4.6),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/1018043738/photo/portrait-of-chinese-personal-trainer-in-gym.jpg?s=612x612&w=0&k=20&c=BpuYao5G3qr5SG7IUq15CESYHxunuvz66qfnDKJfX9s=", "James Smith", 1900, 14, 35, 4.8),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/1018043738/photo/portrait-of-chinese-personal-trainer-in-gym.jpg?s=612x612&w=0&k=20&c=BpuYao5G3qr5SG7IUq15CESYHxunuvz66qfnDKJfX9s=", "Ava Brown", 1750, 13, 30, 4.7)
)