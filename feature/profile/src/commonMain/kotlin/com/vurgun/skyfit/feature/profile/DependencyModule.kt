package com.vurgun.skyfit.feature.profile

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.data.settings.dataSettingsModule
import com.vurgun.skyfit.feature.profile.facility.owner.FacilityProfileOwnerViewModel
import com.vurgun.skyfit.feature.profile.facility.schedule.FacilityProfileScheduleViewModel
import com.vurgun.skyfit.feature.profile.facility.visitor.FacilityProfileVisitorViewModel
import com.vurgun.skyfit.feature.profile.trainer.owner.TrainerProfileOwnerViewModel
import com.vurgun.skyfit.feature.profile.trainer.schedule.TrainerProfileScheduleViewModel
import com.vurgun.skyfit.feature.profile.trainer.visitor.TrainerProfileVisitorViewModel
import com.vurgun.skyfit.feature.profile.user.owner.UserProfileOwnerViewModel
import org.koin.dsl.module

val featureProfileModule = module {
    includes(dataCoursesModule, dataCoreModule, dataSettingsModule)

    factory { UserProfileOwnerViewModel(get(), get(), get(), get()) }

    factory { TrainerProfileOwnerViewModel(get(), get(), get(), get()) }
    factory { TrainerProfileVisitorViewModel(get(), get(), get(), get()) }
    factory { TrainerProfileScheduleViewModel(get(), get(), get()) }

    factory { FacilityProfileOwnerViewModel(get(), get(), get(), get()) }
    factory { FacilityProfileVisitorViewModel(get(), get(), get(), get()) }
    factory { FacilityProfileScheduleViewModel(get(), get(), get()) }
}