package com.vurgun.skyfit.core.ui.components.special

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.data.persona.domain.model.CharacterType
import com.vurgun.skyfit.core.ui.model.CharacterTypeViewData
import org.jetbrains.compose.resources.painterResource

@Composable
expect fun AnimatedCharacterComponent(modifier: Modifier, characterType: CharacterType)

@Composable
fun CharacterImage(
    characterType: CharacterType,
    modifier: Modifier
) {
    val viewType = CharacterTypeViewData.from(characterType)
    Image(
        painter = painterResource(viewType.icon.res),
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
    )
}