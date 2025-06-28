package com.vurgun.skyfit.core.ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.icon.CloseIconRow
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.ui.tooling.preview.Preview
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_star
import fiwe.core.ui.generated.resources.ic_star_filled

@Composable
fun BasicRatingDialog(
    onDismiss: () -> Unit,
    onSubmit: (Int) -> Unit
) {
    var selectedRating by remember { mutableStateOf(0) }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .widthIn(max = 382.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.surfaceSecondary)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CloseIconRow(onClick = onDismiss, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SkyText(
                    text = "Değerlendirmelerinizi Bizimle Paylaşın",
                    styleType = TextStyleType.BodyLargeMedium,
                    alignment = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    (1..5).forEach { index ->
                        SkyIcon(
                            res = if (index <= selectedRating)
                                Res.drawable.ic_star_filled
                            else
                                Res.drawable.ic_star,
                            size = SkyIconSize.Large,
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    selectedRating = index
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                SkyButton(
                    label = "Değerlendirmeyi Gönder",
                    size = SkyButtonSize.Large,
                    onClick = {
                        onSubmit(selectedRating)
                        onDismiss()
                    }
                )
            }
        }
    }
}


@Preview
@Composable
private fun BasicRatingDialogPreview() {
    BasicRatingDialog(
        onDismiss = { /* hide dialog */ },
        onSubmit = { rating ->
            println("Kullanıcı puanı: $rating yıldız") // Send to server or viewModel
        }
    )
}
