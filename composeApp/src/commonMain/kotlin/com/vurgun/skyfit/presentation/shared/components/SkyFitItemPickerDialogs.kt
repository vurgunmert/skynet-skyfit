package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.data.network.models.BodyType
import com.vurgun.skyfit.presentation.mobile.features.onboarding.WeightPicker
import com.vurgun.skyfit.presentation.mobile.features.onboarding.WeightUnitPicker
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_check
import skyfit.composeapp.generated.resources.ic_pencil


@Composable
fun WeightPickerDialog(
    initialWeight: Int? = 65,
    initialUnit: String? = "kg",
    onDismiss: () -> Unit,
    onWeightSelected: (Int, String) -> Unit
) {
    var selectedWeight by remember { mutableStateOf(initialWeight ?: 65) }
    var selectedWeightUnit by remember { mutableStateOf(initialUnit ?: "kg") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kilonuzu Seçin",
                    style = SkyFitTypography.bodyMediumRegular
                )
                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    WeightPicker(
                        selectedWeight = selectedWeight,
                        onWeightSelected = { selectedWeight = it }
                    )

                    WeightUnitPicker(
                        selectedWeightUnit = selectedWeightUnit,
                        onWeightUnitSelected = { selectedWeightUnit = it }
                    )
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Iptal", style = SkyFitTypography.bodyMediumSemibold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onWeightSelected(selectedWeight, selectedWeightUnit)
                            onDismiss()
                        }, shape = CircleShape, colors = ButtonDefaults.buttonColors(
                            backgroundColor = SkyFitColor.specialty.buttonBgRest
                        )
                    ) {
                        Text(
                            text = "Onayla",
                            style = SkyFitTypography.bodyMediumSemibold,
                            color = SkyFitColor.text.inverse
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HeightPickerDialog(
    initialHeight: Int? = 165,
    initialUnit: String? = "cm",
    onDismiss: () -> Unit,
    onHeightSelected: (Int, String) -> Unit
) {
    var selectedHeight by remember { mutableStateOf(initialHeight ?: 165) }
    var selectedHeightUnit by remember { mutableStateOf(initialUnit ?: "cm") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier.padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp)),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Boyunuzu Seçin",
                    style = SkyFitTypography.bodyMediumRegular
                )
                Spacer(Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    WeightPicker(
                        selectedWeight = selectedHeight,
                        onWeightSelected = { selectedHeight = it }
                    )

                    WeightUnitPicker(
                        selectedWeightUnit = selectedHeightUnit,
                        onWeightUnitSelected = { selectedHeightUnit = it }
                    )
                }

                Spacer(Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Iptal", style = SkyFitTypography.bodyMediumSemibold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onHeightSelected(selectedHeight, selectedHeightUnit)
                            onDismiss()
                        }, shape = CircleShape, colors = ButtonDefaults.buttonColors(
                            backgroundColor = SkyFitColor.specialty.buttonBgRest
                        )
                    ) {
                        Text(
                            text = "Onayla",
                            style = SkyFitTypography.bodyMediumSemibold,
                            color = SkyFitColor.text.inverse
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FitnessTagPickerDialog(
    initialTags: List<String> = listOf(),
    availableTags: List<String> = listOf(
        "Kardiyo", "Kas Gelişimi", "Esneklik", "Fonksiyonel Antrenman", "HIIT", "Dayanıklılık",
        "Vücut Geliştirme", "Sporcu Beslenmesi", "Protein Tüketimi", "Hidrasyon", "Egzersiz Programı",
        "Metabolizma Hızı", "Postür Düzeltme", "Çekirdek Güçlendirme", "Mental Sağlık", "Fiziksel Form",
        "Düşük Karbonhidrat", "Kas Onarımı", "Fonksiyonel Hareketler", "Dengeli Beslenme"
    ),
    onDismiss: () -> Unit,
    onTagsSelected: (List<String>) -> Unit
) {
    var selectedTags by remember { mutableStateOf(initialTags) }
    var unselectedTags by remember { mutableStateOf(availableTags - initialTags.toSet()) }

    fun toggleTagSelection(tag: String) {
        if (selectedTags.contains(tag)) {
            selectedTags = selectedTags - tag
            unselectedTags = (unselectedTags + tag).sorted()
        } else {
            if (selectedTags.size >= 5) {
                val removedTag = selectedTags.first()
                selectedTags = selectedTags.drop(1) + tag
                unselectedTags = (unselectedTags - tag + removedTag).sorted()
            } else {
                selectedTags = selectedTags + tag
                unselectedTags = unselectedTags - tag
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
                .fillMaxHeight(0.9f)
        ) {
            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Etiket Seçimi",
                    style = SkyFitTypography.bodyMediumRegular
                )
                Spacer(Modifier.height(12.dp))

                Text("Seçilen Etiketler", style = SkyFitTypography.bodyMediumSemibold)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    selectedTags.forEach { tag ->
                        SkyFitAccountSettingsProfileTagItemComponent(
                            value = tag,
                            enabled = true,
                            showClose = false,
                            onClick = { toggleTagSelection(tag) }
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Divider()
                Spacer(Modifier.height(12.dp))

                Text("Mevcut Etiketler", style = SkyFitTypography.bodyMediumSemibold)
                Spacer(Modifier.height(8.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    unselectedTags.forEach { tag ->
                        SkyFitAccountSettingsProfileTagItemComponent(
                            value = tag,
                            enabled = true,
                            onClick = { toggleTagSelection(tag) }
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal", style = SkyFitTypography.bodyMediumSemibold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onTagsSelected(selectedTags)
                            onDismiss()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SkyFitColor.specialty.buttonBgRest
                        )
                    ) {
                        Text(
                            text = "Onayla",
                            style = SkyFitTypography.bodyMediumSemibold,
                            color = SkyFitColor.text.inverse
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BodyTypePickerDialog(
    initialBodyType: BodyType = BodyType.NOT_DEFINED,
    onDismiss: () -> Unit,
    onBodyTypeSelected: (BodyType) -> Unit
) {
    var selectedBodyType by remember { mutableStateOf(initialBodyType) }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Vücut Tipinizi Seçin",
                    style = SkyFitTypography.bodyMediumRegular
                )
                Spacer(Modifier.height(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BodyType.entries.forEach { bodyType ->
                        val isSelected = selectedBodyType == bodyType
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedBodyType = bodyType }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = bodyType.turkishShort, style = SkyFitTypography.bodyMediumSemibold)
                            if (isSelected) {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_check),
                                    contentDescription = "Selected",
                                    tint = SkyFitColor.icon.success
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal", style = SkyFitTypography.bodyMediumSemibold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onBodyTypeSelected(selectedBodyType)
                            onDismiss()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SkyFitColor.specialty.buttonBgRest
                        )
                    ) {
                        Text(
                            text = "Onayla",
                            style = SkyFitTypography.bodyMediumSemibold,
                            color = SkyFitColor.text.inverse
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SingleTextInputDialog(
    title: String = "Giriş Yap",
    hint: String = "Metin girin",
    initialValue: String = "",
    onDismiss: () -> Unit,
    onValueSubmitted: (String) -> Unit
) {
    var textValue by remember { mutableStateOf(initialValue) }
    val focusRequester = remember { FocusRequester() }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, style = SkyFitTypography.bodyMediumSemibold)
                Spacer(Modifier.height(12.dp))

                SkyFitTextInputComponent(
                    hint = hint,
                    value = textValue,
                    onValueChange = { textValue = it },
                    focusRequester = focusRequester,
                    rightIconPainter = painterResource(Res.drawable.ic_pencil)
                )

                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("İptal", style = SkyFitTypography.bodyMediumSemibold)
                    }
                    Spacer(Modifier.width(16.dp))
                    Button(
                        onClick = {
                            onValueSubmitted(textValue)
                            onDismiss()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = SkyFitColor.specialty.buttonBgRest
                        )
                    ) {
                        Text(
                            text = "Onayla",
                            style = SkyFitTypography.bodyMediumSemibold,
                            color = SkyFitColor.text.inverse
                        )
                    }
                }
            }
        }
    }
}