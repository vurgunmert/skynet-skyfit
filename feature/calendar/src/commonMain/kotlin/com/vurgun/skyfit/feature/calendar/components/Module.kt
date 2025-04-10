package com.vurgun.skyfit.feature.calendar.components

import com.vurgun.skyfit.feature.calendar.components.screen.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.components.screen.UserAppointmentsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureCalendarModule = module {
    viewModel { UserAppointmentsViewModel() }
    viewModel { UserAppointmentDetailViewModel() }
}