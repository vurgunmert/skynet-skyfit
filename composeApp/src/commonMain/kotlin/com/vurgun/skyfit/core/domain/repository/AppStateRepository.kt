package com.vurgun.skyfit.core.domain.repository

import com.vurgun.skyfit.core.domain.model.AppState

interface AppStateRepository {
    suspend fun getAppState(): AppState
}

class AppStateRepositoryImpl() : AppStateRepository {
    override suspend fun getAppState(): AppState {
        return AppState.NORMAL
    }
}
