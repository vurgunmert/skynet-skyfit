package com.vurgun.skyfit.feature.persona.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

internal object ProfileExpandedComponent {

    @Composable
    fun Layout(
        modifier: Modifier = Modifier.Companion,
        isOwner: Boolean = true,
        topBar: @Composable (() -> Unit) = {},
        ownerHeader: @Composable (() -> Unit) = {},
        visitorHeader: @Composable (() -> Unit) = {},
        ownerContent: @Composable (() -> Unit) = {},
        visitorContent: @Composable (() -> Unit) = {}
    ) {
        Column(modifier = modifier) {
            topBar()

            Column(
                modifier = Modifier.Companion
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                if (isOwner) {
                    ownerHeader()
                    ownerContent()
                } else {
                    visitorHeader()
                    visitorContent()
                }
            }
        }
    }
}