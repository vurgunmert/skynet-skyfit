package com.vurgun.skyfit.core.ui.components.picker

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun WheelPicker(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 5
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)

    LaunchedEffect(listState.isScrollInProgress.not()) {
        val centerIndex = listState.firstVisibleItemIndex + (visibleItemsCount / 2)
        if (centerIndex != selectedIndex && centerIndex in items.indices) {
            onItemSelected(centerIndex)
        }
    }

    Box(modifier = modifier.height(150.dp)) { // Adjust height for item size * visible count
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            itemsIndexed(items) { index, item ->

                SkyText(
                    text = item,
                    styleType =
                        when (index) {
                            selectedIndex -> TextStyleType.BodyMediumMedium
                            else -> TextStyleType.BodyMediumSemibold
                        },
                    color = when (index) {
                        selectedIndex -> SkyFitColor.text.default
                        else -> SkyFitColor.text.secondary
                    }
                )
            }
        }

        // Optional: add blur/overlay or highlight for center item
    }
}

@Composable
fun WheelPicker(
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    itemHeight: Dp,
    visibleCount: Int
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)

    LaunchedEffect(listState.isScrollInProgress.not()) {
        val centerIndex = listState.firstVisibleItemIndex + visibleCount / 2
        if (centerIndex != selectedIndex && centerIndex in items.indices) {
            onItemSelected(centerIndex)
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .height(itemHeight * visibleCount)
            .width(60.dp), // fix this width as per UI needs
        contentPadding = PaddingValues(vertical = itemHeight * (visibleCount / 2)),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        itemsIndexed(items) { _, item ->
            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                SkyText(
                    text = item,
                    styleType = TextStyleType.BodyMediumSemibold,
                    color = SkyFitColor.text.secondary
                )
            }
        }
    }
}


@Composable
fun TimeWheelPicker(
    selectedHour: Int,
    selectedMinute: Int,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    val itemHeight = 40.dp
    val visibleItemsCount = 5
    val pickerHeight = itemHeight * visibleItemsCount

    Row(
        modifier = Modifier
            .height(pickerHeight)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hour picker
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                WheelPicker(
                    items = (0..23).map { it.toString().padStart(2, '0') },
                    selectedIndex = selectedHour,
                    onItemSelected = onHourChange,
                    modifier = Modifier.height(pickerHeight)
                )
                // Optional: highlight area
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("saat", color = SkyFitColor.text.default)
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Minute picker
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box {
                WheelPicker(
                    items = (0..59).map { it.toString().padStart(2, '0') },
                    selectedIndex = selectedMinute,
                    onItemSelected = onMinuteChange,
                    modifier = Modifier.height(pickerHeight)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("dakika", color = SkyFitColor.text.default)
        }
    }
}


@Composable
fun DurationWheelPicker(
    hourState: PickerState,
    minuteState: PickerState,
    modifier: Modifier = Modifier,
    visibleItemCount: Int = 5,
    itemHeight: Dp = 40.dp
) {
    val height = itemHeight * visibleItemCount

    Row(
        modifier = modifier
            .height(height)
            .width(172.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Hour Picker
        LabeledWheelPicker(
            items = (0..23).map { it.toString().padStart(2, '0') },
            label = "saat",
            state = hourState,
            itemHeight = itemHeight,
            visibleItemCount = visibleItemCount
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Minute Picker
        LabeledWheelPicker(
            items = (0..59).map { it.toString().padStart(2, '0') },
            label = "dakika",
            state = minuteState,
            itemHeight = itemHeight,
            visibleItemCount = visibleItemCount
        )
    }
}

@Composable
fun rememberPickerState(initialItem: String = ""): PickerState {
    return remember { PickerState(initialItem) }
}

class PickerState(initialItem: String) {
    var selectedItem by mutableStateOf(initialItem)
}

@Composable
fun LabeledWheelPicker(
    items: List<String>,
    label: String,
    state: PickerState = rememberPickerState(),
    itemHeight: Dp,
    visibleItemCount: Int
) {
    val listScrollCount = Int.MAX_VALUE
    val middleOffset = visibleItemCount / 2
    val listScrollMiddle = listScrollCount / 2
    val startIndex = listScrollMiddle - (listScrollMiddle % items.size)

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPx = remember { mutableIntStateOf(0) }
    val itemHeightDp = with(LocalDensity.current) { itemHeightPx.value.toDp() }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + middleOffset) }
            .distinctUntilChanged()
            .collect { state.selectedItem = it }
    }

    Box(modifier = Modifier.wrapContentWidth()) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            modifier = Modifier
                .height(itemHeight * visibleItemCount)
                .width(60.dp),
            contentPadding = PaddingValues(vertical = itemHeight * middleOffset),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listScrollCount) { index ->
                SkyText(
                    text = getItem(index),
                    modifier = Modifier
                        .height(itemHeight)
                        .onSizeChanged { itemHeightPx.value = it.height },
                    styleType = TextStyleType.BodyMediumSemibold,
                    color = SkyFitColor.text.secondary
                )
            }
        }

        // Selected item + label in center row
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeight),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                SkyText(
                    text = state.selectedItem,
                    styleType = TextStyleType.BodyMediumSemibold
                )
                Spacer(modifier = Modifier.width(4.dp))
                SkyText(
                    text = label,
                    styleType = TextStyleType.BodySmallSemibold
                )
            }
        }
    }
}
