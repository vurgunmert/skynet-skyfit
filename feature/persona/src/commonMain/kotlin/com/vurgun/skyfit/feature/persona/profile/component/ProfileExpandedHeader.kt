package com.vurgun.skyfit.feature.persona.profile.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage

@Composable
fun ProfileExpandedHeader(
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
