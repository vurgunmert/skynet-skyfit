package com.vurgun.skyfit.onboarding

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.onboarding.model.OnboardingViewModel
import org.koin.dsl.module

val dataOnboardingModule = module {
    includes(dataCoreModule)

    factory { OnboardingViewModel(get(), get(), get(), get()) }
}