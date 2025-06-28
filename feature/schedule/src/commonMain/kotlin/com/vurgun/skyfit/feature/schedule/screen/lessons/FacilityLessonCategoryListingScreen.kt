package com.vurgun.skyfit.feature.schedule.screen.lessons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.v1.domain.facility.repository.FacilityRepository
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.form.SkyFormTextField
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.add_action
import fiwe.core.ui.generated.resources.delete_action
import fiwe.core.ui.generated.resources.ic_delete
import fiwe.core.ui.generated.resources.save_action

sealed class FacilityLessonCategoryListingUiState {
    data class Content(
        val categories: List<LessonCategory>
    ) : FacilityLessonCategoryListingUiState()

    data class Error(val message: String?) : FacilityLessonCategoryListingUiState()
    data object Loading : FacilityLessonCategoryListingUiState()
}

sealed interface FacilityLessonCategoryListingAction {
    data class CreateCategory(val name: String) : FacilityLessonCategoryListingAction
    data class UpdateCategory(val category: LessonCategory, val newName: String) :
        FacilityLessonCategoryListingAction

    data class DeleteCategory(val category: LessonCategory) : FacilityLessonCategoryListingAction
}

class FacilityLessonCategoryListingViewModel(
    private val facilityRepository: FacilityRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<FacilityLessonCategoryListingUiState>(
        FacilityLessonCategoryListingUiState.Loading
    )
    val uiState = _uiState.asStateFlow()

    private var activeFacilityId: Int = 0

    fun onAction(action: FacilityLessonCategoryListingAction) {
        when (action) {
            is FacilityLessonCategoryListingAction.CreateCategory ->
                createCategory(action.name)

            is FacilityLessonCategoryListingAction.DeleteCategory ->
                deleteCategory(action.category)

            is FacilityLessonCategoryListingAction.UpdateCategory ->
                updateCategory(action.category, action.newName)
        }
    }

    fun loadData(facilityId: Int) {
        activeFacilityId = facilityId
        screenModelScope.launch {
            runCatching {
                val categories = facilityRepository
                    .getLessonCategories(facilityId)
                    .getOrDefault(emptyList())

                _uiState.update(
                    FacilityLessonCategoryListingUiState.Content(
                        categories = categories
                    )
                )
            }.onFailure { error ->
                _uiState.update(FacilityLessonCategoryListingUiState.Error(error.message))
            }
        }
    }

    fun refreshData() {
        loadData(activeFacilityId)
    }

    fun deleteCategory(category: LessonCategory) {
        screenModelScope.launch {
            runCatching {
                facilityRepository.deleteLessonCategory(categoryId = category.id, activeFacilityId)
                refreshData()
            }.onFailure { error ->
                _uiState.update(FacilityLessonCategoryListingUiState.Error(error.message))
            }
        }
    }

    fun updateCategory(category: LessonCategory, newName: String) {
        screenModelScope.launch {
            runCatching {
                facilityRepository.updateLessonCategory(category.id, newName, activeFacilityId)
                refreshData()
            }.onFailure { error ->
                _uiState.update(FacilityLessonCategoryListingUiState.Error(error.message))
            }
        }
    }

    fun createCategory(name: String) {
        screenModelScope.launch {
            runCatching {
                facilityRepository.addLessonCategory(name, activeFacilityId)
                refreshData()
            }.onFailure { error ->
                _uiState.update(FacilityLessonCategoryListingUiState.Error(error.message))
            }
        }
    }
}

class FacilityLessonCategoryListingScreen(
    private val facilityId: Int
) : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinScreenModel<FacilityLessonCategoryListingViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.loadData(facilityId)
        }

        when (val state = uiState) {
            is FacilityLessonCategoryListingUiState.Content -> {
                ListingPage(state.categories, viewModel::onAction)
            }

            is FacilityLessonCategoryListingUiState.Error -> {
                ErrorScreen(message = state.message, onConfirm = { viewModel.refreshData() })
            }

            FacilityLessonCategoryListingUiState.Loading -> {
                FullScreenLoaderContent()
            }
        }
    }

    @Composable
    private fun ListingPage(
        categories: List<LessonCategory>,
        onAction: (FacilityLessonCategoryListingAction) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val focusManager = LocalFocusManager.current

        var newCategoryName by remember { mutableStateOf("") }

        val categoryNames = remember(categories) {
            categories.associate { it.id to mutableStateOf(it.name) }
        }

        // Focus requesters per field
        val focusRequesters = remember(categories) {
            categories.associate { it.id to FocusRequester() }
        }

        Scaffold(
            topBar = {
                CompactTopBar(title = "Ders Kategorileri", onClickBack = { navigator.pop() })
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .imePadding()
            ) {
                // Add New Category Section
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SkyFormTextField(
                            title = "Kategori Ekle",
                            hint = "Kategori adını belirtiniz. Örn: Pilates",
                            value = newCategoryName,
                            onValueChange = { newCategoryName = it },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(16.dp))

                        SkyButton(
                            label = stringResource(Res.string.add_action),
                            onClick = {
                                if (newCategoryName.isNotBlank()) {
                                    onAction(FacilityLessonCategoryListingAction.CreateCategory(newCategoryName.trim()))
                                    newCategoryName = ""
                                    focusManager.clearFocus()
                                }
                            },
                            size = SkyButtonSize.Medium,
                        )
                    }
                }

                // Editable Category Items
                items(categories) { category ->
                    Spacer(Modifier.height(16.dp))

                    val textState = categoryNames[category.id] ?: remember { mutableStateOf(category.name) }
                    val focusRequester = focusRequesters[category.id] ?: remember { FocusRequester() }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SkyFormTextField(
                            title = category.name,
                            hint = "Kategori adını güncelle. Örn: Yoga",
                            value = textState.value,
                            onValueChange = { textState.value = it },
                            focusRequester = focusRequester,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(Modifier.width(LocalPadding.current.medium))

                        if (textState.value.trim() != category.name) {
                            SkyButton(
                                label = stringResource(Res.string.save_action),
                                onClick = {
                                    onAction(
                                        FacilityLessonCategoryListingAction.UpdateCategory(
                                            category,
                                            textState.value.trim()
                                        )
                                    )
                                    focusManager.clearFocus()
                                },
                                size = SkyButtonSize.Micro
                            )
                        }

                        SkyIcon(
                            res = Res.drawable.ic_delete,
                            tint = SkyIconTint.Critical,
                            size = SkyIconSize.Medium,
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .clickable {
                                    onAction(FacilityLessonCategoryListingAction.DeleteCategory(category))
                                    focusManager.clearFocus()
                                },
                        )
                    }
                }
            }
        }
    }
}
