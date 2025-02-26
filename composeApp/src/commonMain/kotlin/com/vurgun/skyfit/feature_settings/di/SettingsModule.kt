package com.vurgun.skyfit.feature_settings.di

import com.vurgun.skyfit.feature_settings.ui.trainer.SkyFitTrainerAccountSettingsViewModel
import com.vurgun.skyfit.feature_settings.ui.user.SkyFitUserAccountSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
    viewModel { SkyFitUserAccountSettingsViewModel() }
    viewModel { SkyFitTrainerAccountSettingsViewModel() }
}