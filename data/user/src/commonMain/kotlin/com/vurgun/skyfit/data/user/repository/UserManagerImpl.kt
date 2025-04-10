package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class UserManagerImpl(
    private val repository: UserRepository
) : UserManager {

    private val userFlow = MutableStateFlow<UserDetail?>(null)

    override suspend fun getActiveUser(forceRefresh: Boolean): Result<UserDetail> {
        if (!forceRefresh && userFlow.value != null) {
            return Result.success(userFlow.value!!)
        }

        return repository.getUserDetails().also { result ->
            result.getOrNull()?.let { userFlow.value = it }
        }
    }

    override fun observeUser(): Flow<UserDetail?> = userFlow
}
