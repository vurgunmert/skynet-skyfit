package com.vurgun.skyfit.feature_auth.data

import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.feature_auth.data.service.AuthApiService
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl(
    private val settingsStore: LocalSettingsStore,
    private val apiService: AuthApiService
) : AuthRepository {

    override suspend fun register(phoneNumber: String, fullName: String, password: String): NetworkResponseWrapper<Unit> {
        delay(2000) // TODO: Replace simulation

        return if (phoneNumber.startsWith("+90")) {
            settingsStore.saveToken("123456")
            NetworkResponseWrapper.Success(data = Unit)
        } else {
            NetworkResponseWrapper.Error(message = "Bu telefon numarası desteklenmiyor.")
        }
    }

    override suspend fun login(phoneNumber: String, password: String): NetworkResponseWrapper<SignInResponse> {

        //TODO: Consume apiService
        return if (phoneNumber.startsWith("+90") && password.isNotBlank()) {
            settingsStore.saveToken("123456")
            NetworkResponseWrapper.Success(data = SignInResponse(verified = true, registered = true))
        } else {
            NetworkResponseWrapper.Error(message = "Giris bilgilerinizi kontrol edip tekrar deneyin.")
        }
    }

    override suspend fun requestOtpCode(phoneNumber: String): NetworkResponseWrapper<Boolean> {
        delay(1000)  // TODO: Replace simulation
        return NetworkResponseWrapper.Success(data = true)
    }

    override suspend fun verifyOtpCode(phoneNumber: String, code: String): NetworkResponseWrapper<SignInResponse> {
        delay(2000)  // TODO: Replace simulation
        return if (code == "1234") {
            settingsStore.saveToken("123456")
            NetworkResponseWrapper.Success(data = SignInResponse(verified = true, registered = true))
        } else if (code == "4321") {
            NetworkResponseWrapper.Success(data = SignInResponse(verified = true, registered = false))
        } else {
            NetworkResponseWrapper.Error(message = "Geçersiz kod. Lütfen tekrar deneyin.")
        }
    }
}
