package com.vurgun.skyfit

import com.vurgun.skyfit.data.core.dataCoreModule
import com.vurgun.skyfit.data.network.dataNetworkModule
import com.vurgun.skyfit.data.user.dataUserModule
import com.vurgun.skyfit.feature.auth.featureAuthModule
import com.vurgun.skyfit.feature.calendar.components.featureCalendarModule
import com.vurgun.skyfit.feature.home.featureHomeModule
import com.vurgun.skyfit.feature.messaging.featureMessagingModule
import com.vurgun.skyfit.feature.notification.featureNotificationModule
import com.vurgun.skyfit.feature.onboarding.featureOnboardingModule
import com.vurgun.skyfit.feature.profile.featureProfileModule
import com.vurgun.skyfit.feature.settings.featureSettingsModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataNetworkModule,
        dataCoreModule,
        dataUserModule,

        featureAuthModule,
        featureHomeModule,
        featureOnboardingModule,
        featureSettingsModule,
        featureMessagingModule,
        featureNotificationModule,
        featureCalendarModule,
        featureProfileModule
    )
}