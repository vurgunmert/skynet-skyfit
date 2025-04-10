package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_chevron_down

@Composable
fun SkyFitDropdownComponent(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    var isOpen by remember { mutableStateOf(false) }

    Column(modifier) {
        // Title
        Text(
            text = title,
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        // Dropdown Selection Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
                .clickable { isOpen = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedOption,
                style = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(Res.drawable.ic_chevron_down),
                contentDescription = "Dropdown Arrow",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(20.dp)
            )
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = { isOpen = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        isOpen = false
                    }
                ) {
                    Text(
                        text = option,
                        style = SkyFitTypography.bodyMediumRegular,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
