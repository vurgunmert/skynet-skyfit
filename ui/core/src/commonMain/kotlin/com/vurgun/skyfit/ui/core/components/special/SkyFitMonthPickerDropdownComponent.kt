package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun SkyFitMonthPickerDropdownComponent(
    modifier: Modifier,
    selectedMonth: String,
    onMonthSelected: (String) -> Unit
) {
    var selected by remember { mutableStateOf(selectedMonth) }
    var isOpen by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable { isOpen = true },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Calendar",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = selected,
            style = SkyFitTypography.bodyXSmallSemibold
        )

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Arrow",
            tint = SkyFitColor.icon.default,
            modifier = Modifier.size(16.dp)
        )
    }

    MonthsMenu(
        isOpen = isOpen,
        setIsOpen = { isOpen = it },
        itemSelected = { id, value ->
            selected = value
            onMonthSelected(value)
            isOpen = false
        }
    )
}

@Composable
fun MonthsMenu(
    isOpen: Boolean,
    setIsOpen: (Boolean) -> Unit,
    itemSelected: (String, String) -> Unit
) {
    val menuItems = getMenu()
    DropdownMenu(
        expanded = isOpen,
        onDismissRequest = { setIsOpen(false) },
        modifier = Modifier.width(120.dp).background(SkyFitColor.background.surfaceSecondary),
        properties = PopupProperties(focusable = true),
    ) {
        menuItems.forEach { (key, value) ->
            DropdownMenuItem(onClick = {
                itemSelected(key, value)
                setIsOpen(false)
            }) {
                Text(text = value, style = SkyFitTypography.bodyXSmallSemibold)
            }
        }
    }
}

private fun getMenu(): List<Pair<String, String>> {
    return listOf(
        "january" to "Ocak",
        "february" to "Şubat",
        "march" to "Mart",
        "april" to "Nisan",
        "may" to "Mayıs",
        "june" to "Haziran",
        "july" to "Temmuz",
        "august" to "Ağustos",
        "september" to "Eylül",
        "october" to "Ekim",
        "november" to "Kasım",
        "december" to "Aralık"
    )
}
