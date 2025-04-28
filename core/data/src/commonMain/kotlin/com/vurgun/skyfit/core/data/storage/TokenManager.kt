package com.vurgun.skyfit.core.data.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TokenManager(
    private val storage: Storage,
    scope: CoroutineScope
) {
    private val mutex = Mutex()
    private val ready = MutableStateFlow(false)

    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState: StateFlow<String?> = _tokenState.asStateFlow()

    init {
        scope.launch {
            val saved = storage.get(AuthToken)
            println("🔑 [TokenManager:init] token from storage: $saved")
            _tokenState.value = saved
            ready.value = true
        }
    }

    suspend fun setToken(token: String?) {
        mutex.withLock {
            println("📝 [TokenManager:setToken] locking token: $token")
            _tokenState.value = token
            storage.writeValue(AuthToken, token)
            ready.value = token != null
        }
    }

    suspend fun waitUntilTokenReady() {
        ready.filter { it }.first()
    }

    fun getTokenOrNull(): String? {
        println("👀 [TokenManager:getTokenOrNull] value: ${_tokenState.value}")
        return _tokenState.value
    }

    fun getTokenOrThrow(): String {
        val value = _tokenState.value
        println("🚨 [TokenManager:getTokenOrThrow] value: $value")
        return value ?: throw IllegalStateException("No token available")
    }

    suspend fun clear() {
        println("❌ [TokenManager:clear] clearing token")
        setToken(null)
    }

    data object AuthToken : Storage.Key.StringKey("user_auth_token", null)
}