package com.vurgun.skyfit.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage

internal object ProfileExpandedComponent {

    @Composable
    fun Layout(
        header: @Composable () -> Unit,
        content: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            header()
            Spacer(Modifier.height(12.dp))
            content()
        }
    }

    @Composable
    fun Layout(
        header: @Composable () -> Unit,
        leftContent: @Composable ColumnScope.() -> Unit,
        rightContent: @Composable ColumnScope.() -> Unit,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            header()
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.weight(1f),
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    content = leftContent
                )
                Spacer(Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(2f),
                    content = rightContent
                )
            }
        }
    }

    @Composable
    fun Header(
        backgroundImageUrl: String?,
        profileImageUrl: String?,
        profilePlaceholder: String? = null,
        leftContent: @Composable RowScope.() -> Unit,
        centerContent: @Composable RowScope.() -> Unit,
        rightContent: @Composable RowScope.() -> Unit
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Background image
            NetworkImage(
                imageUrl = backgroundImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(235.dp)
            )

            // Foreground content container
            Box(
                modifier = Modifier
                    .padding(top = 164.dp)
                    .fillMaxWidth()
                    .heightIn(min = 124.dp)
                    .padding(horizontal = 36.dp, vertical = 32.dp)
                // You can add your blur modifier here
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        content = leftContent
                    )

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.Center,
                        content = centerContent
                    )

                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.End,
                        content = rightContent
                    )
                }

                // Profile image overlay
                profileImageUrl?.let {
                    NetworkImage(
                        imageUrl = profileImageUrl,
                        modifier = Modifier
                            .size(96.dp)
                            .align(Alignment.TopStart)
                            .offset(x = 62.dp)
                    )
                }
            }
        }
    }
}