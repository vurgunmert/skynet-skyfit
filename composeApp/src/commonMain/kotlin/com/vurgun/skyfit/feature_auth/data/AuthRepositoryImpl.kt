package com.vurgun.skyfit.feature_auth.data

import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl(private val settingsStore: LocalSettingsStore) : AuthRepository {

    override suspend fun register(phoneNumber: String, fullName: String, password: String): NetworkResponseWrapper<Unit> {
        delay(2000) // TODO: Replace simulation

        return if (phoneNumber.startsWith("+90")) {
            NetworkResponseWrapper.Success(data = Unit)
        } else {
            NetworkResponseWrapper.Error(message = "Bu telefon numarası desteklenmiyor.")
        }
    }

    override suspend fun login(phoneNumber: String, otp: String?): NetworkResponseWrapper<SignInResponse> {
        delay(2000)  // TODO: Replace simulation
        return if (otp == "1234") {
            settingsStore.saveToken("123456")
            NetworkResponseWrapper.Success(data = SignInResponse(registered = true))
        } else if (otp == "4321") {
            NetworkResponseWrapper.Success(data = SignInResponse(registered = false))
        } else {
            NetworkResponseWrapper.Error(message = "Geçersiz kod. Lütfen tekrar deneyin.")
        }
    }

    override suspend fun resendOtpCode(phoneNumber: String): NetworkResponseWrapper<Boolean> {
        delay(2000)  // TODO: Replace simulation
        return NetworkResponseWrapper.Success(data = true)
    }
}
