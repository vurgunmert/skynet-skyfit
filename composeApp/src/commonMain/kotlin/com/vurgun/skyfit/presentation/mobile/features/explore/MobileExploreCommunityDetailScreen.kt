package com.vurgun.skyfit.presentation.mobile.features.explore

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileExploreCommunityDetailScreen(rootNavigator: Navigator) {

    var joined by remember { mutableStateOf(true) } // MutableState to track join status

    val joinedGroups = listOf(
        Group("Duyurular", "Asıl: Hedeflerine ulaşman için buradayız.", "10:21"),
        Group("Fitness Team", "Asıl: Hedeflerine ulaşman için buradayız.", "10:21")
    )

    val availableGroups = listOf(
        Group("Hardcore Workouts", "Asıl: Hedeflerine ulaşman için buradayız.", "10:21"),
        Group("Duyurular", "Asıl: Hedeflerine ulaşman için buradayız.", "10:21")
    )

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileExploreCommunityDetailScreenToolbarComponent(onBackPress = { rootNavigator.popBackStack() })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreCommunityDetailScreenInfoComponent(
                communityName = "FormdaKal",
                communityImageUrl = "R.drawable.community_fitness",
                onLeaveClick = { joined = false }
            )
            Spacer(Modifier.height(16.dp))
            MobileExploreCommunityDetailScreenJoinedGroupsComponent(groups = joinedGroups)
            Spacer(Modifier.height(16.dp))
            MobileExploreCommunityDetailScreenAvailableGroupsComponent(groups = availableGroups)

            if (joined) {
                Spacer(Modifier.weight(1f))
                MobileExploreCommunityDetailScreenAddGroupActionComponent(onAddGroupClick = {})
            }
        }
    }
}

@Composable
private fun MobileExploreCommunityDetailScreenToolbarComponent(onBackPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPress) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
        }
    }
}


@Composable
private fun MobileExploreCommunityDetailScreenInfoComponent(
    communityName: String,
    communityImageUrl: String, // Replace with actual image resource
    onLeaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Community Image",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = communityName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text(text = "Topluluk", fontSize = 14.sp, color = Color.Gray)
            }
        }

        SkyFitButtonComponent(
            modifier = Modifier
                .padding(end = 24.dp)
                .wrapContentWidth(), text = "Ayrıl",
            onClick = onLeaveClick,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Micro,
            state = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileExploreCommunityDetailScreenJoinedGroupsComponent(groups: List<Group>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Gruplarım", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        groups.forEach { group ->
            GroupListItem(group)
        }
    }
}

@Composable
private fun MobileExploreCommunityDetailScreenAvailableGroupsComponent(groups: List<Group>) {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(text = "Katılabileceğin Gruplar", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        groups.forEach { group ->
            GroupListItem(group)
        }
    }
}

@Composable
private fun MobileExploreCommunityDetailScreenAddGroupActionComponent(onAddGroupClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = onAddGroupClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
                .height(48.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add", tint = Color.Black)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Grup Ekle", fontSize = 16.sp, color = Color.Black)
        }
    }
}


@Composable
private fun GroupListItem(group: Group) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Group Image",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = group.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = group.description, fontSize = 14.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Text(text = group.time, fontSize = 14.sp, color = Color.Gray)
    }
}

data class Group(val name: String, val description: String, val time: String)
