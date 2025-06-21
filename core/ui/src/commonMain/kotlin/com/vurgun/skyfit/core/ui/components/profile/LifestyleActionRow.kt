package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.profile.LifestyleActionRowViewData
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography

@Composable
fun LifestyleActionRow(
    viewData: LifestyleActionRowViewData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                painter = SkyFitAsset.getPainter(viewData.iconId),
                modifier = Modifier.size(24.dp),
                contentDescription = viewData.title,
                tint = SkyFitColor.icon.default
            )
            Text(
                text = viewData.title,
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewData.items) { item ->
                LifestyleActionRowItem(item, viewData.iconSizePx.dp)
            }
        }
    }
}

@Composable
private fun LifestyleActionRowItem(item: LifestyleActionItemViewData, iconSize: Dp) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .size(iconSize + 20.dp)
                .background(SkyFitColor.background.fillTransparentSecondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = SkyFitAsset.getPainter(item.iconId),
                modifier = Modifier.size(iconSize),
                contentDescription = item.label,
                tint = SkyFitColor.icon.default
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = item.label,
            style = SkyFitTypography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(iconSize + 20.dp),
            softWrap = true,
            overflow = TextOverflow.Visible
        )
    }
}

