package com.vurgun.skyfit.feature.home

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.home.model.FacilityHomeViewModel
import com.vurgun.skyfit.feature.home.model.TrainerHomeViewModel
import com.vurgun.skyfit.feature.home.model.UserHomeViewModel
import org.koin.dsl.module

val homeDependencyModule = module {
    includes(dataCoreModule)

    factory { FacilityHomeViewModel(get(), get(), get()) }
    factory { TrainerHomeViewModel(get(), get(), get()) }
    factory { UserHomeViewModel(get(), get(), get()) }
}