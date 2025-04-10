package com.vurgun.skyfit.data.onboarding

import org.koin.dsl.module

val dataOnboardingModule = module {
    single { OnboardingApiService(get()) }
    single<OnboardingRepository> { OnboardingRepositoryImpl(get(), get(), get()) }
}