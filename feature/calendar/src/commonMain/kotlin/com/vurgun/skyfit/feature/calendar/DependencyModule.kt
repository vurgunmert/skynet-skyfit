package com.vurgun.skyfit.feature.calendar

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.calendar.screen.activitycalendar.UserActivityCalendarSearchViewModel
import com.vurgun.skyfit.feature.calendar.screen.appointments.TrainerAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.screen.appointments.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.screen.appointments.UserAppointmentListingViewModel
import org.koin.dsl.module

val featureCalendarModule = module {
    includes(dataCoreModule)

    factory { UserAppointmentListingViewModel(get(), get()) }
    factory { UserAppointmentDetailViewModel(get()) }
    factory { TrainerAppointmentDetailViewModel(get()) }
    factory { UserActivityCalendarSearchViewModel() }
}