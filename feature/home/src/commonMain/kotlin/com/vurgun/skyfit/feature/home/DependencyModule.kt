package com.vurgun.skyfit.feature.home

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.feature.home.screen.FacilityHomeViewModel
import com.vurgun.skyfit.feature.home.screen.TrainerHomeViewModel
import com.vurgun.skyfit.feature.home.screen.UserHomeViewModel
import org.koin.dsl.module

val featureHomeModule = module {
    includes(dataCoreModule, dataCoursesModule)

    factory { UserHomeViewModel(get(), get()) }
    factory { FacilityHomeViewModel(get(), get(), get()) }
    factory { TrainerHomeViewModel(get(), get(), get()) }
}