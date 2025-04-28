package com.vurgun.skyfit.feature.bodyanalysis.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.text.BodySmallRegularText
import com.vurgun.skyfit.core.ui.components.text.BodySmallSemiboldText
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_close_circle
import skyfit.core.ui.generated.resources.ic_info_circle

@Composable
internal fun BoxScope.PostureAnalysisInfoScreen(
    onClickDismiss: () -> Unit,
) {

    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .widthIn(max = 406.dp)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Header with Close Button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            ActionIcon(Res.drawable.ic_close_circle, onClick = onClickDismiss)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Info Sections
        PostureCaptureInfoCard(
            title = "Pozisyon ve Duruş",
            content = listOf(
                "Ekran, tüm vücudu kapsayacak şekilde ayarlanmalıdır.",
                "Bacaklar omuz genişliğinde açık, eller yanda doğal pozisyonda olmalı.",
                "Kafa, düz bir şekilde ekrana bakmalı ve nefes tutulmamalıdır."
            )
        )

        Spacer(modifier = Modifier.height(24.dp))


        PostureCaptureInfoCard(
            title = "Giyim ve Arka Plan",
            content = listOf(
                "Vücudu saran giysiler tercih edilmeli; bol ve geniş giysilerden kaçınılmalıdır.",
                "Omuz başları, dizler ve ayak bileği eklemleri görünür olmalıdır.",
                "Tek renk bir zemin önünde durulmalıdır."
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        PostureCaptureInfoCard(
            title = "Kamera ve Postür Tipi",
            content = listOf(
                "Kamera bel hizasında ve tam karşıdan tutulmalıdır.",
                "Lateral Postür: Vücut yan konumda, baş yukarıda ve karşıya bakmalıdır.",
                "Posterior Postür: Sırt tamamen dönük, skapula kemikleri görünecek şekilde durulmalıdır."
            )
        )
    }
}

@Composable
private fun PostureCaptureInfoCard(
    title: String,
    content: List<String>
) {
    val localPadding = LocalPadding.current
    Column(
        Modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceBrandSelected, RoundedCornerShape(localPadding.small))
            .padding(localPadding.medium)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(Res.drawable.ic_info_circle),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(localPadding.xSmall))
            BodySmallSemiboldText(
                text = title,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(localPadding.xSmall))
        content.forEach {
            BodySmallRegularText(text = "• $it", modifier = Modifier.fillMaxWidth())
        }
    }
}