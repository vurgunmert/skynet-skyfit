package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.user.model.SelectUserTypeResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserTypes(): Result<List<UserAccountType>>
    suspend fun selectUserType(typeId: Int): Result<SelectUserTypeResponse>

    val userTypeId: Flow<Int?>

    data object UserTypeIdKey : Storage.Key.IntKey("user_type_id", null)
    data object UserAuthToken : Storage.Key.StringKey("user_auth_token", null)
    data object UserPhoneNumber : Storage.Key.StringKey("user_phone_number", null)

    suspend fun getSecureUserDetails(): Result<BaseUserDetail>
}