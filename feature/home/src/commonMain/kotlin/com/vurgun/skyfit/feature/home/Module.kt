package com.vurgun.skyfit.feature.home

import com.vurgun.skyfit.data.core.dataCoreModule
import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.feature.home.screen.FacilityHomeViewModel
import com.vurgun.skyfit.feature.home.screen.TrainerHomeViewModel
import com.vurgun.skyfit.feature.home.screen.UserHomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {
    includes(dataCoreModule, dataCoursesModule)

    viewModel { UserHomeViewModel(get(), get()) }
    viewModel { FacilityHomeViewModel(get(), get(), get()) }
    viewModel { TrainerHomeViewModel(get(), get(), get()) }
}