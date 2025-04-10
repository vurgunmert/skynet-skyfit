package com.vurgun.skyfit.ui.core.components.special

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import com.vurgun.skyfit.data.core.domain.model.CharacterType
import platform.UIKit.UIView
import RiveViewFactory

@Composable
actual fun UserCharacterComponent(modifier: Modifier, characterType: CharacterType) {

    UIKitView(
        modifier = modifier,
        factory = {
            RiveViewFactory.createRiveView("carrot.riv") as UIView
        }
    )
}