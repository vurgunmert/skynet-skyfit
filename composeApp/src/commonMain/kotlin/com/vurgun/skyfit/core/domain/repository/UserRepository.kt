package com.vurgun.skyfit.core.domain.repository

import com.vurgun.skyfit.core.domain.model.User
import com.vurgun.skyfit.core.domain.model.UserType
import com.vurgun.skyfit.core.storage.LocalSettingsStore

interface UserRepository {
    suspend fun getUser(token: String): User?
    suspend fun clearUserData()
}

class UserRepositoryImpl(
    private val localStorage: LocalSettingsStore
) : UserRepository {

    override suspend fun getUser(token: String): User? { //TODO: Integrate Auth API service
        return User(
            userId = "123456",
            otpCode = "1234",
            username = "firstUser",
            role = UserType.User
        )
    }

    override suspend fun clearUserData() {
        localStorage.clearToken()
    }
}