package com.vurgun.skyfit.profile

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.profile.facility.owner.FacilityProfileViewModel
import com.vurgun.skyfit.profile.facility.schedule.FacilityProfileScheduleViewModel
import com.vurgun.skyfit.profile.facility.visitor.FacilityProfileVisitorViewModel
import com.vurgun.skyfit.profile.trainer.owner.TrainerProfileOwnerViewModel
import com.vurgun.skyfit.profile.trainer.schedule.TrainerProfileScheduleViewModel
import com.vurgun.skyfit.profile.trainer.visitor.TrainerProfileVisitorViewModel
import com.vurgun.skyfit.profile.user.owner.UserProfileViewModel
import org.koin.dsl.module

val dataProfileModule = module {
    includes(dataCoreModule)

    factory { UserProfileViewModel(get(), get(), get(), get()) }

    factory { TrainerProfileOwnerViewModel(get(), get(), get()) }
    factory { TrainerProfileVisitorViewModel(get(), get(), get()) }
    factory { TrainerProfileScheduleViewModel(get(), get(), get()) }

    factory { FacilityProfileViewModel(get(), get(), get()) }
    factory { FacilityProfileVisitorViewModel(get(), get(), get()) }
    factory { FacilityProfileScheduleViewModel(get(), get(), get()) }

}