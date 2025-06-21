package com.vurgun.skyfit.feature.connect.conversation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.SkyFitCircleAvatarRowComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitNumberBadge
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.connect.conversation.ConversationsAction
import com.vurgun.skyfit.feature.connect.conversation.ConversationsUiState
import com.vurgun.skyfit.feature.connect.conversation.UserConversationItem
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_app_logo

internal object ConversationsComponent {

    @Composable
    fun ActiveUsersRow() {
        val avatars = listOf(
            UserCircleAvatarItem("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg"),
            UserCircleAvatarItem("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg"),
            UserCircleAvatarItem("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg"),
            UserCircleAvatarItem("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg"),
            UserCircleAvatarItem("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg"),
            UserCircleAvatarItem("https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg"),
        )
        Column(Modifier.fillMaxWidth()) {
            Spacer(Modifier.width(8.dp))
            SkyFitCircleAvatarRowComponent(
                label = "Aktif",
                avatars = avatars,
                onClickRowItem = {})
            Spacer(Modifier.width(8.dp))
        }
    }

    @Composable
    fun Content(
        content: ConversationsUiState.Content,
        onAction: (ConversationsAction) -> Unit
    ) {

        MobileUserConversationsSearchComponent()

        if (content.previews.isEmpty()) {
            MobileUserConversationsEmptyComponent()
        } else {
            MobileUserConversationsComponent(
                conversations = content.previews,
                onClickConversation = { onAction(ConversationsAction.OnClickConversation) }
            )
            Spacer(Modifier.height(48.dp))
        }
    }


    @Composable
    private fun MobileUserConversationsSearchComponent() {
        var searchQuery by remember { mutableStateOf("") }

        Column(Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)) {
            Text(
                text = "Chat",
                style = SkyFitTypography.heading4
            )
            Spacer(Modifier.height(8.dp))

            SkyFitSearchTextInputComponent(
                hint = "Search...",
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    private fun MobileUserConversationsEmptyComponent() {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 48.dp), contentAlignment = Alignment.Center) {
            Text(
                "Görünüşe göre henüz bir mesajınız yok. Başlamak için bir arkadaşınıza mesaj gönderin",
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.disabled)
            )
        }
    }

    @Composable
    private fun MobileUserConversationsComponent(
        conversations: List<UserConversationItem>,
        onClickConversation: () -> Unit
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(conversations) {
                MobileUserConversationItemComponent(it, onClick = onClickConversation)
            }
        }
    }

    @Composable
    private fun MobileUserConversationItemComponent(
        conversation: UserConversationItem,
        onClick: () -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Image(
                painter = painterResource(Res.drawable.ic_app_logo), // TODO: Avatar url or res
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = conversation.title,
                    style = SkyFitTypography.bodyMediumMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = conversation.message,
                    style = SkyFitTypography.bodySmall,
                    maxLines = 2
                )
            }

            Spacer(Modifier.width(24.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = conversation.time,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
                if (conversation.unreadCount > 0) {
                    Spacer(Modifier.height(8.dp))
                    SkyFitNumberBadge(value = conversation.unreadCount)
                }
            }
        }
    }
}

