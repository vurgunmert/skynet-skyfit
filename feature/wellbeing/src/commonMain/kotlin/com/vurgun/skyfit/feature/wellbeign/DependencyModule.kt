package com.vurgun.skyfit.feature.wellbeign

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.wellbeign.posture.PostureAnalysisViewModel
import org.koin.dsl.module

val featureWellbeingModule = module {
    includes(dataCoreModule)

    factory { PostureAnalysisViewModel(get()) }
}