package com.vurgun.skyfit.feature_explore.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.SkyFitSearchFilterBarComponent
import com.vurgun.skyfit.core.ui.components.SkyFitSearchTextInputComponent
import com.vurgun.skyfit.feature_explore.ui.viewmodel.DashboardExploreScreenViewModel
import com.vurgun.skyfit.feature_profile.ui.components.FacilityProfileCardItemBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileExploreFacilitiesScreen(rootNavigator: Navigator) {

    val viewModel = DashboardExploreScreenViewModel()
    val facilities = viewModel.facilities
    var isSearchVisible by remember { mutableStateOf(false) }

    SkyFitScaffold(
        topBar = {
            Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                SkyFitScreenHeader("Popüler Tesisler", onClickBack = {})
                Spacer(Modifier.height(16.dp))
                if (isSearchVisible) {
                    SkyFitSearchTextInputComponent()
                    Spacer(Modifier.height(16.dp))
                }
                SkyFitSearchFilterBarComponent(onEnableSearch = { isSearchVisible = it })
            }
        }
    ) {
        MobileExploreFacilitiesGridComponent(facilities)
    }
}

@Composable
private fun MobileExploreFacilitiesGridComponent(facilities: List<FacilityProfileCardItemViewData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // Vertical spacing
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Horizontal spacing
    ) {
        items(facilities) { trainer ->
            FacilityProfileCardItemBox(
                imageUrl = trainer.imageUrl,
                name = trainer.name,
                memberCount = trainer.memberCount,
                trainerCount = trainer.trainerCount,
                rating = trainer.rating ?: 0.0,
                onClick = { /* Handle click */ }
            )
        }
    }
}