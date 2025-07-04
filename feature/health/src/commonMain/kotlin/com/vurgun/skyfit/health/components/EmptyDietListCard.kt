package com.vurgun.skyfit.health.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_app_logo

@Composable
private fun EmptyDietListCard(onClickAdd: () -> Unit) {
    Column(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent)
            .padding(16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(Res.drawable.ic_app_logo),
                modifier = Modifier.size(24.dp),
                contentDescription = ""
            )
            Text(
                text = "Diyet Listesi",
                style = SkyFitTypography.bodyMediumSemibold
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 96.dp),
            contentAlignment = Alignment.Center
        ) {

            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Diyet Listesi Oluştur",
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest
            )
        }
    }
}