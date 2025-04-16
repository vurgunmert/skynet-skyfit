package com.vurgun.skyfit.feature.profile.facility.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.TrainerProfileCardItemViewData
import com.vurgun.skyfit.feature.social.components.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.components.viewdata.fakePosts
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

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

class FacilityProfileViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val lessonSessionItemViewDataMapper: LessonSessionItemViewDataMapper
) : ViewModel() {

    private val _profileState = MutableStateFlow<FacilityProfileState>(FacilityProfileState())
    val profileState: StateFlow<FacilityProfileState> get() = _profileState

    private val _postsVisible = MutableStateFlow(false)
    val postsVisible: StateFlow<Boolean> get() = _postsVisible

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(emptyList())
    val posts: StateFlow<List<SocialPostItemViewData>> get() = _posts

    fun loadData() {

        _posts.value = fakePosts

        val user = userManager.user.value

        val infoViewData = FacilityProfileInfoViewData(
            backgroundUrl = user?.backgroundImageUrl.orEmpty(),
            name = user?.gymName.orEmpty(),
            bio = user?.bio.orEmpty(),
            address = user?.gymAddress.orEmpty(),
            socialLink = "@ironstudio",
            memberCount = 0,
            trainerCount = 0,
            rating = 5.0
        )

        val galleryStackViewData = PhotoGalleryStackViewData(
            title = "Salonu Keşfet", message = "8 fotoğraf, 1 video", imageUrls = listOf(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
                "https://ik.imagekit.io/skynet2skyfit/fake_facility_gym.png?updatedAt=1739637015082",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s"
            )
        )


        viewModelScope.launch {

            val gymId = userManager.user.value?.gymId
            //TODO: Fail if not available gymId

            val items = courseRepository.getLessons(gymId!!, LocalDate.now().toString(), null)
                .map { lessons ->
                    lessons
                        .sortedBy { it.startDateTime }
                        .take(5)
                        .map {
                        lessonSessionItemViewDataMapper.map(it, user?.gymAddress)
                    }
                }.getOrElse { emptyList() }

            val lessonsColumViewData = LessonSessionColumnViewData(
                iconId = SkyFitAsset.SkyFitIcon.EXERCISES.resId,
                title = "Özel Dersler",
                items = items
            )

            _profileState.value = FacilityProfileState(
                infoViewData = infoViewData,
                galleryStackViewData = galleryStackViewData,
                lessonSessionColumnViewData = lessonsColumViewData,
                trainers = fakeTrainers
            )
        }
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
    TrainerProfileCardItemViewData(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTDhR7E86BCe_50BUZK3KU8N2Vafx9hnBy4mzuQ40v1MXYQ5dsTY8Zbn_kZ__Z0L3KBOA&usqp=CAU",
        "Olivia Hayes",
        1500,
        10,
        20,
        4.5
    ),
    TrainerProfileCardItemViewData(
        "https://media.istockphoto.com/id/856797530/photo/portrait-of-a-beautiful-woman-at-the-gym.jpg?s=612x612&w=0&k=20&c=0wMa1MYxt6HHamjd66d5__XGAKbJFDFQyu9LCloRsYU=",
        "Mason Reed",
        2000,
        15,
        40,
        4.9
    ),
    TrainerProfileCardItemViewData(
        "https://media.istockphoto.com/id/852401732/photo/happy-personal-trainer-working-at-the-gym.jpg?s=612x612&w=0&k=20&c=m4Wk3lVvjEFIHbiAfUuFNBwEhvvSgf4Vv5ib9JUsrJk=",
        "Sophia Hill",
        1700,
        12,
        28,
        4.7
    ),
    TrainerProfileCardItemViewData(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqjbihw0jL4P6DvYHu4fI4oKlGqRSTL_gxjZ6LUoI4ZOU23m1VpHP9G__wigT5_4q-C1c&usqp=CAU",
        "Emma Johnson",
        1600,
        11,
        25,
        4.6
    ),
    TrainerProfileCardItemViewData(
        "https://media.istockphoto.com/id/1018043738/photo/portrait-of-chinese-personal-trainer-in-gym.jpg?s=612x612&w=0&k=20&c=BpuYao5G3qr5SG7IUq15CESYHxunuvz66qfnDKJfX9s=",
        "James Smith",
        1900,
        14,
        35,
        4.8
    ),
    TrainerProfileCardItemViewData(
        "https://media.istockphoto.com/id/1018043738/photo/portrait-of-chinese-personal-trainer-in-gym.jpg?s=612x612&w=0&k=20&c=BpuYao5G3qr5SG7IUq15CESYHxunuvz66qfnDKJfX9s=",
        "Ava Brown",
        1750,
        13,
        30,
        4.7
    )
)