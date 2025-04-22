package com.vurgun.skyfit.feature.calendar.components

import com.vurgun.skyfit.data.user.dataUserModule
import com.vurgun.skyfit.feature.calendar.components.screen.TrainerAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.components.screen.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.components.screen.UserAppointmentsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureCalendarModule = module {
    includes(dataUserModule)

    viewModel { UserAppointmentsViewModel(get(), get()) }
    viewModel { UserAppointmentDetailViewModel(get()) }
    viewModel { TrainerAppointmentDetailViewModel(get()) }
}