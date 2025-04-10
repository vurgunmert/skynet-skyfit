package com.vurgun.skyfit.data.auth.domain.model

sealed interface AuthLoginResult {
    data object Success : AuthLoginResult
    data object OTPVerificationRequired : AuthLoginResult
    data object OnboardingRequired : AuthLoginResult
    data class Error(val message: String?) : AuthLoginResult
}

sealed class AuthorizationOTPResult {
    data object LoginSuccess : AuthorizationOTPResult()
    data object RegistrationRequired : AuthorizationOTPResult()
    data object OnboardingRequired : AuthorizationOTPResult()
    data class Error(val message: String?) : AuthorizationOTPResult()
}

sealed class CreatePasswordResult {
    data object Success : CreatePasswordResult()
    data class Error(val message: String?) : CreatePasswordResult()
}

sealed class ResetPasswordResult {
    data object Success : ResetPasswordResult()
    data class Error(val message: String?) : ResetPasswordResult()
}

sealed class ForgotPasswordResult {
    data object Success : ForgotPasswordResult()
    data class Error(val message: String?) : ForgotPasswordResult()
}

sealed class ForgotPasswordOTPResult {
    data object Success : ForgotPasswordOTPResult()
    data class Error(val message: String?) : ForgotPasswordOTPResult()
}

sealed class SendOTPResult {
    data object Success : SendOTPResult()
    data class Error(val message: String?) : SendOTPResult()
}