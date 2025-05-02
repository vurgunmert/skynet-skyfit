package com.vurgun.skyfit.feature.settings

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.data.settings.dataSettingsModule
import com.vurgun.skyfit.feature.settings.facility.member.FacilityAddMembersViewModel
import com.vurgun.skyfit.feature.settings.facility.member.FacilityManageMembersViewModel
import com.vurgun.skyfit.feature.settings.facility.notification.FacilityNotificationSettingsViewModel
import com.vurgun.skyfit.feature.settings.facility.payment.FacilityPaymentHistoryViewModel
import com.vurgun.skyfit.feature.settings.facility.profile.FacilityEditProfileViewModel
import com.vurgun.skyfit.feature.settings.facility.profile.FacilityManageProfileViewModel
import com.vurgun.skyfit.feature.settings.facility.trainer.FacilityAddTrainerViewModel
import com.vurgun.skyfit.feature.settings.facility.trainer.FacilityManageTrainersViewModel
import com.vurgun.skyfit.feature.settings.shared.SettingsHomeViewModel
import com.vurgun.skyfit.feature.settings.shared.account.ManageAccountsViewModel
import com.vurgun.skyfit.feature.settings.shared.changepassword.ChangePasswordViewModel
import com.vurgun.skyfit.feature.settings.trainer.profile.TrainerSettingsEditProfileViewModel
import com.vurgun.skyfit.feature.settings.trainer.profile.TrainerSettingsManageProfileViewModel
import com.vurgun.skyfit.feature.settings.user.profile.UserSettingsManageProfileViewModel
import org.koin.dsl.module

val featureSettingsModule = module {
    includes(dataSettingsModule, dataCoreModule)

    factory { SettingsHomeViewModel(get()) }
    factory { ChangePasswordViewModel() }
    factory { ManageAccountsViewModel(get()) }

    factory { UserSettingsManageProfileViewModel(get()) }

    factory { TrainerSettingsManageProfileViewModel(get(), get()) }
    factory { TrainerSettingsEditProfileViewModel(get(), get()) }

    factory { FacilityManageProfileViewModel(get(), get()) }
    factory { FacilityEditProfileViewModel(get(), get()) }
    factory { FacilityManageMembersViewModel(get()) }
    factory { FacilityAddMembersViewModel(get()) }
    factory { FacilityManageTrainersViewModel(get()) }
    factory { FacilityPaymentHistoryViewModel() }
    factory { FacilityAddTrainerViewModel(get()) }
    factory { FacilityNotificationSettingsViewModel() }
}