package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_app_logo
import fiwe.core.ui.generated.resources.ic_fiwe_logo_dark
import fiwe.core.ui.generated.resources.ic_fiwe_logo_light

@Composable
fun FiweLogoDark(modifier: Modifier = Modifier) {
    Image(
        painterResource(Res.drawable.ic_fiwe_logo_dark),
        "FIWE Logo Dark",
        modifier = modifier
    )
}


@Composable
fun FiweLogoLight(modifier: Modifier = Modifier) {
    Image(
        painterResource(Res.drawable.ic_fiwe_logo_light),
        "FIWE Logo Light",
        modifier = modifier
    )
}

@Composable
fun FiweLogoGroup(
    title: String? = null,
    subtitle: String? = null,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))
        Image(
            painterResource(Res.drawable.ic_fiwe_logo_dark),
            "FIWE Logo Dark",
            modifier = modifier
        )
        title.takeUnless { it.isNullOrEmpty() }?.let { text ->
            Spacer(Modifier.height(48.dp))
            SkyText(
                text = text,
                styleType = TextStyleType.Heading3
            )
        }

        subtitle.takeUnless { it.isNullOrEmpty() }?.let { text ->
            if (title.isNullOrEmpty()) {
                Spacer(Modifier.height(48.dp))
            } else {
                Spacer(Modifier.height(16.dp))
            }
            SkyText(
                text = text,
                styleType = TextStyleType.BodyMediumRegular,
                color = SkyFitColor.text.secondary
            )
        }
    }


    @Composable
    fun SkyFitAutoSizeLogo(maxWidth: Dp) {
        Image(
            painterResource(Res.drawable.ic_app_logo),
            null,
            modifier = Modifier.size(maxWidth * 0.6f)
        )
    }

}