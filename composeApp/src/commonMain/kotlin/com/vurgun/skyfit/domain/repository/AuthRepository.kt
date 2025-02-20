package com.vurgun.skyfit.domain.repository

import com.vurgun.skyfit.domain.util.ResultWrapper

interface AuthRepository {
    suspend fun authenticatePhoneNumber(phoneNumber: String): ResultWrapper<Boolean>
}
