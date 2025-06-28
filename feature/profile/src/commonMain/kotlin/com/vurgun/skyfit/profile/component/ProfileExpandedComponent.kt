package com.vurgun.skyfit.profile.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.PrimaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.profile.model.ProfileDestination
import dev.chrisbanes.haze.*
import fiwe.core.ui.generated.resources.*

internal object ProfileExpandedComponent {

    @Composable
    fun Layout(
        header: @Composable () -> Unit,
        content: @Composable ColumnScope.() -> Unit,
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .padding(end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
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
        rightContent: @Composable RowScope.() -> Unit,
        modifier: Modifier = Modifier.fillMaxWidth()
    ) {

        val hazeState = rememberHazeState()
        val hazeStyle = HazeStyle(
            backgroundColor = SkyFitColor.background.surfaceSecondary,
            tints = listOf(
                HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.5f))
            ),
            blurRadius = 20.dp,
            noiseFactor = 0f
        )

        Box(modifier) {
            NetworkImage(
                imageUrl = backgroundImageUrl,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(1083.dp, 235.dp)
                    .hazeSource(state = hazeState)
            )

            Box(
                modifier = Modifier
                    .padding(top = 164.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .fillMaxWidth()
                    .height(124.dp)
                    .hazeEffect(hazeState, hazeStyle)
                    .padding(horizontal = 36.dp, vertical = 32.dp)
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
            }

            profileImageUrl?.let {
                NetworkImage(
                    imageUrl = profileImageUrl,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 96.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .size(100.dp)
                )
            }
        }
    }

    @Composable
    fun HeaderNavigationGroup(
        onClickAbout: (() -> Unit)? = null,
        onClickPosts: (() -> Unit)? = null,
        onClickMeasurements: (() -> Unit)? = null,
        onClickShare: (() -> Unit)? = null,
        destination: ProfileDestination,
    ) {

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    SkyFitColor.specialty.secondaryButtonRest,
                    RoundedCornerShape(size = 20.dp)
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onClickAbout != null) {
                if (destination == ProfileDestination.About) {
                    PrimaryIconButton(
                        res = Res.drawable.ic_clock,
                        iconModifier = Modifier.size(24.dp),
                        onClick = onClickAbout
                    )
                } else {
                    SecondaryIconButton(
                        res = Res.drawable.ic_clock,
                        iconModifier = Modifier.size(24.dp),
                        onClick = onClickAbout
                    )
                }
            }

            if (onClickPosts != null) {
                if (destination == ProfileDestination.Posts) {
                    PrimaryIconButton(
                        res = Res.drawable.ic_dashboard,
                        iconModifier = Modifier.size(24.dp),
                        onClick = onClickPosts
                    )
                } else {
                    SecondaryIconButton(
                        res = Res.drawable.ic_dashboard,
                        iconModifier = Modifier.size(24.dp),
                        onClick = onClickPosts
                    )
                }
            }

            if (onClickMeasurements != null) {
                if (destination == ProfileDestination.Measurements) {
                    PrimaryIconButton(
                        res = Res.drawable.ic_chart_pie,
                        iconModifier = Modifier.size(24.dp),
                        onClick = onClickMeasurements
                    )
                } else {
                    SecondaryIconButton(
                        res = Res.drawable.ic_chart_pie,
                        iconModifier = Modifier.size(24.dp),
                        onClick = onClickMeasurements
                    )
                }
            }

            if (onClickShare != null) {
                SecondaryIconButton(
                    res = Res.drawable.ic_share,
                    iconModifier = Modifier.size(24.dp),
                    onClick = onClickMeasurements
                )
            }
        }
    }
}