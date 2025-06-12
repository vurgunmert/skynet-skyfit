package com.vurgun.skyfit.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

internal object HomeExpandedComponent {

    @Composable
    fun Scaffold(
        topBar: @Composable () -> Unit,
        leftContent: @Composable ColumnScope.() -> Unit,
        rightContent: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = topBar,
            content = {
                Row(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(end = 16.dp)
                        .fillMaxSize()
                        .background(
                            color = SkyFitColor.background.surfaceTertiary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        content = leftContent
                    )

                    Spacer(Modifier.width(16.dp))

                    Column(
                        modifier = Modifier
                            .width(363.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        content = rightContent
                    )
                }
            }
        )
    }

    @Composable
    fun Scaffold(
        topBar: @Composable () -> Unit,
        content: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = topBar,
            content = {
                Row(
                    modifier = modifier
                        .verticalScroll(rememberScrollState())
                        .padding(end = 16.dp)
                        .fillMaxSize()
                        .background(
                            color = SkyFitColor.background.surfaceTertiary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        content = content
                    )

                    Spacer(Modifier.width(132.dp))
                }
            }
        )
    }

    @Composable
    fun UserDefaultTopBar(
        userProfile: UserProfile,
        modifier: Modifier = Modifier,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
    ) {

    }

    @Composable
    fun TrainerDefaultTopBar(
        trainerProfile: TrainerProfile,
        modifier: Modifier = Modifier,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
    ) {

    }

    @Composable
    fun FacilityDefaultTopBar(
        trainerProfile: TrainerProfile,
        modifier: Modifier = Modifier,
        onClickNotifications: () -> Unit,
        onClickConversations: () -> Unit,
        onClickChatBot: () -> Unit,
    ) {

    }
}