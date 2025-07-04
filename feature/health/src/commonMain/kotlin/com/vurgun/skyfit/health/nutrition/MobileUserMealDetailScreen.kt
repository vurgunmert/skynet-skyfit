package com.vurgun.skyfit.health.nutrition

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyFitIconButton
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_app_logo

@Composable
fun MobileUserMealDetailScreen(goToBack: () -> Unit) {

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar("Kahvalti", onClickBack = goToBack)
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
            SkyFitIconButton(painterResource(Res.drawable.ic_app_logo), onClick = {})
            Spacer(Modifier.height(8.dp))
            NetworkImage(
                imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape)
            )
        }
    }
}