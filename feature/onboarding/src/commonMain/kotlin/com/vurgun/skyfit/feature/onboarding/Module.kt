package com.vurgun.skyfit.feature.onboarding

import com.vurgun.skyfit.data.onboarding.dataOnboardingModule
import com.vurgun.skyfit.feature.onboarding.screen.OnboardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureOnboardingModule = module {
    includes(dataOnboardingModule)

    viewModel { OnboardingViewModel(get()) }
}