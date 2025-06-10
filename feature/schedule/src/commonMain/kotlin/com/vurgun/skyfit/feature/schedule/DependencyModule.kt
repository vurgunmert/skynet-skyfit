package com.vurgun.skyfit.feature.schedule

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.schedule.screen.activitycalendar.*
import com.vurgun.skyfit.feature.schedule.screen.appointments.TrainerAppointmentDetailViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.TrainerAppointmentListingViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentListingViewModel
import com.vurgun.skyfit.feature.schedule.screen.lessons.FacilityLessonEditViewModel
import com.vurgun.skyfit.feature.schedule.screen.lessons.FacilityLessonListingViewModel
import org.koin.dsl.module

val featureScheduleModule = module {
    includes(dataCoreModule)

    factory { UserAppointmentListingViewModel(get(), get()) }
    factory { TrainerAppointmentListingViewModel(get(), get()) }
    factory { UserAppointmentDetailViewModel(get()) }
    factory { TrainerAppointmentDetailViewModel(get(), get()) }
    factory { UserActivityCalendarSearchViewModel(get(), get()) }
    factory { UserActivityCalendarViewModel(get()) }
    factory { CalendarWorkoutEditViewModel() }
    factory { CalendarWorkoutEditDurationViewModel() }
    factory { CalendarWorkoutEditConfirmViewModel(get()) }

    factory { FacilityLessonListingViewModel(get(), get(), get()) }
    factory { FacilityLessonEditViewModel(get(), get(), get()) }
}