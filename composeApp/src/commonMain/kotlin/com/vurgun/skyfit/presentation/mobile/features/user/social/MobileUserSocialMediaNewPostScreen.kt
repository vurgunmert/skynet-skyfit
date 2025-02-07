package com.vurgun.skyfit.presentation.mobile.features.user.social

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSocialMediaNewPostScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserSocialMediaNewPostScreenToolbarComponent(
                onClickShare = {},
                onClickCancel = {}
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserSocialMediaNewPostScreenInputComponent()
        }
    }
}

@Composable
private fun MobileUserSocialMediaNewPostScreenToolbarComponent(onClickCancel: () -> Unit,
                                                               onClickShare: () -> Unit) {

    Row(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            "İptal",
            style = SkyFitTypography.bodyLarge,
            modifier = Modifier.clickable(onClick = onClickCancel)
        )
        Spacer(Modifier.weight(1f))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Paylaş",
            onClick = onClickShare,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileUserSocialMediaNewPostScreenInputComponent() {
    SkyFitTextInputComponent(
        hint = "Post content filling",
        value = "5 km koşu ve ardından 20 dakika core çalışması! Hedefime adım adım yaklaşıyorum \uD83D\uDCAA",
    )

}
