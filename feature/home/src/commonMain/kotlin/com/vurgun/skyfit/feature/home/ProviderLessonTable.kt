package com.vurgun.skyfit.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
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
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.ui.components.chip.RectangleChip
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.text.SkyInputTextField
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_chevron_down
import skyfit.core.ui.generated.resources.ic_close
import skyfit.core.ui.generated.resources.ic_filter
import skyfit.core.ui.generated.resources.ic_search

internal object ProviderLessonTable {
    @Composable
    fun ExpandedHomeFacilityLessonTable(items: List<LessonSessionItemViewData>) {
        var sessions by remember { mutableStateOf(items) }

        Column(
            modifier = Modifier.Companion
                .fillMaxWidth()
                .background(SkyFitColor.background.default, shape = RoundedCornerShape(16.dp))
        ) {
            ExpandedHomeFacilityLessonTableHeader()

            CourseSessionStableTable(
                sessions = sessions,
                onSelectionChange = { lessonId, selected ->
                    sessions = sessions.map {
                        if (it.lessonId == lessonId) it.copy(selected = selected) else it
                    }
                }
            )
        }
    }

    @Composable
    private fun ExpandedHomeFacilityLessonTableHeader() {
        var searchQuery by remember { mutableStateOf("") }

        Column(
            Modifier.Companion
                .fillMaxWidth()
                .background(
                    SkyFitColor.background.surfaceHover,
                    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.Companion.fillMaxWidth(),
                verticalAlignment = Alignment.Companion.CenterVertically
            ) {

                Row(
                    modifier = Modifier.Companion
                        .width(280.dp)
                        .background(SkyFitColor.background.surfaceSecondary)
                        .border(
                            1.dp,
                            SkyFitColor.border.secondaryButtonHover,
                            androidx.compose.foundation.shape.RoundedCornerShape(50)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    SkyInputTextField(
                        hint = "Tabloda ara",
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.Companion.weight(1f)
                    )

                    if (searchQuery.isNotBlank()) {
                        Spacer(Modifier.Companion.width(8.dp))
                        SkyIcon(
                            res = Res.drawable.ic_close,
                            size = SkyIconSize.Small
                        )
                    }
                }

                Spacer(Modifier.Companion.width(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    TableFilterChip("Aktif")
                    TableFilterChip("Pilates")
                    TableFilterChip("07:00-08:00")
                    TableFilterChip("22/11/2024")
                }

                Spacer(Modifier.Companion.weight(1f))

                Row(
                    Modifier.Companion
                        .background(
                            SkyFitColor.specialty.buttonBgRest,
                            androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp)
                ) {
                    SkyIcon(
                        res = Res.drawable.ic_search,
                        size = SkyIconSize.Small,
                        tint = SkyIconTint.Inverse
                    )
                    Spacer(Modifier.Companion.width(8.dp))
                    SkyIcon(
                        res = Res.drawable.ic_filter,
                        size = SkyIconSize.Small,
                        tint = SkyIconTint.Inverse
                    )
                }
            }

            Spacer(modifier = Modifier.Companion.height(8.dp))


        }
    }

    @Composable
    private fun TableFilterChip(label: String) {
        Row(
            modifier = Modifier.Companion
                .border(
                    1.dp,
                    SkyFitColor.border.secondaryButtonHover,
                    androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
                .padding(6.dp)
        ) {
            SkyText(
                text = label,
                styleType = TextStyleType.BodySmallSemibold
            )
            Spacer(Modifier.Companion.width(4.dp))
            SkyIcon(
                res = Res.drawable.ic_chevron_down,
                size = SkyIconSize.Small
            )
        }
    }

    @Composable
    private fun RowScope.TableCell(text: String, weight: Float) {
        Box(
            modifier = Modifier.Companion
                .weight(weight)
                .padding(6.dp),
            contentAlignment = Alignment.Companion.CenterStart
        ) {
            Text(text, fontSize = 14.sp, color = Color.Companion.White)
        }
    }

    @Composable
    private fun CourseSessionStableTable(
        sessions: List<LessonSessionItemViewData>,
        onSelectionChange: (Int, Boolean) -> Unit
    ) {
        Column(modifier = Modifier.Companion.fillMaxWidth()) {
            // Table Header
            Row(modifier = Modifier.Companion.fillMaxWidth().background(SkyFitColor.background.surfaceSecondary)) {
                TableCell(" ", weight = 0.5f)
                TableCell("Etkinliğin Adı", weight = 2f)
                TableCell("Saat", weight = 1f)
                TableCell("Tarih", weight = 1f)
                TableCell("Eğitmen", weight = 1f)
                TableCell("Durum", weight = 1f)
            }

            Divider(color = Color.Companion.DarkGray.copy(alpha = 0.4f), thickness = 1.dp)

            // Table Rows
            sessions.forEach { session ->
                Row(
                    modifier = Modifier.Companion.fillMaxWidth().background(Color.Companion.Black.copy(alpha = 0.03f)),
                    verticalAlignment = Alignment.Companion.CenterVertically
                ) {
                    Checkbox(
                        checked = session.selected,
                        onCheckedChange = { onSelectionChange(session.lessonId, it) },
                        modifier = Modifier.Companion.weight(0.5f).padding(4.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.Companion.CenterVertically,
                        modifier = Modifier.Companion.weight(2f).padding(4.dp)
                    ) {
                        SkyIcon(
                            iconId = session.iconId,
                            size = SkyIconSize.Medium,
                            modifier = Modifier.Companion
                        )
                        Spacer(modifier = Modifier.Companion.width(10.dp))
                        SkyText(
                            text = session.title,
                            styleType = TextStyleType.BodyMediumRegular
                        )
                    }

                    TableCell(session.hours.toString(), weight = 1f)
                    TableCell(session.date.toString(), weight = 1f)
                    TableCell(session.trainer.toString(), weight = 1f)

                    Box(modifier = Modifier.Companion.weight(1f).padding(6.dp)) {
                        LessonTableStatusBadge(isActive = session.isActive)
                    }
                }

                Divider(color = Color.Companion.DarkGray.copy(alpha = 0.2f), thickness = 0.5.dp)
            }
        }
    }

    @Composable
    private fun LessonTableStatusBadge(isActive: Boolean) {
        val textColor = when {
            isActive -> SkyFitColor.text.successOnBgFill
            else -> SkyFitColor.text.criticalOnBgFill
        }
        val backgroundColor = when {
            isActive -> SkyFitColor.background.surfaceSuccessActive
            else -> SkyFitColor.background.surfaceCriticalActive
        }
        val text = when {
            isActive -> "Aktif"
            else -> "Kullanım dışı"
        }

        RectangleChip(text, textColor = textColor, backgroundColor = backgroundColor)
    }
}