package com.vurgun.skyfit.feature.messaging.communication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.special.SkyFitCircleAvatarRowComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitNumberBadge
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.components.special.UserCircleAvatarItem
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

class ConversationsScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = UserConversationsViewModel()

        MobileUserConversationsScreen(
            goToBack = { navigator.pop() },
            goToChat = { navigator.push(SharedScreen.UserChat(0)) },
            viewModel = viewModel
        )
    }
}

@Composable
fun MobileUserConversationsScreen(
    goToBack: () -> Unit,
    goToChat: () -> Unit,
    viewModel: UserConversationsViewModel
) {

    val conversations = viewModel.converstations

    SkyFitMobileScaffold(
        topBar = {
            Column {
                SkyFitScreenHeader(title = "Mesajlar", onClickBack = goToBack)
                MobileUserConversationsScreenToolbarComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserConversationsSearchComponent()

            if (conversations.isEmpty()) {
                MobileUserConversationsEmptyComponent()
            } else {
                MobileUserConversationsComponent(
                    conversations,
                    onClickConversation = goToChat
                )
                Spacer(Modifier.height(48.dp))
            }
        }
    }
}

@Composable
private fun MobileUserConversationsScreenToolbarComponent() {
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

data class UserConversationItem(
    val avatarUrl: String = "",
    val title: String = "Julia Heosten",
    val message: String = "It was amazing! Really helped me stretch out and relax. Have you tried yoga before?",
    val time: String = "13:55",
    val unreadCount: Int = 0
)

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
            painter = painterResource(Res.drawable.logo_skyfit), // TODO: Avatar url or res
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