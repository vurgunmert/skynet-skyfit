package com.vurgun.skyfit.feature.courses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.FacilityDetail
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.repository.UserManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class FacilityLessonListUiState(
    val isLoading: Boolean = false,
    val lessons: List<LessonSessionItemViewData> = emptyList(),
    val errorMessage: String? = null
) {
    val isEmpty: Boolean get() = lessons.isEmpty()
    val activeLessons: List<LessonSessionItemViewData> get() = lessons.filter { it.isActive }
    val inactiveLessons: List<LessonSessionItemViewData> get() = lessons.filterNot { it.isActive }
}

class FacilityLessonListViewModel(
    private val courseRepository: CourseRepository,
    private val userManager: UserManager,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ViewModel() {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    private val gymId: Int
        get() = facilityUser.gymId

    private val gymAddress: String
        get() = facilityUser.gymAddress

    private val _uiState = MutableStateFlow(FacilityLessonListUiState())
    val uiState: StateFlow<FacilityLessonListUiState> = _uiState

    private val _editLessonChannel = Channel<Lesson>(Channel.BUFFERED)
    val editLessonFlow = _editLessonChannel.receiveAsFlow()

    private var lessons: List<Lesson> = emptyList()

    fun loadLessonsFor(date: LocalDate) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = courseRepository.getLessonsByFacility(gymId, date.toString(), date.toString())
            result.fold(
                onSuccess = { lessons ->
                    this@FacilityLessonListViewModel.lessons = lessons
                    val mappedLessons = lessons.map { lesson ->
                        lessonMapper.map(lesson, gymAddress)
                    }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            lessons = mappedLessons
                        )
                    }
                },
                onFailure = { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "An unexpected error occurred."
                        )
                    }
                }
            )
        }
    }

    fun navigateToEdit(lessonId: Int) {
        val lesson = lessons.firstOrNull { it.lessonId == lessonId }
        lesson?.let {
            viewModelScope.launch {
                _editLessonChannel.send(it)
            }
        }
    }

    fun toggleLessonStatus(lessonId: Int) {
        // TODO: Call repository to update status
    }

    fun deleteLesson(lessonId: Int) {
        // TODO: Call repository to delete
    }
}
