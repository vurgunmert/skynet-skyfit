package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import kotlinx.coroutines.launch


@Composable
fun <T> SkyFitWheelPickerComponent(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    itemText: (T) -> String = { it.toString() },
    visibleItemCount: Int = 3,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // Helper function to calculate the center index
    fun calculateCenterIndex(): Int {
        val visibleItems = listState.layoutInfo.visibleItemsInfo
        return visibleItems.firstOrNull { it.offset == 0 }?.index
            ?: (listState.firstVisibleItemIndex + visibleItems.size / 2)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp * visibleItemCount) // Total height based on visible items
    ) {
        // Highlighted Center Area
        Box(
            modifier = Modifier
                .padding(horizontal = 80.dp)
                .fillMaxWidth()
                .height(48.dp)
                .align(Alignment.Center)
                .background(
                    color = SkyFitColor.background.surfaceTertiary,
                    shape = RoundedCornerShape(8.dp)
                )
        )

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            flingBehavior = rememberSnapFlingBehavior(listState),
            contentPadding = PaddingValues(vertical = (48.dp * (visibleItemCount - 1)) / 2)
        ) {
            itemsIndexed(items) { index, item ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                                onItemSelected(item)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemText(item),
                        style = if (item == selectedItem) {
                            SkyFitTypography.bodyLarge.copy(color = SkyFitColor.text.default)
                        } else {
                            SkyFitTypography.bodyMediumMedium.copy(color = SkyFitColor.text.inverseSecondary)
                        }
                    )
                }
            }
        }
    }

    // Scroll to the selected item when the component loads
    LaunchedEffect(selectedItem) {
        coroutineScope.launch {
            val index = items.indexOf(selectedItem)
            if (index != -1 && index != listState.firstVisibleItemIndex) {
                listState.scrollToItem(index)
            }
        }
    }

    // Trigger onItemSelected when scrolling stops
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            coroutineScope.launch {
                val centerIndex = calculateCenterIndex()
                if (centerIndex in items.indices && items[centerIndex] != selectedItem) {
                    onItemSelected(items[centerIndex])
                }
            }
        }
    }
}


@Composable
fun YearPicker(
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    startYear: Int = 1900,
    endYear: Int = 2100
) {
    val years = (startYear..endYear).toList()

    SkyFitWheelPickerComponent(
        items = years,
        selectedItem = selectedYear,
        onItemSelected = onYearSelected,
        itemText = { it.toString() },
        visibleItemCount = 5 // Show 5 visible items
    )
}
