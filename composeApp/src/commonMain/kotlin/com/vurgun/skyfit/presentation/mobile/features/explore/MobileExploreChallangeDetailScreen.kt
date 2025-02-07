package com.vurgun.skyfit.presentation.mobile.features.explore

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileExploreChallengeDetailScreen(rootNavigator: Navigator) {

    var joined: Boolean = true
    var challengeTitle = "10,000 Steps a Day Challenge"

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader(challengeTitle, onClickBack = { rootNavigator.popBackStack() })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (joined) {
                MobileExploreChallengeDetailScreenParticipantBarComponent()
            } else {
//                MobileExploreChallengeDetailScreenJoinInfoComponent()
            }

            MobileExploreChallengeDetailScreenLeadershipBoardComponent()

            if (joined) {
                MobileExploreChallengeDetailScreenExitActionComponent()
            } else {
                MobileExploreChallengeDetailScreenJoinActionComponent()
            }
        }
    }
}

@Composable
private fun MobileExploreChallengeDetailScreenParticipantBarComponent() {
    val participants = listOf(
        "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680",
        "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680",
        "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680",
        "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"
    )

    Row(
        modifier = Modifier
            .size(382.dp, 56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.DarkGray)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Overlapping Profile Images
        participants.forEachIndexed { index, image ->
            AsyncImage(
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Member Count
        Text(
            text = "212 Members",
            fontSize = 14.sp,
            color = Color.LightGray
        )
    }
}

@Composable
private fun MobileExploreChallengeDetailScreenLeadershipBoardComponent() {
    val leaderboard = listOf(
        LeaderboardEntry(1, "Eddy Grah", 3, "11,279 steps"),
        LeaderboardEntry(2, "Joseph Phils", 2, "10,203 steps"),
        LeaderboardEntry(3, "Ely Buckner", 1, "9,436 steps"),
        LeaderboardEntry(4, "Maxine Jaconson", 0, "9,236 steps"),
        LeaderboardEntry(5, "Annie Debsk", 0, "9,124 steps"),
        LeaderboardEntry(6, "Sarah Hawl", 0, "8,987 steps"),
        LeaderboardEntry(7, "Benjamin Doel", 0, "8,674 steps")
    )

    Column(
        modifier = Modifier
            .size(382.dp, 644.dp)
            .background(Color.Black, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Lider Tablosu",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            TimeFilterSelector()
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Leaderboard Table
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(leaderboard) { entry ->
                LeaderboardRow(entry)
            }
        }
    }
}

@Composable
private fun LeaderboardRow(entry: LeaderboardEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Rank and Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${entry.rank}", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = entry.name, fontSize = 16.sp, color = Color.White)
        }

        // Crowns for top ranks
        Row {
            repeat(entry.crowns) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "Crown", tint = Color.Cyan)
            }
        }

        // Steps Count
        Text(text = entry.steps, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
private fun TimeFilterSelector() {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        listOf("1A", "1H", "1G").forEach { label ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (label == "1A") Color.Cyan else Color.DarkGray)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = label, fontSize = 12.sp, color = Color.White)
            }
        }
    }
}

private data class LeaderboardEntry(val rank: Int, val name: String, val crowns: Int, val steps: String)

@Composable
private fun MobileExploreChallengeDetailScreenExitActionComponent() {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Ayril",
        onClick = { },
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        state = ButtonState.Rest
    )
}

@Composable
private fun MobileExploreChallengeDetailScreenJoinActionComponent() {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Katil",
        onClick = { },
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = ButtonState.Rest
    )
}