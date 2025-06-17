package com.vurgun.skyfit.settings

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.core.network.dataNetworkModule
import com.vurgun.skyfit.settings.facility.member.FacilityAddMembersViewModel
import com.vurgun.skyfit.settings.facility.member.FacilityManageMembersViewModel
import com.vurgun.skyfit.settings.facility.notification.FacilityNotificationSettingsViewModel
import com.vurgun.skyfit.settings.facility.packages.FacilityPackageEditViewModel
import com.vurgun.skyfit.settings.facility.packages.FacilityPackageListingViewModel
import com.vurgun.skyfit.settings.facility.payment.FacilityPaymentHistoryViewModel
import com.vurgun.skyfit.settings.facility.profile.FacilityProfileSettingsViewModel
import com.vurgun.skyfit.settings.facility.account.FacilityAccountSettingsViewModel
import com.vurgun.skyfit.settings.facility.trainer.FacilityAddTrainerViewModel
import com.vurgun.skyfit.settings.facility.trainer.FacilityManageTrainersViewModel
import com.vurgun.skyfit.settings.shared.SettingsViewModel
import com.vurgun.skyfit.settings.shared.account.AccountRoleSettingsViewModel
import com.vurgun.skyfit.settings.shared.changepassword.PasswordSettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.trainer.notification.TrainerNotificationSettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.trainer.profile.TrainerSettingsEditProfileViewModel
import com.vurgun.skyfit.feature.persona.settings.trainer.profile.TrainerSettingsManageProfileViewModel
import com.vurgun.skyfit.settings.user.account.UserAccountSettingsViewModel
import com.vurgun.skyfit.settings.user.notification.UserNotificationSettingsViewModel
import com.vurgun.skyfit.settings.user.profile.UserProfileSettingsViewModel
import org.koin.dsl.module

val dataPersonaModule = module {
    includes(dataNetworkModule, dataCoreModule)

    factory { SettingsViewModel(get()) }
    factory { PasswordSettingsViewModel() }
    factory { AccountRoleSettingsViewModel(get()) }

    factory { UserAccountSettingsViewModel(get(), get()) }
    factory { UserProfileSettingsViewModel(get(), get(), get()) }

    factory { TrainerSettingsManageProfileViewModel(get(), get()) }
    factory { TrainerSettingsEditProfileViewModel(get(), get(), get(), get()) }

    factory { FacilityAccountSettingsViewModel(get(), get()) }
    factory { FacilityProfileSettingsViewModel(get(), get(), get(), get()) }
    factory { FacilityManageMembersViewModel(get(), get()) }
    factory { FacilityAddMembersViewModel(get(), get()) }
    factory { FacilityManageTrainersViewModel(get(), get()) }
    factory { FacilityPaymentHistoryViewModel() }
    factory { FacilityAddTrainerViewModel(get(), get()) }
    factory { FacilityNotificationSettingsViewModel() }
    factory { FacilityPackageListingViewModel(get(), get()) }
    factory { FacilityPackageEditViewModel(get(), get(), get()) }

    factory { TrainerNotificationSettingsViewModel() }

    factory { UserNotificationSettingsViewModel() }
}