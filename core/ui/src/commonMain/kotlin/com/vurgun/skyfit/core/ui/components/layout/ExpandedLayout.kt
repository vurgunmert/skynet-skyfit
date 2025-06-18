package com.vurgun.skyfit.core.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

object ExpandedLayout {

    @Composable
    fun LeftLargeMultiLaneScaffold(
        leftContent: @Composable ColumnScope.() -> Unit,
        rightContent: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Row(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(end = 16.dp)
                .background(
                    color = SkyFitColor.background.surfaceTertiary,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = leftContent
            )

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .width(363.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = rightContent
            )
        }
    }

    @Composable
    fun PageScaffold(
        topBar: @Composable () -> Unit,
        content: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = topBar,
            content = { innerPadding ->
                Row(
                    modifier = modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(end = 16.dp)
                        .fillMaxSize()
                        .background(
                            color = SkyFitColor.background.surfaceTertiary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        content = content
                    )

                    Spacer(Modifier.width(132.dp))
                }
            }
        )
    }

}