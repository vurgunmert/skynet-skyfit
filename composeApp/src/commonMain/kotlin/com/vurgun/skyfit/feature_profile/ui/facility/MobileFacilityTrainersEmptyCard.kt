package com.vurgun.skyfit.feature_profile.ui.facility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography

@Composable
fun MobileFacilityTrainersCard(onClickAdd: () -> Unit) {
    Column(Modifier.padding(16.dp).fillMaxWidth()) {
        Text("Antrenorlerimiz", style = SkyFitTypography.bodyLargeSemibold)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .padding(vertical = 34.dp),
            contentAlignment = Alignment.Center
        ) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(),
                text = "Antrenör Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest
            )
        }
    }
}

