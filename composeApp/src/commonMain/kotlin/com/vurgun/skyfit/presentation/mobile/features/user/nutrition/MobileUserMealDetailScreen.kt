package com.vurgun.skyfit.presentation.mobile.features.user.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.button.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserMealDetailScreen(rootNavigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Kahvalti", onClickBack = { rootNavigator.popBackStack() })
        }
    ) {
        MobileUserMealDetailScreenFoodRecordsComponent()
    }
}

@Composable
private fun MobileUserMealDetailScreenFoodRecordsComponent() {
    var records = listOf(1, 23, 4, 5, 5, 6)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(records) {
            MobileMealRecordItemComponent()
        }
    }
}

@Composable
private fun MobileMealRecordItemComponent() {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(Modifier.weight(0.7f)) {
            Row {
                Text(
                    text = "Peynirli Salata",
                    style = SkyFitTypography.bodyMediumSemibold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "120 kcal",
                    style = SkyFitTypography.bodyMediumSemibold,
                    color = SkyFitColor.text.secondary
                )
            }
            Text(
                text = "120 kcal",
                style = SkyFitTypography.bodySmall,
                color = SkyFitColor.text.secondary
            )
            SkyFitSearchTextInputComponent(
                hint = "1 Porsiyon",
                modifier = Modifier.height(36.dp)
            )
            Text(
                text = "Zaman Aralığı",
                style = SkyFitTypography.bodySmall,
                color = SkyFitColor.text.secondary
            )
            SkyFitSearchTextInputComponent(
                hint = "1 Hafta",
                modifier = Modifier.height(36.dp)
            )
        }
        Spacer(Modifier.width(8.dp))

        Column(Modifier.weight(0.3f)) {
            SkyFitIconButton(painterResource(Res.drawable.logo_skyfit), onClick = {})
            Spacer(Modifier.height(8.dp))
            AsyncImage(
                model = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape)
            )
        }
    }
}