package com.vurgun.skyfit.feature.persona

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.core.network.dataNetworkModule
import com.vurgun.skyfit.feature.persona.profile.facility.owner.FacilityProfileOwnerViewModel
import com.vurgun.skyfit.feature.persona.profile.facility.schedule.FacilityProfileScheduleViewModel
import com.vurgun.skyfit.feature.persona.profile.facility.visitor.FacilityProfileVisitorViewModel
import com.vurgun.skyfit.feature.persona.profile.trainer.owner.TrainerProfileOwnerViewModel
import com.vurgun.skyfit.feature.persona.profile.trainer.schedule.TrainerProfileScheduleViewModel
import com.vurgun.skyfit.feature.persona.profile.trainer.visitor.TrainerProfileVisitorViewModel
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.member.FacilityAddMembersViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.member.FacilityManageMembersViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.notification.FacilityNotificationSettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.packages.FacilityPackageEditViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.packages.FacilityPackageListingViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.payment.FacilityPaymentHistoryViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.profile.FacilityEditProfileViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.profile.FacilityManageProfileViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.trainer.FacilityAddTrainerViewModel
import com.vurgun.skyfit.feature.persona.settings.facility.trainer.FacilityManageTrainersViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.account.ManageAccountsViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.changepassword.ChangePasswordViewModel
import com.vurgun.skyfit.feature.persona.settings.trainer.notification.TrainerNotificationSettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.trainer.profile.TrainerSettingsEditProfileViewModel
import com.vurgun.skyfit.feature.persona.settings.trainer.profile.TrainerSettingsManageProfileViewModel
import com.vurgun.skyfit.feature.persona.settings.user.notification.UserNotificationSettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.user.profile.UserProfileSettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.user.account.UserAccountSettingsViewModel
import com.vurgun.skyfit.feature.persona.social.SocialMediaViewModel
import org.koin.dsl.module

val featurePersonaModule = module {
    includes(dataNetworkModule, dataCoreModule)

    factory { SettingsViewModel(get()) }
    factory { ChangePasswordViewModel() }
    factory { ManageAccountsViewModel(get()) }

    factory { UserAccountSettingsViewModel(get(), get()) }
    factory { UserProfileSettingsViewModel(get(), get(), get()) }

    factory { TrainerSettingsManageProfileViewModel(get(), get()) }
    factory { TrainerSettingsEditProfileViewModel(get(), get(), get(), get()) }

    factory { FacilityManageProfileViewModel(get(), get()) }
    factory { FacilityEditProfileViewModel(get(), get(), get(), get()) }
    factory { FacilityManageMembersViewModel(get(), get()) }
    factory { FacilityAddMembersViewModel(get(), get()) }
    factory { FacilityManageTrainersViewModel(get(), get()) }
    factory { FacilityPaymentHistoryViewModel() }
    factory { FacilityAddTrainerViewModel(get(), get()) }
    factory { FacilityNotificationSettingsViewModel() }
    factory { FacilityPackageListingViewModel(get(), get()) }
    factory { FacilityPackageEditViewModel(get(), get(), get()) }

    factory { UserProfileViewModel(get(), get(), get(), get()) }

    factory { TrainerProfileOwnerViewModel(get(), get(), get()) }
    factory { TrainerProfileVisitorViewModel(get(), get(), get()) }
    factory { TrainerProfileScheduleViewModel(get(), get(), get()) }
    factory { TrainerNotificationSettingsViewModel() }

    factory { FacilityProfileOwnerViewModel(get(), get(), get()) }
    factory { FacilityProfileVisitorViewModel(get(), get(), get()) }
    factory { FacilityProfileScheduleViewModel(get(), get(), get()) }


    factory { SocialMediaViewModel() }
    factory { UserNotificationSettingsViewModel() }
}