package com.vurgun.skyfit.feature.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

data class HomeTopBarState(
    val isVisible: Boolean = true,
    val profileName: String = "",
    val userProfile: UserProfile? = null,
    val notificationsEnabled: Boolean = false,
    val notificationsHighlighted: Boolean = false,
    val conversationsEnabled: Boolean = false,
    val conversationsHighlighted: Boolean = false,
)

@Composable
fun ExpandedDefaultTopBar(
    state: HomeTopBarState,
    onNavigate: (ScreenProvider) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        HomeExpandedTopbarStartContentGroup(state.profileName)

        Spacer(Modifier.weight(1f))

        state.userProfile?.let {
            HomeExpandedTopbarProfileGroup(it)
        }

        HomeExpandedTopbarEndActionGroup(
            notificationHighlighted = state.notificationsHighlighted,
            conversationsHighlighted = state.conversationsHighlighted,
            onClickNotifications = { onNavigate(SharedScreen.Notifications) },
            onClickConversations = { onNavigate(SharedScreen.Conversations) },
            onClickAppAction = { onNavigate(SharedScreen.ChatBot) }
        )
    }
}


@Composable
private fun HomeExpandedTopbarStartContentGroup(firstName: String) {
    Column {
        SkyText(
            text = "Merhaba $firstName!",
            styleType = TextStyleType.BodyMediumSemibold
        )
        SkyText(
            text = "Bugünün meydan okumalarına hazır mısın?",
            styleType = TextStyleType.BodySmall,
            color = SkyFitColor.text.secondary
        )
    }
}

@Composable
private fun HomeExpandedTopbarEndActionGroup(
    notificationHighlighted: Boolean,
    conversationsHighlighted: Boolean,
    onClickNotifications: () -> Unit,
    onClickConversations: () -> Unit,
    onClickAppAction: () -> Unit,
) {

}

@Composable
private fun HomeExpandedTopbarProfileGroup(userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .wrapContentWidth()
            .padding(4.dp)
    ) {
        NetworkImage(
            imageUrl = "https://picsum.photos/400/400",
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.width(16.dp))
        NetworkImage(
            imageUrl = "https://picsum.photos/400/400",
            modifier = Modifier.size(48.dp)
        )
        Spacer(Modifier.width(16.dp))
        NetworkImage(
            imageUrl = "https://picsum.photos/400/400",
            modifier = Modifier.size(48.dp)
        )
    }
}

