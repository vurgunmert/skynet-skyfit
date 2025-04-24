package com.vurgun.skyfit.feature.profile

import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.data.user.dataUserModule
import com.vurgun.skyfit.feature.profile.facility.viewmodel.FacilityProfileOwnerViewModel
import com.vurgun.skyfit.feature.profile.facility.viewmodel.FacilityProfileVisitorViewModel
import com.vurgun.skyfit.feature.profile.trainer.TrainerProfileOwnerViewModel
import com.vurgun.skyfit.feature.profile.user.UserProfileOwnerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureProfileModule = module {
    includes(dataCoursesModule, dataUserModule)

    viewModel { TrainerProfileOwnerViewModel(get(), get(), get(), get()) }
    viewModel { UserProfileOwnerViewModel(get(), get(), get()) }

    viewModel { FacilityProfileOwnerViewModel(get(), get(), get(), get()) }
    viewModel { FacilityProfileVisitorViewModel(get(), get(), get(), get()) }
}