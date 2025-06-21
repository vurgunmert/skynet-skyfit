package com.vurgun.skyfit.analytics

import org.koin.dsl.module

val dependencyOnboardingModule = module {
    single { AnalyticsClient() }
}