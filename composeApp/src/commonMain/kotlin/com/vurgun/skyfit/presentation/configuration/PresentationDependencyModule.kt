package com.vurgun.skyfit.presentation.configuration

import com.vurgun.skyfit.presentation.shared.features.auth.SplashViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitConversationViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitUserAccountSettingsViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.UserAppointmentDetailViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.UserAppointmentsViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.UserNotificationsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationDependencyModule = module {

    //ViewModel
//    viewModel { SplashViewModel(get()) }
    viewModelOf(::SplashViewModel)
    viewModel { ChatbotViewModel(get()) }
    viewModel { SkyFitConversationViewModel() }
    viewModel { UserNotificationsViewModel() }
    viewModel { UserAppointmentsViewModel() }
    viewModel { UserAppointmentDetailViewModel() }
    viewModel { SkyFitUserAccountSettingsViewModel() }
}