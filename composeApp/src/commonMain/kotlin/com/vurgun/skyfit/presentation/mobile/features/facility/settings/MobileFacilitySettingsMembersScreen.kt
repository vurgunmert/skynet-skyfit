package com.vurgun.skyfit.presentation.mobile.features.facility.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilitySettingsMembersScreen(navigator: Navigator) {

    SkyFitScaffold(
        topBar = {
            Column {
                MobileFacilitySettingsSearchUserToolbarComponent(
                    title = "Uyeler",
                    onClickBack = { navigator.popBackStack() },
                    onClickAdd = { }
                )
                MobileFacilitySettingsSearchUserComponent()
            }
        }
    ) {
        MobileFacilitySettingsSearchResultsComponent()
    }
}

@Composable
fun MobileFacilitySettingsSearchUserToolbarComponent(
    title: String,
    onClickBack: () -> Unit,
    onClickAdd: () -> Unit
) {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        SkyFitScreenHeader(title, onClickBack = onClickBack)

        SkyFitButtonComponent(
            Modifier
                .padding(end = 24.dp)
                .wrapContentWidth(), text = "Ekle",
            onClick = onClickAdd,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Medium,
            initialState = ButtonState.Rest
        )
    }
}


@Composable
fun MobileFacilitySettingsSearchUserComponent() {
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitSearchTextInputComponent("Ara")
    }
}

@Composable
fun MobileFacilitySettingsSearchResultsComponent() {
    var items = listOf<Any>(1, 2, 3)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) {
            MobileFacilitySettingsSearchResultItemComponent()
        }
    }
}

@Composable
fun MobileFacilitySettingsSearchResultItemComponent() {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://img.freepik.com/free-psd/3d-illustration-person-with-sunglasses_23-2149436188.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.width(16.dp))
        Column(
            Modifier.weight(1f)
        ) {
            Text(
                text = "stephaniebrook",
                style = SkyFitTypography.bodyLargeSemibold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Stephanie Brook",
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary
            )
        }
        Spacer(Modifier.width(24.dp))
        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Sil",
            onClick = { },
            variant = ButtonVariant.Primary,
            size = ButtonSize.Medium,
            initialState = ButtonState.Rest
        )
    }
}