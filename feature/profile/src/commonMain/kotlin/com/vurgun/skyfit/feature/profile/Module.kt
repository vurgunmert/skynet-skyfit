package com.vurgun.skyfit.feature.profile

import com.vurgun.skyfit.feature.profile.facility.viewmodel.FacilityProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureProfileModule = module {
    //TODO: ACTUALLY NEED USER DATA MODULE
    viewModel { FacilityProfileViewModel(get(), get()) }
}