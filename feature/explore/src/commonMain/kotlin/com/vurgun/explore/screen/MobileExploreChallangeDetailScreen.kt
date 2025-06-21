package com.vurgun.explore.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_star_filled

@Composable
fun MobileExploreChallengeDetailScreen(
    goToBack: () -> Unit
) {

    var userJoined by remember { mutableStateOf(true) }
    var challengeTitle = "10,000 Steps a Day Challenge"

    SkyFitScaffold(
        topBar = {
            CompactTopBar(challengeTitle, onClickBack = goToBack )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (userJoined) {
                MobileExploreChallengeDetailScreenParticipantBarComponent()
            } else {
//                MobileExploreChallengeDetailScreenJoinInfoComponent()
            }

            MobileExploreChallengeDetailScreenLeadershipBoardComponent()

            Spacer(Modifier.weight(1f))

            if (userJoined) {
                MobileExploreChallengeDetailScreenExitActionComponent(onClick = { userJoined = false })
            } else {
                MobileExploreChallengeDetailScreenJoinActionComponent(onClick = { userJoined = true })
            }

            Spacer(Modifier.height(32.dp))
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
            .background(SkyFitColor.background.surfaceSecondary)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Overlapping Profile Images
        participants.forEachIndexed { index, image ->
            NetworkImage(
                imageUrl = image,
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
            .size(382.dp, 400.dp)
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
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
                Icon(
                    painter = painterResource(Res.drawable.ic_star_filled),
                    contentDescription = "Crown",
                    tint = Color.Cyan)
            }
        }

        // Steps Count
        Text(text = entry.steps, fontSize = 14.sp, color = Color.Gray)
    }
}

@Composable
fun TimeFilterSelector(
    options: List<String> = listOf("Y", "6A", "3A", "1A", "H"),
    selected: String = "H",
    onSelect: (String) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        options.forEach { label ->
            val isSelected = label == selected
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color(0xFF1D2C36) else Color.Transparent
                    )
                    .clickable { onSelect(label) }
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    color = if (isSelected) Color.White else Color.Gray
                )
            }
        }
    }
}


private data class LeaderboardEntry(val rank: Int, val name: String, val crowns: Int, val steps: String)

@Composable
private fun MobileExploreChallengeDetailScreenExitActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Ayril",
        onClick = onClick,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        state = ButtonState.Rest
    )
}

@Composable
private fun MobileExploreChallengeDetailScreenJoinActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Katil",
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = ButtonState.Rest
    )
}