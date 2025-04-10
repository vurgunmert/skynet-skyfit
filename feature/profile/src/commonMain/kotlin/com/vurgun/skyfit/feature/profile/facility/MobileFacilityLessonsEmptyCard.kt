package com.vurgun.skyfit.feature.profile.facility

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
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonState
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun MobileFacilityLessonsEmptyCard(onClickAdd: () -> Unit) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Ozel Dersler", style = SkyFitTypography.bodyLargeSemibold)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .padding(vertical = 34.dp),
            contentAlignment = Alignment.Center
        ) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(),
                text = "Ders Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest
            )
        }
    }
}
