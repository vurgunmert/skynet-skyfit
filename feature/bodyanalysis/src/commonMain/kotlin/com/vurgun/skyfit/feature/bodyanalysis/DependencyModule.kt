package com.vurgun.skyfit.feature.bodyanalysis

import com.vurgun.skyfit.data.bodyanalysis.dataBodyAnalysisModule
import com.vurgun.skyfit.feature.bodyanalysis.screen.PostureAnalysisViewModel
import org.koin.dsl.module

val featurePostureAnalysis = module {
    includes(dataBodyAnalysisModule)

    factory { PostureAnalysisViewModel(get()) }
}