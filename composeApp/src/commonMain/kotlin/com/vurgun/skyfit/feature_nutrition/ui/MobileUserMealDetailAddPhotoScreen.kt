package com.vurgun.skyfit.feature_nutrition.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.ui.components.button.SkyFitIconButton
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserMealDetailAddPhotoScreen(rootNavigator: Navigator) {

    SkyFitScaffold {
        Box(Modifier.fillMaxSize()) {

            MobileMealDetailAddPhotoScreenGuideComponent()

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(24.dp))
                MobileMealDetailAddPhotoScreenToolbarComponent { rootNavigator.popBackStack() }
                Spacer(Modifier.weight(1f))
                MobileMealDetailAddPhotoScreenMediaActionsComponent()
            }
        }
    }
}


@Composable
private fun MobileMealDetailAddPhotoScreenToolbarComponent(onClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp),
            onClick = onClick
        )
    }
}

@Composable
fun MobileMealDetailAddPhotoScreenGuideComponent() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SkyFitColor.background.default)
                .drawWithContent {
                    drawContent()

                    // Get center and radius for the cutout
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radius = size.width * 0.45f // Adjust circle size

                    drawIntoCanvas { canvas ->
                        val paint = Paint().apply {
                            color = Color.Transparent
                            blendMode = BlendMode.Clear // This makes the area transparent
                        }
                        canvas.drawCircle(
                            Offset(centerX, centerY),
                            radius,
                            paint
                        )
                    }
                }
        )

        // Place other elements inside the transparent cutout
        val mealPhotoUrl: String? = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg"

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = mealPhotoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(CircleShape)
            )
        }
    }
}


@Composable
private fun MobileMealDetailAddPhotoScreenMediaActionsComponent() {
    Box(
        Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .background(SkyFitColor.background.surfaceOpalTransparent, RoundedCornerShape(20.dp))
            .padding(horizontal = 48.dp, vertical = 16.dp)
    ) {

        Row {
            SkyFitIconButton(painterResource(Res.drawable.logo_skyfit))
            Spacer(Modifier.weight(1f))
            Box {
                SkyFitIconButton(painterResource(Res.drawable.logo_skyfit))
            }
            Spacer(Modifier.weight(1f))
            SkyFitIconButton(painterResource(Res.drawable.logo_skyfit))
        }
    }
}