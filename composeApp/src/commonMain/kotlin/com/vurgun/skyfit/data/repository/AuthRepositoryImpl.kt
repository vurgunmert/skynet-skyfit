package com.vurgun.skyfit.data.repository

import com.vurgun.skyfit.data.network.models.OTPResponse
import com.vurgun.skyfit.domain.repository.AuthRepository
import com.vurgun.skyfit.domain.usecases.auth.AuthLoginResult
import com.vurgun.skyfit.domain.util.ResultWrapper
import kotlinx.coroutines.delay

class AuthRepositoryImpl : AuthRepository {

    override suspend fun authenticatePhoneNumber(phoneNumber: String): AuthLoginResult {
        delay(2000) // TODO: Replace simulation

        return when {
            phoneNumber.startsWith("+9055") -> AuthLoginResult.AwaitingOTPLogin
            phoneNumber.startsWith("+9054") -> AuthLoginResult.AwaitingOTPLogin
            else -> AuthLoginResult.Error("Desteklenmeyen veya geçersiz telefon numarası.")
        }
    }

    override suspend fun registerUser(phoneNumber: String, fullName: String, password: String): ResultWrapper<Unit> {
        delay(2000) // TODO: Replace simulation

        return if (phoneNumber.startsWith("+90")) {
            ResultWrapper.Success(Unit)
        } else {
            ResultWrapper.Error("Bu telefon numarası desteklenmiyor.")
        }
    }

    override suspend fun verifyOtp(phoneNumber: String, otp: String): ResultWrapper<OTPResponse> {
        delay(2000)  // TODO: Replace simulation
        return if (otp == "1234") {
            ResultWrapper.Success(OTPResponse(registered = true))
        } else if (otp == "4321") {
            ResultWrapper.Success(OTPResponse(registered = false))
        } else {
            ResultWrapper.Error("Geçersiz kod. Lütfen tekrar deneyin.")
        }
    }
    override suspend fun resendOtp(phoneNumber: String): ResultWrapper<Boolean> {
        delay(2000)  // TODO: Replace simulation
        return ResultWrapper.Success(true)
    }
}
