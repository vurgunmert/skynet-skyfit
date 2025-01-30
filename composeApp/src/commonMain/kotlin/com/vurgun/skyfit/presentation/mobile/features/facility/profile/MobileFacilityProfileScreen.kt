package com.vurgun.skyfit.presentation.mobile.features.facility.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenPhotosComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenPrivateClassesComponent
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileVisitedScreen.MobileFacilityProfileVisitedScreenTrainersComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.profile.FacilityProfileVisitedViewModel
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityProfileScreen(navigator: Navigator) {

    val viewModel = FacilityProfileVisitedViewModel()
    val scrollState = rememberScrollState()
    val photos: List<Any> = listOf(1, 2, 3)
    val trainers = viewModel.trainers
    val privateClasses = viewModel.privateClasses

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        val width = maxWidth
        val imageHeight = width * 9 / 16

        AsyncImage(
            model = "https://cdn.shopify.com/s/files/1/0599/3624/3866/t/57/assets/e69266f5f9de--field-street-fitness-6-4a2977.jpg?v=1682607953",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
        )

        val contentTopPadding = imageHeight * 8 / 10

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(contentTopPadding))

            if (photos.isEmpty()) {
                MobileFacilityProfileScreenPhotosEmptyComponent(onClickAdd = {})
            } else {
                MobileFacilityProfileVisitedScreenPhotosComponent()
            }

            if (trainers.isEmpty()) {
                MobileFacilityProfileScreenTrainersEmptyComponent(onClickAdd = {})
            } else {
                MobileFacilityProfileVisitedScreenTrainersComponent(trainers)
            }

            if (privateClasses.isEmpty()) {
                MobileFacilityProfileScreenPrivateClassesEmptyComponent(onClickAdd = {})
            } else {
                MobileFacilityProfileVisitedScreenPrivateClassesComponent(privateClasses)
            }
        }
    }
}

@Composable
private fun MobileFacilityProfileScreenPhotosEmptyComponent(onClickAdd: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val componentSize = maxWidth

        Box(Modifier.size(componentSize), contentAlignment = Alignment.Center) {
            SkyFitButtonComponent(
                Modifier.wrapContentWidth(), text = "Fotoğraf Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.logo_skyfit)
            )
        }

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = "Salonu Kesfet",
                style = SkyFitTypography.heading4,
            )
        }
    }
}

@Composable
private fun MobileFacilityProfileScreenTrainersEmptyComponent(onClickAdd: () -> Unit) {
    Column(Modifier.padding(16.dp).fillMaxWidth()) {
        Text("Antrenorlerimiz", style = SkyFitTypography.bodyLargeSemibold)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .padding(vertical = 34.dp),
            contentAlignment = Alignment.Center
        ) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(),
                text = "Antrenör Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest
            )
        }
    }
}

@Composable
private fun MobileFacilityProfileScreenPrivateClassesEmptyComponent(onClickAdd: () -> Unit) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Text("Ozel Dersler", style = SkyFitTypography.bodyLargeSemibold)
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .padding(vertical = 34.dp),
            contentAlignment = Alignment.Center
        ) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(),
                text = "Ders Ekle",
                onClick = onClickAdd,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Medium,
                initialState = ButtonState.Rest
            )
        }
    }
}
