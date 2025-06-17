package com.vurgun.skyfit.profile.facility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.profile.facility.owner.FacilityProfileViewModel

@Composable
fun FacilityProfileExpanded(viewModel: FacilityProfileViewModel) {

    Scaffold(
        topBar = {

        },
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                content = {

                }
            )
        }
    )
}
