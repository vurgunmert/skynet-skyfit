package com.vurgun.skyfit.feature_appointments.di

import com.vurgun.skyfit.feature_appointments.ui.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature_appointments.ui.UserAppointmentsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appointmentModule = module {
    viewModel { UserAppointmentsViewModel() }
    viewModel { UserAppointmentDetailViewModel() }
}