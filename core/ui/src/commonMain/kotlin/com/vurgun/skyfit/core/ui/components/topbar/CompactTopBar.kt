package com.vurgun.skyfit.core.ui.components.topbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconButton
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_chevron_left

object CompactTopBar {

    @Composable
    fun TopBarWithTitle(
        title: String,
        onClickBack: (() -> Unit)? = null,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            onClickBack?.let {
                SkyIconButton(
                    res = Res.drawable.ic_chevron_left,
                    size = SkyIconSize.Small,
                    onClick = onClickBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }

            SkyText(
                text = title,
                styleType = TextStyleType.BodyLargeSemibold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun TopbarWithEndAction(
        title: String,
        actionLabel: String,
        onClickBack: (() -> Unit)? = null,
        onClickAction: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier.fillMaxWidth().padding(vertical = 12.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            TopBarWithTitle(title, onClickBack = onClickBack)

            SkyButton(
                label = actionLabel,
                variant = SkyButtonVariant.Primary,
                size = SkyButtonSize.Micro,
                onClick = onClickAction,
                modifier = Modifier.padding(end = 24.dp)
            )
        }
    }
}