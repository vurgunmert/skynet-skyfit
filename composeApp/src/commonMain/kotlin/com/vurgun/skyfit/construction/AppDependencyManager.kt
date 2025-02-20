package com.vurgun.skyfit.construction

import com.vurgun.skyfit.data.configuration.dataDependencyModule
import com.vurgun.skyfit.di.authModule
import com.vurgun.skyfit.presentation.configuration.presentationDependencyModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

object AppDependencyManager {

    fun loadSkyFitModules() {
        startKoin {
            modules(authModule,
                    dataDependencyModule,
                    presentationDependencyModule)
        }
    }
}

fun loadKoinDependencyModules() {
    startKoin {
        modules(authModule,
            dataDependencyModule,
            presentationDependencyModule)
    }
}
