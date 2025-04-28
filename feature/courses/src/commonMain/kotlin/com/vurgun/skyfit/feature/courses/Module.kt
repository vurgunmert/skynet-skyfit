package com.vurgun.skyfit.feature.courses

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.data.settings.dataSettingsModule
import com.vurgun.skyfit.feature.courses.screen.FacilityEditLessonViewModel
import com.vurgun.skyfit.feature.courses.screen.FacilityLessonListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureCoursesModule = module {
    includes(dataCoursesModule, dataSettingsModule, dataCoreModule)

    viewModel { FacilityLessonListViewModel(get(), get(), get()) }
    viewModel { FacilityEditLessonViewModel(get(), get(), get(), get()) }
}