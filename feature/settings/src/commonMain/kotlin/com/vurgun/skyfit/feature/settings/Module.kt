package com.vurgun.skyfit.feature.settings

import com.vurgun.skyfit.data.settings.dataSettingsModule
import com.vurgun.skyfit.feature.settings.changepassword.ChangePasswordViewModel
import com.vurgun.skyfit.feature.settings.facility.member.FacilityAddMembersViewModel
import com.vurgun.skyfit.feature.settings.facility.member.FacilityManageMembersViewModel
import com.vurgun.skyfit.feature.settings.facility.trainer.FacilityAddTrainerViewModel
import com.vurgun.skyfit.feature.settings.facility.trainer.FacilityManageTrainersViewModel
import com.vurgun.skyfit.feature.settings.trainer.TrainerAccountSettingsViewModel
import com.vurgun.skyfit.feature.settings.user.SettingsHomeViewModel
import com.vurgun.skyfit.feature.settings.user.SkyFitUserAccountSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureSettingsModule = module {
    includes(dataSettingsModule)

    viewModel { SkyFitUserAccountSettingsViewModel() }
    viewModel { TrainerAccountSettingsViewModel() }
    viewModel { SettingsHomeViewModel(get(), get()) }
    viewModel { ChangePasswordViewModel() }

    viewModel { FacilityManageMembersViewModel(get()) }
    viewModel { FacilityAddMembersViewModel(get()) }
    viewModel { FacilityManageTrainersViewModel(get()) }
    viewModel { FacilityAddTrainerViewModel(get()) }
}