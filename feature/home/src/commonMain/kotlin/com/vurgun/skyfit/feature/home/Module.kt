package com.vurgun.skyfit.feature.home

import com.vurgun.skyfit.feature.home.screen.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureHomeModule = module {

    viewModel { HomeViewModel(get()) }
}