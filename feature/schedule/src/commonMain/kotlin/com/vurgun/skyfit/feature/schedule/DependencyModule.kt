package com.vurgun.skyfit.feature.schedule

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.schedule.screen.FacilityLessonEditViewModel
import com.vurgun.skyfit.feature.schedule.screen.FacilityLessonListingViewModel
import com.vurgun.skyfit.feature.schedule.screen.activitycalendar.UserActivityCalendarSearchViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.TrainerAppointmentDetailViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.TrainerAppointmentListingViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature.schedule.screen.appointments.UserAppointmentListingViewModel
import org.koin.dsl.module

val featureScheduleModule = module {
    includes(dataCoreModule)

    factory { UserAppointmentListingViewModel(get(), get()) }
    factory { TrainerAppointmentListingViewModel(get(), get()) }
    factory { UserAppointmentDetailViewModel(get()) }
    factory { TrainerAppointmentDetailViewModel(get()) }
    factory { UserActivityCalendarSearchViewModel() }

    factory { UserAppointmentListingViewModel(get(), get()) }
    factory { TrainerAppointmentListingViewModel(get(), get()) }
    factory { UserAppointmentDetailViewModel(get()) }
    factory { TrainerAppointmentDetailViewModel(get()) }
    factory { UserActivityCalendarSearchViewModel() }

    factory { FacilityLessonListingViewModel(get(), get(), get()) }
    factory { FacilityLessonEditViewModel(get(), get(), get(), get()) }
}