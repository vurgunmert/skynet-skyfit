package com.vurgun.skyfit.feature_profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

@Composable
fun MobileVisitedProfileActionsComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClickAbout: () -> Unit = {},
    onClickPosts: () -> Unit = {}
) {
    var aboutSelected by remember { mutableStateOf(true) }

    Row(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(if (aboutSelected) 1f else 3f)
                .background(
                    if (aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary,
                    RoundedCornerShape(12.dp)
                )
                .clickable(onClick = { aboutSelected = true; onClickAbout.invoke() })
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hakkımda",
                style = SkyFitTypography.bodyLargeMedium,
                color = if (aboutSelected) SkyFitColor.text.inverse else SkyFitColor.text.default
            )
        }

        Box(
            modifier = Modifier
                .weight(if (!aboutSelected) 3f else 1f)
                .background(
                    if (!aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary,
                    RoundedCornerShape(12.dp)
                )
                .clickable(onClick = { aboutSelected = false; onClickPosts.invoke() })
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Paylaşımlar",
                style = SkyFitTypography.bodyLargeMedium,
                color = if (!aboutSelected) SkyFitColor.text.inverse else SkyFitColor.text.default
            )
        }
    }
}