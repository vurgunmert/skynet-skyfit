package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import kotlinx.serialization.Serializable

@Serializable
data class SkyFitPolicy(val title: String = "", val content: String = "")

@Composable
fun MobileAppLegalDialog(
    policyId: String,
    onDismissRequest: () -> Unit
) {
    var policy by remember { mutableStateOf<SkyFitPolicy?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }


    if (policy != null || isLoading || error != null) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    policy?.title?.let { title ->
                        Text(
                            text = title,
                            style = SkyFitTypography.bodyMediumMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Loading, Error, or Content
                    when {
                        isLoading -> CircularProgressIndicator()
                        error != null -> Text(text = error ?: "", color = Color.Red)
                        policy != null -> {
                            policy?.content?.let { rawContent ->
                                val sections = parseContent(rawContent)
                                sections.forEach { section ->
                                    Text(
                                        text = section,
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Close Button
                    Button(
                        onClick = onDismissRequest,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Kapat")
                    }
                }
            }
        }
    }
}

// Helper function to parse content with delimiters
fun parseContent(rawContent: String): List<String> {
    // Split by "###" for sections, replace "/n" with newline characters
    return rawContent.split("###")
        .map { it.replace("/n", "\n").trim() }
        .filter { it.isNotEmpty() }
}
