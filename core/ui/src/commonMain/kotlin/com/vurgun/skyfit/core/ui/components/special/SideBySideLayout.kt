package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize

@Composable
fun SideBySideLayout(
    modifier: Modifier = Modifier,
    leftModifier: Modifier = Modifier,
    rightModifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit,
    rightContent: @Composable () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        Box(
            modifier = leftModifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            leftContent()
        }
        Box(
            modifier = rightModifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            rightContent()
        }
    }
}


@Composable
fun AutoSideBySideLayout(
    modifier: Modifier = Modifier,
    leftModifier: Modifier = Modifier,
    rightModifier: Modifier = Modifier,
    leftContent: @Composable ColumnScope.() -> Unit,
    rightContent: @Composable ColumnScope.() -> Unit
) {
    val windowSize = LocalWindowSize.current
    val isCompact = windowSize == WindowSize.COMPACT || windowSize == WindowSize.MEDIUM


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default)
            .padding(16.dp)
    ) {
        if (isCompact) {
            Column(modifier = Modifier.fillMaxWidth()) {
                leftContent()
                Spacer(modifier = Modifier.height(16.dp))
                rightContent()
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = leftModifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    leftContent()
                }

                Column(
                    modifier = rightModifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    rightContent()
                }
            }
        }
    }
}

@Composable
fun HomeScreenResponsiveLayout(
    modifier: Modifier = Modifier,
    leftContent: @Composable ColumnScope.() -> Unit,
    rightContent: @Composable ColumnScope.() -> Unit
) {
    val windowSize = LocalWindowSize.current
    val isCompact = windowSize == WindowSize.COMPACT || windowSize == WindowSize.MEDIUM
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        if (isCompact) {
            Column(Modifier.fillMaxWidth()) {
                leftContent()
                Spacer(Modifier.height(16.dp))
                rightContent()
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight()
                ) {
                    leftContent()
                }
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier
                        .widthIn(min = 363.dp, max = 430.dp)
                        .wrapContentHeight()
                ) {
                    rightContent()
                }
            }
        }
    }
}

