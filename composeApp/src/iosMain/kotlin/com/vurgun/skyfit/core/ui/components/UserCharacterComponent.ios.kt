package com.vurgun.skyfit.core.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.CharacterType

@Composable
actual fun UserCharacterComponent(
    modifier: Modifier,
    characterType: CharacterType
) {
    SkyFitImageComponent(
        url =  characterType.toString(), //TODO: Replace
        modifier = modifier.size(240.dp)
    )
}