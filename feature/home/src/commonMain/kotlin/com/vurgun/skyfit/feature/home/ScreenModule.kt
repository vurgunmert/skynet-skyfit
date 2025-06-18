package com.vurgun.skyfit.feature.home

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.home.component.LessonFilterScreen
import com.vurgun.skyfit.feature.home.screen.HomeScreen

val screenHomeModule = screenModule {
    register<SharedScreen.Home> { HomeScreen() }
    register<SharedScreen.LessonFilter> { LessonFilterScreen(it.lessons as List<LessonSessionItemViewData>, onApply = it.onApply) }
}
