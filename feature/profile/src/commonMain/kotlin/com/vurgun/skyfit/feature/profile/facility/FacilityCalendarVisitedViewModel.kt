package com.vurgun.skyfit.feature.profile.facility

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.courses.model.LessonSessionColumnViewData
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

sealed class FacilityCalendarVisitedEvent {
    data object GoToSessionDetail : FacilityCalendarVisitedEvent()
}

class FacilityCalendarVisitedViewModel : ViewModel() {

    private val _isBookingEnabled = MutableStateFlow(false)
    val isBookingEnabled: StateFlow<Boolean> get() = _isBookingEnabled

    private val _navigationEvent = MutableStateFlow<FacilityCalendarVisitedEvent?>(null)
    val navigationEvent: StateFlow<FacilityCalendarVisitedEvent?> get() = _navigationEvent

    private val _lessonsColumnViewData = MutableStateFlow<LessonSessionColumnViewData?>(null)
    val lessonsColumnViewData: StateFlow<LessonSessionColumnViewData?> get() = _lessonsColumnViewData

    private val _selectedSessionId = MutableStateFlow<String?>(null)
    val selectedSessionId: StateFlow<String?> get() = _selectedSessionId

    val userBookedSessionIds = listOf("123", "1234", "12345") // User's booked sessions

    fun loadData(date: LocalDate) {
//        val privateLessonsViewData = listOf(
//            LessonSessionItemViewData(
//                iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.resId,
//                title = "Shoulders and Abs",
//                trainer = "Micheal Blake",
//                category = "Group Fitness",
//                hours = "08:00 - 09:00",
//                enrolledCount = 2,
//                maxCapacity = 4,
//                note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
//                lessonId = "4444"
//            ),
//            LessonSessionItemViewData(
//                iconId = SkyFitAsset.SkyFitIcon.HIGH_INTENSITY_TRAINING.resId,
//                title = "Reformer Pilates",
//                trainer = "Sarah L.",
//                category = "Pilates",
//                hours = "08:00 - 09:00",
//                enrolledCount = 3,
//                maxCapacity = 3,
//                lessonId = "55555"
//            ),
//            LessonSessionItemViewData(
//                iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.resId,
//                title = "Fitness",
//                trainer = "Sarah L.",
//                category = "PT",
//                hours = "08:00 - 09:00",
//                enrolledCount = 1,
//                maxCapacity = 2,
//                lessonId = "12345"
//            )
//        ).map { lesson ->
//            lesson.copy(isActive = lesson.isBooked(userBookedSessionIds) || (lesson.enrolledCount ?: 0) < (lesson.maxCapacity ?: 0))
//        }
//
//        _lessonsColumnViewData.value = LessonSessionColumnViewData(
//            iconId = SkyFitAsset.SkyFitIcon.EXERCISES.resId,
//            title = "Özel Ders Seç",
//            items = privateLessonsViewData
//        )
    }

    fun handleClassSelection(selectedItem: LessonSessionItemViewData) {
        if (selectedItem.isBooked(userBookedSessionIds)) {
            _navigationEvent.value = FacilityCalendarVisitedEvent.GoToSessionDetail
        } else {
            toggleSelection(selectedItem)
        }
    }

    private fun toggleSelection(selectedItem: LessonSessionItemViewData) {
        // 🔥 Prevent selection if the session is disabled
        if (!selectedItem.isActive) return

//        _selectedSessionId.value = if (_selectedSessionId.value == selectedItem.lessonId) {
//            null // 🔥 Deselect if already selected
//        } else {
//            selectedItem.lessonId // 🔥 Set new selection
//        }

//        _lessonsColumnViewData.update { currentData ->
//            currentData?.copy(
//                items = currentData.items.map { lesson ->
//                    lesson.copy(selected = lesson.lessonId == _selectedSessionId.value)
//                }
//            )
//        }

        _isBookingEnabled.value = _selectedSessionId.value != null
    }

}
