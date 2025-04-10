package com.vurgun.skyfit.data.settings.repository

import com.vurgun.skyfit.data.settings.model.Member

interface SettingsRepository {
    suspend fun addGymUser(gymId: Int, userId: Int): Result<Boolean>
    suspend fun getGymMembers(gymId: Int): Result<List<Member>>
    suspend fun deleteGymMember(gymId: Int, userId: Int): Result<Boolean>
    suspend fun getPlatformMembers(gymId: Int): Result<List<Member>>

    suspend fun addGymTrainer(gymId: Int, userId: Int): Result<Boolean>
    suspend fun getGymTrainers(gymId: Int): Result<List<Member>>
    suspend fun deleteGymTrainer(gymId: Int, userId: Int): Result<Boolean>
    suspend fun getPlatformTrainers(): Result<List<Member>>
}

