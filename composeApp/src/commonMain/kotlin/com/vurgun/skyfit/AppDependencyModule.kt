package com.vurgun.skyfit

import com.vurgun.skyfit.feature.access.featureAccessModule
import com.vurgun.skyfit.feature.connect.featureConnectModule
import com.vurgun.skyfit.feature.dashboard.featureDashboardModule
import com.vurgun.skyfit.feature.persona.featurePersonaModule
import com.vurgun.skyfit.feature.schedule.featureScheduleModule
import com.vurgun.skyfit.feature.wellbeign.featureWellbeingModule
import org.koin.dsl.module

val appModule = module {
    includes(
        featureAccessModule,
        featureConnectModule,
        featureDashboardModule,
        featurePersonaModule,
        featureScheduleModule,
        featureWellbeingModule
    )
}