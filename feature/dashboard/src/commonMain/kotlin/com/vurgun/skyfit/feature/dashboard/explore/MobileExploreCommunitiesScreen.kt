package com.vurgun.skyfit.feature.dashboard.explore


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_right
import skyfit.core.ui.generated.resources.ic_posture_fill
import skyfit.core.ui.generated.resources.logo_skyfit

@Composable
fun MobileExploreCommunitiesScreen(
    goToBack: () -> Unit
) {

    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitMobileScaffold(
        topBar = {
            Column {
                Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    SkyFitScreenHeader("Topluluklar", onClickBack = goToBack)
                    Spacer(Modifier.height(16.dp))
                    if (isSearchVisible) {
                        SkyFitSearchTextInputComponent()
                        Spacer(Modifier.height(16.dp))
                    }
                    SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileExploreFeaturedCommunitiesComponent()
            MobileExploreNewCommunitiesComponent()
        }
    }
}

@Composable
private fun MobileExploreFeaturedCommunitiesComponent() {
    val featuredCommunity = Community(
        name = "FormdaKal",
        description = "Hedeflerine ulaşırken seni motive eden, destekleyici ve enerjik bir fitness topluluğu...",
        members = "20.3K"
    )

    Column(
        modifier = Modifier
            .size(398.dp, 152.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFFFF9800), Color(0xFFFF5722))))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Featured Community",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = featuredCommunity.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = featuredCommunity.description,
                    fontSize = 14.sp,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(Res.drawable.ic_posture_fill), contentDescription = "Members", tint = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = featuredCommunity.members, fontSize = 14.sp, color = Color.Black)
                }
            }
            Icon(painter = painterResource(Res.drawable.ic_chevron_right), contentDescription = "Go", tint = Color.Black)
        }
    }
}

@Composable
private fun MobileExploreNewCommunitiesComponent() {
    val newCommunities = listOf(
        Community(
            "FitZirve",
            "Hedeflerine ulaşırken seni motive eden, destekleyici ve enerjik bir fitness topluluğu...",
            "65"
        ),
        Community(
            "Esnek ve Aktif",
            "Hedeflerine ulaşırken seni motive eden, destekleyici ve enerjik bir fitness topluluğu...",
            "126"
        ),
        Community(
            "Zirveye Doğru",
            "Hedeflerine ulaşırken seni motive eden, destekleyici ve enerjik bir fitness topluluğu...",
            "42"
        )
    )

    Column(
        modifier = Modifier
            .size(430.dp, 400.dp)
            .padding(8.dp)
    ) {
        Text(text = "Yeni", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(newCommunities) { community ->
                CommunityCard(community)
            }
        }
    }
}

@Composable
private fun CommunityCard(community: Community) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.DarkGray)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = "Community Image",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = community.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Text(
                text = community.description,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(Res.drawable.ic_posture_fill),
                    contentDescription = "Members",
                    tint = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = community.members, fontSize = 14.sp, color = Color.Gray)
            }
        }
        Icon(painter = painterResource(Res.drawable.ic_chevron_right), contentDescription = "Go", tint = Color.Gray)
    }
}

private data class Community(val name: String, val description: String, val members: String)
