package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageBubble
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitConversationViewModel
import com.vurgun.skyfit.utils.keyboardAsState
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileUserToGroupChatScreen(navigator: Navigator) {
    val viewModel: SkyFitConversationViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val keyboardState by keyboardAsState()
    val scrollState = rememberLazyListState()

    // Animate the bottom padding (or offset) based on keyboard height
    val animatedBottomOffset by animateDpAsState(
        targetValue = keyboardState.heightDp,
        animationSpec = tween(
            durationMillis = 600, // Animation duration in milliseconds
            easing = FastOutSlowInEasing // Fancy easing for a smooth transition
        )
    )


    // Automatically scroll to the last message when the messages list changes
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scrollState.animateScrollToItem(index = messages.size - 1)
        }
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserToGroupChatScreenToolbarComponent()
        }
    ) {
        Box {
            // Chat messages list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = keyboardState.heightDp + 64.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = scrollState
            ) {
                items(messages) { message ->
                    SkyFitChatMessageBubble(message)
                }
            }

            // Input component
            SkyFitChatMessageInputComponent(
                modifier = Modifier
                    .padding(bottom = animatedBottomOffset)
                    .align(Alignment.BottomCenter),
                onSend = { userInput ->
                    viewModel.sendMessage(userInput)
                }
            )
        }
    }
}


@Composable
private fun MobileUserToGroupChatScreenToolbarComponent() {
    TodoBox("MobileUserToGroupChatScreenToolbarComponent", Modifier.size(430.dp, 80.dp))
}