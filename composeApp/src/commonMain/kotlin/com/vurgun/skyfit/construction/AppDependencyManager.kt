package com.vurgun.skyfit.construction

import com.vurgun.skyfit.data.configuration.dataDependencyModule
import com.vurgun.skyfit.presentation.configuration.presentationDependencyModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

object AppDependencyManager {

    fun loadSkyFitModules() {
        startKoin {
            modules(dataDependencyModule,
                    presentationDependencyModule)
        }
    }
}

fun loadKoinDependencyModules() {
    startKoin {
        modules(dataDependencyModule,
                presentationDependencyModule)
    }
}
