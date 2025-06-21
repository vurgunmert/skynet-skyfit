package com.vurgun.skyfit.health

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.health.posture.PostureAnalysisViewModel
import org.koin.dsl.module

val dataHealthModule = module {
    includes(dataCoreModule)

    factory { PostureAnalysisViewModel(get()) }
}