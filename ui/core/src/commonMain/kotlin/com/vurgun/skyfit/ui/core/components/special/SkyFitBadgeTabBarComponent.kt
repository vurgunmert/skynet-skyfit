package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun SkyFitBadgeTabBarComponent(
    titles: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    onFilter: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        titles.forEachIndexed { index, title ->
            TabItem(
                title = title,
                isSelected = index == selectedTabIndex,
                onClick = { onTabSelected(index) }
            )
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = "Filtre",
            color = SkyFitColor.text.linkInverse,
            style = SkyFitTypography.bodyMediumRegular,
            softWrap = true,
            modifier = Modifier
                .clickable(onClick = onFilter)
                .padding(start = 8.dp, end = 16.dp)
        )
    }
}

@Composable
private fun TabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    var tabWidth by remember { mutableStateOf(0) } // Holds the measured width of the TabItem

    Box(modifier = Modifier
        .wrapContentWidth().height(52.dp)
        .clickable(onClick = onClick)
        .onGloballyPositioned { coordinates ->
            tabWidth = coordinates.size.width // Measure the width of this Box
        }) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title.substringBefore(" "),
                style = SkyFitTypography.bodyMediumMedium
            )
            val badgeCount = extractBadgeCount(title)
            if (badgeCount > 0) {
                Spacer(modifier = Modifier.width(4.dp))
                SkyFitNumberBadge(value = badgeCount)
            }
        }
        // Selected Indicator
        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(with(LocalDensity.current) { tabWidth.toDp() }) // Use measured width
                    .height(2.dp)
                    .align(Alignment.BottomCenter)
                    .background(SkyFitColor.border.secondaryButton)
            )
        }
    }
}

@Composable
fun SkyFitNumberBadge(value: Int) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(CircleShape)
            .background(SkyFitColor.background.surfaceSecondary),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString(),
            style = SkyFitTypography.bodySmall
        )
    }
}

private fun extractBadgeCount(title: String): Int {
    return title.substringAfter("(").substringBefore(")").toIntOrNull() ?: 0
}
