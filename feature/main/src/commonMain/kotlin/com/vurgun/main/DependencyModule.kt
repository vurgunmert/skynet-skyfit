package com.vurgun.main

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.main.dashboard.DashboardViewModel
import org.koin.dsl.module

val dataMainModule = module {
    includes(dataCoreModule)

    single { DashboardViewModel(get()) }
}