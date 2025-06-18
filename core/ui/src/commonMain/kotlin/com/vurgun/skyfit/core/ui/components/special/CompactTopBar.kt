package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_left

@Composable
fun CompactTopBar(
    title: String,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        SkyIcon(
            res = Res.drawable.ic_chevron_left,
            size = SkyIconSize.Small,
            onClick = onClickBack,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        SkyText(
            text = title,
            styleType = TextStyleType.BodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun ExpandedTopBar(
    title: String,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        SkyIcon(
            res = Res.drawable.ic_chevron_left,
            size = SkyIconSize.Small,
            onClick = onClickBack,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        SkyText(
            text = title,
            styleType = TextStyleType.BodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
