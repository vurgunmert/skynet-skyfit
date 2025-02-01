package com.vurgun.skyfit.presentation.mobile.features.user.messages

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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitCircularImageComponent
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitChatMessageBubble
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.presentation.shared.components.UserCircleAvatarItem
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
fun MobileUserToUserChatScreen(navigator: Navigator) {

    val viewModel: SkyFitConversationViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()
    val keyboardState by keyboardAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserToUserChatScreenToolbarComponent(
                participantName = "Olvia Witha",
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
                modifier = Modifier.padding(bottom = keyboardState.heightDp).align(Alignment.BottomCenter),
                onSend = { userInput -> viewModel.sendMessage(userInput) }
            )
        }
    }
}

@Composable
private fun MobileUserToUserChatScreenToolbarComponent(
    participantName: String,
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
