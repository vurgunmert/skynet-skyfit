package com.vurgun.skyfit.feature.profile

import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.feature.profile.facility.viewmodel.FacilityProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureProfileModule = module {
    includes(dataCoursesModule)

    viewModel { FacilityProfileViewModel(get(), get(), get()) }
}