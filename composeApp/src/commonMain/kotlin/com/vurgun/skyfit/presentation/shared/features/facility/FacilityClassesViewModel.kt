package com.vurgun.skyfit.presentation.shared.features.facility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitClassCalendarCardItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FacilityClassesViewModel : ViewModel() {

    private val _allClasses = MutableStateFlow(generateSampleClasses())
    private val _activeClasses = MutableStateFlow<List<SkyFitClassCalendarCardItem>>(emptyList())
    private val _inactiveClasses = MutableStateFlow<List<SkyFitClassCalendarCardItem>>(emptyList())

    val activeClasses: StateFlow<List<SkyFitClassCalendarCardItem>> = _activeClasses
    val inactiveClasses: StateFlow<List<SkyFitClassCalendarCardItem>> = _inactiveClasses

    init {
        filterClasses()
    }

    private fun filterClasses() {
        viewModelScope.launch {
            _allClasses.collect { classes ->
                _activeClasses.value = classes.filter { it.enabled }
                _inactiveClasses.value = classes.filterNot { it.enabled }
            }
        }
    }

    fun toggleClassStatus(classId: String) {
        viewModelScope.launch {
            _allClasses.value = _allClasses.value.map {
                if (it.classId == classId) it.copy(enabled = !it.enabled) else it
            }
            filterClasses()
        }
    }

    fun addNewClass(classItem: SkyFitClassCalendarCardItem) {
        _allClasses.value = _allClasses.value + classItem
        filterClasses()
    }

    fun deleteClass(classId: String) {
        _allClasses.value = _allClasses.value.filterNot { it.classId == classId }
        filterClasses()
    }
}

fun generateSampleClasses(): List<SkyFitClassCalendarCardItem> {
    return listOf(
        SkyFitClassCalendarCardItem(
            title = "Morning Yoga",
            hours = "08:00 - 09:00",
            category = "Yoga",
            trainer = "Alice Johnson",
            note = "Start your day with mindful movements and deep breathing.",
            enabled = true,
            iconId = "ic_yoga"
        ),
        SkyFitClassCalendarCardItem(
            title = "HIIT Burn",
            hours = "09:30 - 10:15",
            category = "HIIT",
            trainer = "Mark Evans",
            note = "Intense full-body workout, expect sweat and results.",
            enabled = true,
            iconId = "ic_push_up"
        ),
        SkyFitClassCalendarCardItem(
            title = "Pilates Core",
            hours = "11:00 - 12:00",
            category = "Pilates",
            trainer = "Michael Blake",
            note = "Improve core strength and posture.",
            enabled = true,
            iconId = "ic_push_up"
        ),
        SkyFitClassCalendarCardItem(
            title = "Zumba Energy",
            hours = "12:30 - 13:30",
            category = "Zumba",
            trainer = "Sophie Martinez",
            note = "Dance and sweat with Latin-inspired beats!",
            enabled = true,
            iconId = "ic_sit_up"
        ),
        SkyFitClassCalendarCardItem(
            title = "Reformer Pilates",
            hours = "15:00 - 16:00",
            category = "Pilates",
            trainer = "Michael Blake",
            enabled = true,
            iconId = "ic_pilates"
        ),
        SkyFitClassCalendarCardItem(
            title = "Functional Training",
            hours = "16:30 - 17:30",
            category = "Strength",
            trainer = "Jack Thomson",
            note = "Train movements, not muscles!",
            enabled = false,
            iconId = "ic_jumping_rope"
        ),
        SkyFitClassCalendarCardItem(
            title = "Kickboxing Basics",
            hours = "18:00 - 19:00",
            category = "Kickboxing",
            trainer = "Daniel Adams",
            note = "Learn the fundamentals of kickboxing.",
            enabled = true,
            iconId = "ic_jumping_rope"
        ),
        SkyFitClassCalendarCardItem(
            title = "Evening Stretch",
            hours = "19:30 - 20:00",
            category = "Flexibility",
            trainer = "Emma Wilson",
            note = "Relax and recover with deep stretching.",
            enabled = false,
            iconId = "ic_jumping_rope️"
        ),
        SkyFitClassCalendarCardItem(
            title = "Strength & Conditioning",
            hours = "20:30 - 21:30",
            category = "Strength",
            trainer = "Olivia Carter",
            enabled = true,
            iconId = "ic_yoga"
        ),
        SkyFitClassCalendarCardItem(
            title = "Midnight Meditation",
            hours = "23:00 - 23:45",
            category = "Meditation",
            trainer = "Nathan Richards",
            note = "Calm your mind before sleep.",
            enabled = false,
            iconId = "ic_jumping_rope"
        )
    )
}
