package com.vurgun.skyfit.presentation.mobile.features.user.social

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSocialMediaScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserSocialMediaScreenUserProfilesComponent(onNewPost = {
                navigator.jumpAndStay(SkyFitNavigationRoute.UserSocialMediaPostAdd)
            })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserSocialMediaScreenPostsComponent()
        }
    }
}

@Composable
private fun MobileUserSocialMediaScreenUserProfilesComponent(onNewPost: () -> Unit) {
    TodoBox("MobileUserSocialMediaScreenUserProfilesComponent", Modifier.size(398.dp, 92.dp).clickable(onClick = onNewPost))
}

@Composable
private fun MobileUserSocialMediaScreenPostsComponent() {
    TodoBox("MobileUserSocialMediaScreenPostsComponent", Modifier.size(382.dp, 920.dp))
}
