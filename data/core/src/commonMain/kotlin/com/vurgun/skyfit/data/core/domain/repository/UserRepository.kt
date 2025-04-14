package com.vurgun.skyfit.data.core.domain.repository

import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.storage.Storage
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserDetails(): Result<UserDetail>
    suspend fun getUserTypes(): Result<List<UserAccountType>>
    suspend fun selectUserType(typeId: Int): Result<Boolean>

    val userTypeId: Flow<Int?>
    data object UserTypeIdKey : Storage.Key.IntKey("user_type_id", null)
    data object UserAuthToken : Storage.Key.StringKey("user_auth_token", null)
    data object UserPhoneNumber: Storage.Key.StringKey("user_phone_number", null)
}