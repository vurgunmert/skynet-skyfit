package com.vurgun.skyfit.feature_dashboard.di

import com.vurgun.skyfit.feature_dashboard.ui.DashboardHomeViewModel
import org.koin.dsl.module

val dashboardModule = module {
    single { DashboardHomeViewModel(get()) }
}