package com.vurgun.skyfit.data.courses

import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.repository.CourseRepositoryImpl
import org.koin.dsl.module

val dataCoursesModule = module {

//    includes(dataCoreModule)

    single<CourseApiService> { CourseApiService(get()) }
    single<LessonSessionItemViewDataMapper> { LessonSessionItemViewDataMapper() }
    single<CourseRepository> { CourseRepositoryImpl(get(), get(), get()) }
}