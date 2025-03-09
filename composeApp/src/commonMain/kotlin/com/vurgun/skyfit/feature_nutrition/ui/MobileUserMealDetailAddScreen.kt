package com.vurgun.skyfit.feature_nutrition.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.SkyFitTextInputComponent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserMealDetailAddScreen(rootNavigator: Navigator) {

    val userMealDetailAddViewModel: Any = 1
    val detailNavigator = rememberNavigator()

    val onComplete: () -> Unit = {
        rootNavigator.popBackStack()
    }

    NavHost(
        navigator = detailNavigator,
        initialRoute = NavigationRoute.UserMealDetailAdd.route
    ) {
        scene(NavigationRoute.UserMealDetailAdd.route) {
            MobileUserMealDetailAddInputScreen(
                onClickBack = { rootNavigator.popBackStack() },
                onClickCapturePhoto = { detailNavigator.jumpAndStay(NavigationRoute.UserMealDetailAddPhoto) },
                onClickSave = { rootNavigator.navigate(NavigationRoute.DashboardNutrition.route) }
            )
        }
        scene(NavigationRoute.UserMealDetailAddPhoto.route) {
            MobileUserMealDetailAddPhotoScreen(detailNavigator)
        }
    }
}

@Composable
private fun MobileUserMealDetailAddInputScreen(
    onClickBack: () -> Unit,
    onClickCapturePhoto: () -> Unit,
    onClickSave: () -> Unit
) {

    val showSave: Boolean = true
    val photoUrl: String? = ""

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Besin Ekle", onClickBack = onClickBack)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            MobileMealDetailAddScreenInputComponent()
            Spacer(Modifier.height(12.dp))
            MobileMealDetailAddScreenAddPhotoActionComponent(photoUrl, onClickCapturePhoto)
            Spacer(Modifier.height(16.dp))
            if (showSave) {
                MobileMealDetailAddScreenSaveActionComponent(onClick = onClickSave)
            }
        }
    }
}

@Composable
private fun MobileMealDetailAddScreenInputComponent() {

    var name: String = "Yumurta"
    var count: String = "1 Adet"
    var timing: String = "1 Gun"
    var calorieCount: String = "75 kcal"

    Spacer(Modifier.height(24.dp))
    Text("Besin Adı", style = SkyFitTypography.bodySmallSemibold)
    Spacer(Modifier.height(4.dp))
    SkyFitTextInputComponent(
        hint = "Ad Soyad *",
        value = name,
        onValueChange = { },
        leftIconPainter = painterResource(Res.drawable.logo_skyfit),
        rightIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(24.dp))
    Text("Miktar", style = SkyFitTypography.bodySmallSemibold)
    Spacer(Modifier.height(4.dp))
    SkyFitTextInputComponent(
        hint = "Ad Soyad *",
        value = count,
        onValueChange = { },
        leftIconPainter = painterResource(Res.drawable.logo_skyfit),
        rightIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(24.dp))
    Text("Zaman Aralığı", style = SkyFitTypography.bodySmallSemibold)
    Spacer(Modifier.height(4.dp))
    SkyFitTextInputComponent(
        hint = "Ad Soyad *",
        value = timing,
        onValueChange = { },
        leftIconPainter = painterResource(Res.drawable.logo_skyfit),
        rightIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(24.dp))
    Text("Kalori Miktrai", style = SkyFitTypography.bodySmallSemibold)
    Spacer(Modifier.height(4.dp))
    SkyFitTextInputComponent(
        hint = "Kalorie",
        value = calorieCount,
        onValueChange = { },
        leftIconPainter = painterResource(Res.drawable.logo_skyfit),
        rightIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}

@Composable
private fun MobileMealDetailAddScreenAddPhotoActionComponent(
    photoUrl: String? = null,
    onClick: () -> Unit
) {

    Box(Modifier.fillMaxWidth().padding(16.dp)) {

        if (photoUrl.isNullOrEmpty()) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Besin Ekle",
                onClick = onClick,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.logo_skyfit)
            )
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.weight(1f))

                SkyFitButtonComponent(
                    modifier = Modifier.wrapContentWidth(), text = "Besin Ekle",
                    onClick = onClick,
                    variant = ButtonVariant.Secondary,
                    size = ButtonSize.Medium,
                    state = ButtonState.Rest,
                    leftIconPainter = painterResource(Res.drawable.logo_skyfit)
                )
            }
        }
    }
}

@Composable
private fun MobileMealDetailAddScreenSaveActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Kaydet",
        onClick = onClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        state = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}