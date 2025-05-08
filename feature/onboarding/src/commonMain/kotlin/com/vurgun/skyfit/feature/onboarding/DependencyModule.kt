package com.vurgun.skyfit.feature.onboarding

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.data.onboarding.dataOnboardingModule
import com.vurgun.skyfit.feature.onboarding.screen.OnboardingViewModel
import org.koin.dsl.module

val featureOnboardingModule = module {
    includes(dataOnboardingModule, dataCoreModule)

    factory { OnboardingViewModel(get(), get(), get()) }
}