package com.vurgun.skyfit.module

import com.vurgun.explore.dataExploreModule
import com.vurgun.main.dataMainModule
import com.vurgun.skyfit.feature.access.dataAuthModule
import com.vurgun.skyfit.feature.connect.dataConnectModule
import com.vurgun.skyfit.feature.home.dataHomeModule
import com.vurgun.skyfit.feature.schedule.dataScheduleModule
import com.vurgun.skyfit.health.dataHealthModule
import com.vurgun.skyfit.onboarding.dataOnboardingModule
import com.vurgun.skyfit.profile.dataProfileModule
import com.vurgun.skyfit.settings.dataPersonaModule
import org.koin.dsl.module

internal val appDataModule = module {
    includes(
        dataAuthModule,
        dataOnboardingModule,
        dataConnectModule,
        dataMainModule,
        dataHomeModule,
        dataExploreModule,
        dataProfileModule,
        dataPersonaModule,
        dataScheduleModule,
        dataHealthModule
    )
}