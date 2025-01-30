package com.vurgun.skyfit.presentation.mobile.features.user.messages

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitAvatarCircle
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageBubble
import com.vurgun.skyfit.presentation.shared.components.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.presentation.shared.components.UserCircleAvatarItem
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitConversationViewModel
import com.vurgun.skyfit.utils.keyboardAsState
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserToFacilityChatScreen(navigator: Navigator) {

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
            MobileUserToFacilityChatScreenToolbarComponent(
                facilityName = "SkyFit Studio",
                lastActive = "En son 12:00’de aktifti"
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = SkyFitColor.border.default
            )

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
}

@Composable
private fun MobileUserToFacilityChatScreenToolbarComponent(
    facilityName: String,
    lastActive: String
) {
    val facilityAvatar =
        UserCircleAvatarItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpTzzIb45xy3IfaYLKb4jOMiQOpFNHkya3pg&s")

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        SkyFitAvatarCircle(
            modifier = Modifier.size(48.dp),
            item = facilityAvatar,
            onClick = { }
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = facilityName,
                style = SkyFitTypography.bodyLargeMedium.copy(color = SkyFitColor.text.default)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = lastActive,
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
            )
        }
    }
}

@Composable
private fun MobileUserToFacilityChatScreenMessagesComponent() {
    TodoBox("MobileUserToFacilityChatScreenMessagesComponent", Modifier.size(430.dp, 208.dp))
}

@Composable
private fun MobileUserToFacilityChatScreenInputComponent() {
    TodoBox("MobileUserToFacilityChatScreenInputComponent", Modifier.size(430.dp, 24.dp))
}