package com.vurgun.skyfit.feature.social.screen

import cafe.adriel.voyager.core.model.ScreenModel
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.viewdata.fakePosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialMediaViewModel : ScreenModel {

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(fakePosts)
    val posts = _posts.asStateFlow()
}
