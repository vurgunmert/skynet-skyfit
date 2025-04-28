package com.vurgun.skyfit.feature.dashboard

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.dashboard.screen.DashboardViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureDashboardModule = module {
    includes(dataCoreModule)

    viewModel { DashboardViewModel(get()) }
}