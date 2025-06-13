package com.vurgun.skyfit

import com.vurgun.explore.exploreDependencyModule
import com.vurgun.main.mainDependencyModule
import com.vurgun.skyfit.feature.access.featureAccessModule
import com.vurgun.skyfit.feature.connect.featureConnectModule
import com.vurgun.skyfit.feature.home.homeDependencyModule
import com.vurgun.skyfit.feature.persona.featurePersonaModule
import com.vurgun.skyfit.feature.schedule.featureScheduleModule
import com.vurgun.skyfit.feature.wellbeign.featureWellbeingModule
import org.koin.dsl.module

val appModule = module {
    includes(
        featureAccessModule,
        featureConnectModule,
        mainDependencyModule,
        homeDependencyModule,
        exploreDependencyModule,
        featurePersonaModule,
        featureScheduleModule,
        featureWellbeingModule
    )
}