package com.vurgun.skyfit.feature.connect.conversation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.connect.conversation.component.ConversationsComponent
import com.vurgun.skyfit.feature.connect.conversation.component.ConversationsComponent.ActiveUsersRow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

class ConversationsScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        when (windowSize) {
            WindowSize.EXPANDED -> ConversationsExpanded()
            else -> ConversationsCompact()
        }
        val viewModel = UserConversationsViewModel()

        ConversationsCompact(
            goToBack = { navigator.pop() },
            goToChat = { navigator.push(UserToUserChatScreen()) },
            viewModel = viewModel
        )
    }
}


class ConversationsExpanded(
    private val onDismiss: () -> Unit,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = UserConversationsViewModel()

        ConversationsCompact(
            goToBack = onDismiss,
            goToChat = { navigator.push(UserToUserChatScreen()) },
            viewModel = viewModel
        )
    }
}

@Composable
fun ConversationsCompact(
    goToBack: () -> Unit,
    goToChat: () -> Unit,
    viewModel: UserConversationsViewModel
) {

    val conversations = viewModel.converstations

    Scaffold(
        topBar = { SkyFitScreenHeader(title = stringResource(Res.string.messages_label), onClickBack = goToBack) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ConversationsComponent.ActiveUsersRow()

            ConversationsComponent.Content()
    }
}


