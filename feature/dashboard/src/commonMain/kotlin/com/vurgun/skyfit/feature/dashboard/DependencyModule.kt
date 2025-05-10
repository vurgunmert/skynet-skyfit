package com.vurgun.skyfit.feature.dashboard

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.dashboard.dashboard.DashboardViewModel
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeViewModel
import com.vurgun.skyfit.feature.dashboard.home.TrainerHomeViewModel
import com.vurgun.skyfit.feature.dashboard.home.UserHomeViewModel
import org.koin.dsl.module

val featureDashboardModule = module {
    includes(dataCoreModule)

    factory { UserHomeViewModel(get(), get(), get()) }
    factory { FacilityHomeViewModel(get(), get(), get(), get()) }
    factory { TrainerHomeViewModel(get(), get(), get(), get()) }

    factory { DashboardViewModel(get()) }
}