package com.vurgun.skyfit

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.auth.featureAuthModule
import com.vurgun.skyfit.feature.bodyanalysis.featurePostureAnalysis
import com.vurgun.skyfit.feature.calendar.featureCalendarModule
import com.vurgun.skyfit.feature.courses.featureCoursesModule
import com.vurgun.skyfit.feature.dashboard.featureDashboardModule
import com.vurgun.skyfit.feature.home.featureHomeModule
import com.vurgun.skyfit.feature.onboarding.featureOnboardingModule
import com.vurgun.skyfit.feature.profile.featureProfileModule
import com.vurgun.skyfit.feature.settings.featureSettingsModule
import com.vurgun.skyfit.feature.splash.featureSplashModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataCoreModule,

        featureSplashModule,
        featureAuthModule,
        featureOnboardingModule,
        featureDashboardModule,
        featureHomeModule, //TODO: Probably move to dashboard
        featureSettingsModule,
        featureCoursesModule,
        featureCalendarModule,
        featureProfileModule,
        featurePostureAnalysis
    )
}