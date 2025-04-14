package com.vurgun.skyfit.data.bodyanalysis

import com.vurgun.skyfit.data.bodyanalysis.repository.PostureAnalysisRepository
import org.koin.dsl.module

val dataBodyAnalysisModule = module {
    single { PostureAnalysisRepository() }
}