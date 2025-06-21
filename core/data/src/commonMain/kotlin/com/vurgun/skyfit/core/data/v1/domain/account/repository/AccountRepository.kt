package com.vurgun.skyfit.core.data.v1.domain.account.repository

import com.vurgun.skyfit.core.data.v1.data.account.model.SelectActiveAccountTypeResponseDTO
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingResult
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingFormData
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType

interface AccountRepository {
    suspend fun submitOnboarding(request: AccountOnboardingFormData, isAccountAddition: Boolean): AccountOnboardingResult
    suspend fun getRegisteredAccountTypes(): Result<List<AccountType>>
    suspend fun getAccountDetails(): Result<Account>
    suspend fun selectActiveAccountType(typeId: Int): Result<SelectActiveAccountTypeResponseDTO>
    suspend fun changePassword(old: String, new: String, again: String): Result<Unit>
}