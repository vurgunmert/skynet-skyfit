package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.model.UserRole
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.model.MissingUserTypeException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserManagerImpl(
    private val repository: UserRepository
) : UserManager {

    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val userFlow = MutableStateFlow<UserDetail?>(null)
    override val user = userFlow.asStateFlow()

    // Derived role state
    override val userRole: StateFlow<UserRole> = userFlow
        .map { it?.userRole ?: UserRole.Guest }
        .stateIn(appScope, SharingStarted.Eagerly, UserRole.Guest)

    private val _accountTypes = MutableStateFlow<List<UserAccountType>>(emptyList())
    override val accountTypes: StateFlow<List<UserAccountType>> = _accountTypes

    override suspend fun getActiveUser(forceRefresh: Boolean): Result<UserDetail> {
        println("🌐 getActiveUser called. forceRefresh = $forceRefresh")

        // Short-circuit if user is already cached
        if (!forceRefresh && userFlow.value != null) {
            println("✅ Returning cached user: ${userFlow.value}")
            return Result.success(userFlow.value!!)
        }

        // Always try to update account types on forced refresh
        println("🔄 Fetching user account types...")
        val userTypesResult = repository.getUserTypes()
        userTypesResult.fold(
            onSuccess = {
                println("✅ Account types fetched: $it")
                _accountTypes.value = it
            },
            onFailure = {
                println("⚠️ Failed to fetch account types: $it")
            }
        )

        // Initial user fetch
        println("👤 Fetching user details...")
        val result = repository.getUserDetails()

        result.fold(
            onSuccess = {
                println("✅ User fetched: $it")
                userFlow.value = it
                return Result.success(it)
            },
            onFailure = { error ->
                println("❌ Initial user fetch failed: $error")

                if (error is MissingUserTypeException) {
                    println("⚠️ Missing user type detected.")

                    // Step 1: Try cached user type
                    val cachedUserTypeId = repository.userTypeId.firstOrNull()
                    println("🔍 Cached userTypeId: $cachedUserTypeId")

                    val selectResult: Result<Boolean> = if (cachedUserTypeId != null) {
                        println("➡️ Trying to select cached userTypeId...")
                        repository.selectUserType(cachedUserTypeId).also {
                            println("✅ Cached userType selection result: $it")
                        }
                    } else {
                        // Step 2: Try fallback from accountTypes
                        val fallbackUserTypeId = _accountTypes.value.firstOrNull()?.typeId
                        println("🔄 Fallback userTypeId from accountTypes: $fallbackUserTypeId")

                        if (fallbackUserTypeId != null) {
                            repository.selectUserType(fallbackUserTypeId).also {
                                println("✅ Fallback userType selection result: $it")
                            }
                        } else {
                            println("❌ No valid userTypeId available to select.")
                            return Result.failure(IllegalStateException("User type ID could not be determined."))
                        }
                    }

                    // Step 3: Verify selection and retry
                    if (selectResult.isFailure || selectResult.getOrNull() != true) {
                        println("❌ Failed to select a valid user type.")
                        return Result.failure(IllegalStateException("Failed to select a valid user type."))
                    }

                    println("🔁 Retrying getUserDetails after successful type selection...")
                    val retryResult = repository.getUserDetails()

                    retryResult.fold(
                        onSuccess = {
                            println("✅ Retry successful: $it")
                            userFlow.value = it
                            return Result.success(it)
                        },
                        onFailure = {
                            println("❌ Retry failed after selecting userType: $it")
                            return Result.failure(it)
                        }
                    )
                } else {
                    println("❌ Unknown error during user fetch: $error")
                    return Result.failure(error)
                }
            }
        )
    }

    override suspend fun updateUserType(userTypeId: Int) {
        val selection = repository.selectUserType(userTypeId)
        if (selection.isSuccess) {
            val result = getActiveUser(forceRefresh = true)
            result.getOrNull()?.let {
                userFlow.value = it // ensure flow triggers
            } ?: println("Failed to fetch user after type change")
        } else {
            println("User type selection failed.")
        }
    }
}
