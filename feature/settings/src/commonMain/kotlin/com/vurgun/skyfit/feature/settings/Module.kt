package com.vurgun.skyfit.feature.settings

import com.vurgun.skyfit.data.settings.dataSettingsModule
import com.vurgun.skyfit.feature.settings.changepassword.ChangePasswordViewModel
import com.vurgun.skyfit.feature.settings.facility.FacilityAddMembersViewModel
import com.vurgun.skyfit.feature.settings.facility.FacilityManageMembersViewModel
import com.vurgun.skyfit.feature.settings.trainer.TrainerAccountSettingsViewModel
import com.vurgun.skyfit.feature.settings.user.SkyFitUserAccountSettingsViewModel
import com.vurgun.skyfit.feature.settings.user.UserSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureSettingsModule = module {
    includes(dataSettingsModule)

    viewModel { FacilityManageMembersViewModel(get()) }
    viewModel { FacilityAddMembersViewModel(get()) }
    viewModel { SkyFitUserAccountSettingsViewModel() }
    viewModel { TrainerAccountSettingsViewModel() }
    viewModel { UserSettingsViewModel(get()) }
    viewModel { ChangePasswordViewModel() }
}