package com.vurgun.skyfit.feature.social.screen

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.feature.social.viewdata.SocialPostItemViewData
import com.vurgun.skyfit.feature.social.viewdata.fakePosts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSocialMediaViewModel : ViewModel() {

    private val _posts = MutableStateFlow<List<SocialPostItemViewData>>(fakePosts)
    val posts = _posts.asStateFlow()
}
