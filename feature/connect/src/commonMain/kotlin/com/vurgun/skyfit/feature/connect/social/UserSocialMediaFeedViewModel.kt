package com.vurgun.skyfit.feature.connect.social

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import com.vurgun.skyfit.core.data.v1.domain.social.repository.SocialMediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserSocialMediaFeedViewModel(
    private val accountManager: ActiveAccountManager,
    private val socialMediaRepository: SocialMediaRepository
) : ScreenModel {

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(fakePosts)
    val posts = _posts.asStateFlow()
    var activeUserId: Int = 0
    var activeTypeId: Int = 0

    fun loadData(userId: Int? = null) {
        activeUserId = userId ?: accountManager.account.value?.userId ?: return
        activeTypeId = accountManager.accountRole.value.typeId

        screenModelScope.launch {
            runCatching {
                val posts = socialMediaRepository.getPostsByUser(activeUserId, activeUserId)
                    .getOrDefault(emptyList())



            }
        }
    }
}
