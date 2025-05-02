package com.vurgun.skyfit.feature.courses

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.data.courses.dataCoursesModule
import com.vurgun.skyfit.data.settings.dataSettingsModule
import com.vurgun.skyfit.feature.courses.screen.FacilityLessonEditViewModel
import com.vurgun.skyfit.feature.courses.screen.FacilityLessonListingViewModel
import org.koin.dsl.module

val featureCoursesModule = module {
    includes(dataCoursesModule, dataSettingsModule, dataCoreModule)

    factory { FacilityLessonListingViewModel(get(), get(), get()) }
    factory { FacilityLessonEditViewModel(get(), get(), get(), get()) }
}