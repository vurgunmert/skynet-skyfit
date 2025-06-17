package com.vurgun.skyfit.feature.connect.social

import cafe.adriel.voyager.core.model.ScreenModel
import com.vurgun.skyfit.core.data.v1.domain.profile.SocialPostItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialMediaViewModel : ScreenModel {

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(fakePosts)
    val posts = _posts.asStateFlow()
}
