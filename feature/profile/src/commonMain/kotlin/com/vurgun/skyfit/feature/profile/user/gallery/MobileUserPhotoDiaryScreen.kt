package com.vurgun.skyfit.feature.profile.user.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.ui.core.components.button.SkyFitIconButton
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.logo_skyfit

@Composable
fun MobileUserPhotoDiaryScreen(
    goToBack: () -> Unit
) {

    var dietsExpanded: Boolean = false
    var exercisesExpanded: Boolean = false
    var showAddPhoto: Boolean = false

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Fotograf Gunlugum", onClickBack = goToBack)
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

                MobileUserPhotoDiaryScreenExercisesCompactComponent()
            }

            if (showAddPhoto) {
                MobileUserPhotoDiaryScreenAddPhotoComponent(
                    imageUri = null,
                    onDismiss = { },
                    onChangePhoto = { },
                    onSavePhoto = { }
                )
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
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val imageSize = maxWidth
        val image2Size = maxWidth - 16.dp
        val image3Size = maxWidth - 32.dp

        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(image3Size)
                .clip(RoundedCornerShape(16.dp))
        )

        NetworkImage(
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .size(image2Size)
                .clip(RoundedCornerShape(16.dp))
        )

        NetworkImage(
            imageUrl = "https://gymstudiohome.com/assets/img/slide/1.jpg",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(16.dp))
        )

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Fotoğraf Günlüğüm",
                    style = SkyFitTypography.heading4,
                )
                Text(
                    text = "Çarşamba, 28 Ağustos", style = SkyFitTypography.bodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
        }
    }
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsComponent() {
    var dietItems = listOf(1, 2, 3, 4)

    LazyColumn(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dietItems) {
            MobileUserPhotoDiaryScreenDietItemComponent()
        }
    }
}

@Composable
private fun MobileUserPhotoDiaryScreenDietItemComponent() {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val imageSize = maxWidth

        NetworkImage(
            imageUrl = "https://gymstudiohome.com/assets/img/slide/1.jpg",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(16.dp))
        )

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = "“Bir sonraki öğününde de bu dengeyi korumaya çalış! Eğer eklemek istersen, yanında biraz sağlıklı yağlar da ekleyebilirsin. Böylece tabağın daha da besleyici olur. İyi iş çıkardın, devam et!\"",
                    style = SkyFitTypography.heading4,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(16.dp))
                SkyFitIconButton(painter = painterResource(Res.drawable.logo_skyfit))
            }
        }
    }
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
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val imageSize = maxWidth
        val image2Size = maxWidth - 16.dp
        val image3Size = maxWidth - 32.dp

        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(image3Size)
                .clip(RoundedCornerShape(16.dp))
        )

        NetworkImage(
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .size(image2Size)
                .clip(RoundedCornerShape(16.dp))
        )

        NetworkImage(
            imageUrl = "https://gymstudiohome.com/assets/img/slide/1.jpg",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(16.dp))
        )

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row {
                Text(
                    text = "Henuz yorum yok",
                    style = SkyFitTypography.heading4,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(16.dp))
                SkyFitIconButton(painter = painterResource(Res.drawable.logo_skyfit))
            }
        }
    }
}

@Composable
fun MobileUserPhotoDiaryScreenAddPhotoComponent(
    imageUri: String?,
    onDismiss: () -> Unit,
    onChangePhoto: () -> Unit,
    onSavePhoto: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF0B1D23)) // Dark themed background
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Fotoğraf Ekle",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    IconButton(onClick = { onDismiss() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }

                // Image Preview
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    imageUri?.let {
                        NetworkImage(
                            imageUrl = it,
                            modifier = Modifier.fillMaxSize()
                        )
                    } ?: Text("Fotoğraf Ekleyin", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.Red
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("İptal")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onChangePhoto,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Değiştir")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onSavePhoto,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF37D67A)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Kaydet", color = Color.White)
                    }
                }
            }
        }
    }
}

