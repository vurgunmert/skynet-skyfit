package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserPhotoDiaryScreen(navigator: Navigator) {

    var dietsExpanded: Boolean = false
    var exercisesExpanded: Boolean = false
    var showAddPhoto: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Fotograf Gunlugum", onBackClick = { navigator.popBackStack() })
        }
    ) {
        Box(Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MobileUserPhotoDiaryScreenDietsHeaderComponent()

                if (dietsExpanded) {
                    MobileUserPhotoDiaryScreenDietsComponent()
                } else {
                    MobileUserPhotoDiaryScreenDietsCompactComponent()
                }

                Spacer(Modifier.height(24.dp))

                MobileUserPhotoDiaryScreenExercisesHeaderComponent()

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = SkyFitColor.border.default
                )

                if (exercisesExpanded) {
                    MobileUserPhotoDiaryScreenExercisesComponent()
                } else {
                    MobileUserPhotoDiaryScreenExercisesCompactComponent()
                }
            }

            if (showAddPhoto) {
                MobileUserPhotoDiaryScreenAddPhotoComponent()
            }

        }
    }
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsHeaderComponent() {
    Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = SkyFitColor.icon.default
                    )
                    Text(
                        text = "Diyet",
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                }
                Text(
                    text = "Çarşamba, 28 Ağustos",
                    style = SkyFitTypography.bodySmall,
                    color = SkyFitColor.text.secondary
                )
            }

            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
        }
    }
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsCompactComponent() {
    TodoBox("MobileUserPhotoDiaryScreenDietsHeaderComponent", Modifier.size(398.dp, 414.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsComponent() {
    TodoBox("MobileUserPhotoDiaryScreenDietsHeaderComponent", Modifier.size(430.dp, 1258.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenExercisesHeaderComponent() {
    Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Row {
                    Icon(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = SkyFitColor.icon.default
                    )
                    Text(
                        text = "Egzersiz",
                        style = SkyFitTypography.bodyLargeSemibold
                    )
                }
                Text(
                    text = "Çarşamba, 28 Ağustos",
                    style = SkyFitTypography.bodySmall,
                    color = SkyFitColor.text.secondary
                )
            }

            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
        }
    }
}

@Composable
private fun MobileUserPhotoDiaryScreenExercisesCompactComponent() {
    TodoBox("MobileUserPhotoDiaryScreenExercisesCompactComponent", Modifier.size(398.dp, 414.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenExercisesComponent() {
    TodoBox("MobileUserPhotoDiaryScreenExercisesComponent", Modifier.size(430.dp, 1258.dp))
}


@Composable
private fun MobileUserPhotoDiaryScreenAddPhotoComponent() {
    TodoBox("MobileUserPhotoDiaryScreenAddPhotoComponent", Modifier.size(398.dp, 518.dp))
}
