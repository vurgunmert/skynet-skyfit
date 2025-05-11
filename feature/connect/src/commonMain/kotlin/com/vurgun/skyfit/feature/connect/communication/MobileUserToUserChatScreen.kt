package com.vurgun.skyfit.feature.connect.communication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitCircularImageComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import com.vurgun.skyfit.feature.messaging.component.SkyFitChatMessageBubble
import com.vurgun.skyfit.feature.messaging.component.SkyFitChatMessageInputComponent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

class UserToUserChatScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ChatViewModel>()

        MobileUserToUserChatScreen(
            goToBack = { navigator.pop() }
        )
    }
}


@Composable
private fun MobileUserToUserChatScreen(
    goToBack: () -> Unit
) {

    val viewModel: ChatViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val keyboardState by keyboardAsState()

    SkyFitMobileScaffold(
        topBar = {
            MobileUserToUserChatScreenToolbarComponent(
                onClickBack = goToBack,
                participantName = "Olvia Lorael",
                lastActive = "2 hours ago"
            )
        }
    ) {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(messages) { message ->
                    SkyFitChatMessageBubble(message)
                }
            }

            SkyFitChatMessageInputComponent(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .padding(bottom = keyboardState.heightDp),
                onSend = { userInput -> viewModel.sendMessage(userInput) }
            )
        }
    }
}

@Composable
private fun MobileUserToUserChatScreenToolbarComponent(
    onClickBack: () -> Unit,
    participantName: String,
    lastActive: String
) {
    val facilityAvatar =
        UserCircleAvatarItem("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpTzzIb45xy3IfaYLKb4jOMiQOpFNHkya3pg&s")

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = onClickBack) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Back",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(Modifier.width(24.dp))

        SkyFitCircularImageComponent(
            modifier = Modifier.size(48.dp),
            item = facilityAvatar,
            onClick = { }
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                text = participantName,
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
