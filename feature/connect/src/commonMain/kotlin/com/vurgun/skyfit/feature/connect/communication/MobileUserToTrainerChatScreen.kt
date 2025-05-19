package com.vurgun.skyfit.feature.connect.communication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageBubble
import com.vurgun.skyfit.feature.connect.component.SkyFitChatMessageInputComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitCircularImageComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

@Composable
fun MobileUserToTrainerChatScreen(goToBack: () -> Unit) {
    val viewModel: ChatViewModel = koinInject()
    val messages by viewModel.messages.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            MobileUserToTrainerChatScreenToolbarComponent(
                trainerName = "Olvia Witha",
                lastActive = "2 hours ago"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Allows list to take available space
                    .fillMaxWidth()
                    .consumeWindowInsets(WindowInsets.ime) // Prevents layout shifting
                    .windowInsetsPadding(WindowInsets.ime), // Ensures correct keyboard behavior
                reverseLayout = true, // Keeps the latest message at the bottom
                verticalArrangement = Arrangement.Bottom
            ) {
                items(messages) { message ->
                    SkyFitChatMessageBubble(message)
                }
            }

            // Chat input field at the bottom
            SkyFitChatMessageInputComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.ime) // Ensures correct spacing
                    .navigationBarsPadding() // Avoids navbar overlap
                    .padding(horizontal = 8.dp, vertical = 4.dp) // Fine-tune spacing
            ) { userInput ->
                viewModel.sendMessage(userInput)
            }
        }
    }
}



@Composable
private fun MobileUserToTrainerChatScreenToolbarComponent(
    trainerName: String,
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
                text = trainerName,
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