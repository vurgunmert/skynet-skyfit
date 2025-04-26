package com.vurgun.skyfit.feature.calendar

import com.vurgun.skyfit.data.user.dataUserModule
import com.vurgun.skyfit.feature.calendar.screen.TrainerAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.screen.UserAppointmentDetailViewModel
import com.vurgun.skyfit.feature.calendar.screen.UserAppointmentListingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureCalendarModule = module {
    includes(dataUserModule)

    viewModel { UserAppointmentListingViewModel(get(), get()) }
    viewModel { UserAppointmentDetailViewModel(get()) }
    viewModel { TrainerAppointmentDetailViewModel(get()) }
}