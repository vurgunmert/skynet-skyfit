package com.vurgun.explore

import com.vurgun.explore.model.ExploreViewModel
import com.vurgun.skyfit.core.data.dataCoreModule
import org.koin.dsl.module

val exploreDependencyModule = module {
    includes(dataCoreModule)

    factory { ExploreViewModel(get()) }
}