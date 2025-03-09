package com.vurgun.skyfit.feature_profile.di

import com.vurgun.skyfit.feature_profile.ui.facility.viewmodel.FacilityProfileViewModel
import org.koin.dsl.module

val profileModule = module {
    factory { FacilityProfileViewModel() }
}