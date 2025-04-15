package com.vurgun.skyfit.feature.courses

import com.vurgun.skyfit.data.courses.dataCoursesModule
import org.koin.dsl.module

val featureCoursesModule = module {
    includes(dataCoursesModule)


}