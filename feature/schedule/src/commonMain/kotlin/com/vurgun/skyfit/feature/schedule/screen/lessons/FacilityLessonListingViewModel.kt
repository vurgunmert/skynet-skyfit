package com.vurgun.skyfit.feature.schedule.screen.lessons

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.account.model.FacilityAccount
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
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

sealed class FacilityLessonListingAction {
    data object NavigateToBack : FacilityLessonListingAction()
    data object NavigateToNewLesson : FacilityLessonListingAction()
    data class NavigateToEditLesson(val lessonId: Int) : FacilityLessonListingAction()
    data class DeactivateLesson(val lessonId: Int) : FacilityLessonListingAction()
    data class ActivateLesson(val lessonId: Int) : FacilityLessonListingAction()
    data class DeleteLesson(val lessonId: Int) : FacilityLessonListingAction()
}

sealed class FacilityLessonListingEffect {
    data object NavigateToBack : FacilityLessonListingEffect()
    data object NavigateToNewLesson : FacilityLessonListingEffect()
    data class NavigateToEditLesson(val lesson: Lesson) : FacilityLessonListingEffect()
}

class FacilityLessonListingViewModel(
    private val facilityRepository: FacilityRepository,
    private val userManager: ActiveAccountManager,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val facilityUser: FacilityAccount
        get() = userManager.account.value as? FacilityAccount
            ?: error("User is not a Facility")

    private val gymId: Int
        get() = facilityUser.gymId

    private val _uiState = MutableStateFlow(FacilityLessonListUiState())
    val uiState: StateFlow<FacilityLessonListUiState> = _uiState

    private val _effect = SingleSharedFlow<FacilityLessonListingEffect>()
    val effect: SharedFlow<FacilityLessonListingEffect> = _effect

    private var lessons: List<Lesson> = emptyList()
    private var currentDate: LocalDate? = null

    fun onAction(action: FacilityLessonListingAction) {
        when (action) {
            FacilityLessonListingAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, FacilityLessonListingEffect.NavigateToBack)

            is FacilityLessonListingAction.NavigateToEditLesson ->
                navigateToEdit(action.lessonId)

            FacilityLessonListingAction.NavigateToNewLesson ->
                _effect.emitIn(screenModelScope, FacilityLessonListingEffect.NavigateToNewLesson)

            is FacilityLessonListingAction.ActivateLesson -> activateLesson(action.lessonId)
            is FacilityLessonListingAction.DeactivateLesson -> deactivateLesson(action.lessonId)
            is FacilityLessonListingAction.DeleteLesson -> deleteLesson(action.lessonId)
        }
    }

    fun loadLessonsFor(date: LocalDate) {
        currentDate = date

        screenModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = facilityRepository.getActiveLessonsByFacility(gymId, date.formatToServerDate(), date.formatToServerDate())
            result.fold(
                onSuccess = { lessons ->
                    this@FacilityLessonListingViewModel.lessons = lessons
                    val mappedLessons = lessons.map { lesson ->
                        lessonMapper.map(lesson)
                    }
                    _uiState.update { it.copy(isLoading = false, lessons = mappedLessons) }
                },
                onFailure = { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "An unexpected error occurred.",
                            lessons = emptyList()
                        )
                    }
                }
            )
        }
    }

    private fun refreshLessons() {
        val date = currentDate ?: return
        loadLessonsFor(date)
    }

    private fun navigateToEdit(lessonId: Int) {
        val lesson = lessons.firstOrNull { it.lessonId == lessonId }
        lesson?.let {
            screenModelScope.launch {
                _effect.emitOrNull(FacilityLessonListingEffect.NavigateToEditLesson(it))
            }
        }
    }

    private fun deactivateLesson(lessonId: Int) {
        screenModelScope.launch {
            facilityRepository.deactivateLesson(lessonId).fold(
                onSuccess = { refreshLessons() },
                onFailure = { showError(it) }
            )
        }
    }

    private fun activateLesson(lessonId: Int) {
        screenModelScope.launch {
            facilityRepository.activateLesson(lessonId).fold(
                onSuccess = { refreshLessons() },
                onFailure = { showError(it) }
            )
        }
    }

    private fun deleteLesson(lessonId: Int) {
        screenModelScope.launch {
            facilityRepository.deleteLesson(lessonId).fold(
                onSuccess = { refreshLessons() },
                onFailure = { showError(it) }
            )
        }
    }

    private fun showError(throwable: Throwable) {
        _uiState.update {
            it.copy(errorMessage = throwable.message ?: "Bir hata oluştu")
        }
    }
}
