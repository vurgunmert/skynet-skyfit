package com.vurgun.skyfit.feature_onboarding.di

import androidx.lifecycle.SavedStateHandle
import com.vurgun.skyfit.feature_onboarding.data.repository.OnboardingRepositoryImpl
import com.vurgun.skyfit.feature_onboarding.data.service.OnboardingApiService
import com.vurgun.skyfit.feature_onboarding.domain.repository.OnboardingRepository
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.OnboardingViewModel
import org.koin.dsl.module

val onboardingModule = module {
    single { OnboardingApiService(get()) }

    single<OnboardingRepository> { OnboardingRepositoryImpl(get(), get(), get()) }

    factory { (handle: SavedStateHandle) -> OnboardingViewModel(get(), handle) }
}