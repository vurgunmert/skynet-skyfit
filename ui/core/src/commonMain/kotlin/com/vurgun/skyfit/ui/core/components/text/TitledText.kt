package com.vurgun.skyfit.designsystem.components.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun TitledMediumRegularText(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String,
    hint: String = "",
    value: String? = null,
    rightIconRes: DrawableResource? = null
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(title, Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, shape = CircleShape)
                .padding(vertical = 18.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (value.isNullOrEmpty()) {
                Text(
                    text = hint,
                    modifier = Modifier.padding(start = 8.dp).weight(1f),
                    color = SkyFitColor.text.secondary,
                    style = SkyFitTypography.bodyMediumRegular
                )
            } else {
                Text(
                    text = value,
                    modifier = Modifier.weight(1f),
                    color = SkyFitColor.text.default,
                    style = SkyFitTypography.bodyMediumRegular
                )
            }

            rightIconRes?.let { iconRes ->
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
            }
        }
    }
}