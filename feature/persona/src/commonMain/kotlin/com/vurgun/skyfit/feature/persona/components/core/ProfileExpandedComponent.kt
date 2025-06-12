package com.vurgun.skyfit.feature.persona.components.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.topbar.ExpandedTopBar

internal object ProfileExpandedComponent {

    @Composable
    fun Layout(
        modifier: Modifier = Modifier,
        isOwner: Boolean = true,
        topBar: @Composable (() -> Unit) = {},
        ownerHeader: @Composable (() -> Unit) = {},
        visitorHeader: @Composable (() -> Unit) = {},
        ownerContent: @Composable (() -> Unit) = {},
        visitorContent: @Composable (() -> Unit) = {}
    ) {
        Column(modifier = modifier) {
            topBar()

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                if (isOwner) {
                    ownerHeader()
                    ownerContent()
                } else {
                    visitorHeader()
                    visitorContent()
                }
            }
        }
    }


    @Composable
    fun TopBar(
        modifier: Modifier = Modifier,
        profileName: String,
        profileContent: @Composable (() -> Unit)? = {},
        onNavigate: (ScreenProvider) -> Unit = {}
    ) {
        ExpandedTopBar.DefaultTopBar(
            modifier = modifier,
            editorialTitle = "Merhaba $profileName!",
            editorialSubtitle = "Bugünün meydan okumalarına hazır mısın?",
            onClickNotifications = { onNavigate(SharedScreen.Notifications) },
            onClickConversations = { onNavigate(SharedScreen.Conversations) },
            onClickChatBot = { onNavigate(SharedScreen.ChatBot) },
            extraEndContent = profileContent
        )
    }

}