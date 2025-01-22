package com.vurgun.skyfit.presentation.mobile.features.explore

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileExploreBlogArticleDetailScreen(rootNavigator: Navigator) {

    var joined: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileExploreBlogArticleDetailScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(22.dp))
            MobileExploreBlogArticleDetailScreenInfoComponent()
            Spacer(Modifier.height(24.dp))
            MobileExploreBlogArticleDetailScreenContentComponent()
            Spacer(Modifier.height(24.dp))
            MobileExploreBlogFeaturedArticlesComponent()
        }
    }
}

@Composable
private fun MobileExploreBlogArticleDetailScreenToolbarComponent() {
    TodoBox("MobileExploreBlogArticleDetailScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExploreBlogArticleDetailScreenInfoComponent() {
    TodoBox("MobileExploreBlogArticleDetailScreenParticipantBarComponent", Modifier.size(382.dp, 278.dp))
}


@Composable
private fun MobileExploreBlogArticleDetailScreenContentComponent() {
    TodoBox("MobileExploreBlogArticleDetailScreenContentComponent", Modifier.size(382.dp, 1300.dp))
}

@Composable
private fun MobileExploreBlogFeaturedArticlesComponent() {
    TodoBox("ExploreBlogFeaturedArticlesComponent", Modifier.size(430.dp, 264.dp))
}