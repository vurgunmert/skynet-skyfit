package com.vurgun.skyfit.presentation.mobile.features.user.nutrition


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealsScreen.MobileDashboardNutritionScreenDayOfWeekComponent
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealsScreen.MobileDashboardNutritionScreenMealEditActionComponent
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealsScreen.MobileDashboardNutritionScreenMealsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.nutrition.MobileUserMealsScreen.MobileDashboardNutritionScreenStatisticsComponent
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.utils.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_chevron_left
import skyfit.composeapp.generated.resources.ic_chevron_right
import skyfit.composeapp.generated.resources.ic_plus

@Composable
fun MobileUserMealsScreen(rootNavigator: Navigator) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    fun goToPreviousWeek() {
        selectedDate = selectedDate.minus(7, DateTimeUnit.DAY) // Go back 7 days
    }

    fun goToNextWeek() {
        selectedDate = selectedDate.plus(7, DateTimeUnit.DAY) // Go forward 7 days
    }

    SkyFitScaffold(
        topBar = {
            MobileDashboardNutritionScreenDayOfWeekComponent(
                selectedDate = selectedDate,
                onDateSelected = { newDate -> selectedDate = newDate },
                onPreviousWeek = { goToPreviousWeek() },
                onNextWeek = { goToNextWeek() }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MobileDashboardNutritionScreenStatisticsComponent()

            MobileDashboardNutritionScreenMealsComponent()

            MobileDashboardNutritionScreenMealEditActionComponent(onClick = {})
        }
    }
}

object MobileUserMealsScreen {
    @Composable
    fun MobileDashboardNutritionScreenDayOfWeekComponent(
        selectedDate: LocalDate,  // Selected date
        onDateSelected: (LocalDate) -> Unit, // Callback when user selects a new date
        onPreviousWeek: () -> Unit,  // Navigate previous week
        onNextWeek: () -> Unit       // Navigate next week
    ) {
        val daysOfWeek = listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz")

        // Find the start of the week (Monday as the first day)
        val startOfWeek = selectedDate.minus(selectedDate.dayOfWeek.ordinal, DateTimeUnit.DAY)
        val weekDays = (0..6).map { startOfWeek.plus(it, DateTimeUnit.DAY) } // 7 days from startOfWeek

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Left Arrow (Previous Week)
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_left),
                contentDescription = "Previous",
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
            Spacer(Modifier.width(4.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Weekday Labels
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            style = SkyFitTypography.bodyMediumSemibold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Date Selection
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(weekDays) { day ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (selectedDate == day) SkyFitColor.transparent else SkyFitColor.background.surfaceSecondary)
                                .border(
                                    2.dp,
                                    if (selectedDate == day) SkyFitColor.border.secondaryButton else SkyFitColor.transparent,
                                    CircleShape
                                )
                                .clickable { onDateSelected(day) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.dayOfMonth.toString(),
                                style = SkyFitTypography.bodyMediumRegular
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(4.dp))

            // Right Arrow (Next Week)
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_right),
                contentDescription = "Next",
                modifier = Modifier.size(24.dp),
                tint = SkyFitColor.icon.default
            )
        }
    }

    @Composable
    fun MobileDashboardNutritionScreenStatisticsComponent(
        proteinPercentage: Int = 40,
        proteinCurrent: Int = 452,
        proteinGoal: Int = 1130,
        fatPercentage: Int = 80,
        fatCurrent: Int = 1050,
        fatGoal: Int = 1312,
        carbPercentage: Int = 50,
        carbCurrent: Int = 360,
        carbGoal: Int = 720,
        totalCurrent: Int = 1862,
        totalGoal: Int = 3162
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                NutrientProgressBar("Protein", proteinPercentage, proteinCurrent, proteinGoal, Color(0xFFDC6E5A), Color(0xFF2C2222))
                Spacer(modifier = Modifier.height(12.dp))
                NutrientProgressBar("Yağlar", fatPercentage, fatCurrent, fatGoal, Color(0xFFCF6CE7), Color(0xFF31202F))
                Spacer(modifier = Modifier.height(12.dp))
                NutrientProgressBar("Karbohidratlar", carbPercentage, carbCurrent, carbGoal, Color(0xFF5ADAE7), Color(0xFF1E3B3F))
            }

            Spacer(modifier = Modifier.width(16.dp))

            CircularNutritionProgress(
                proteinPercentage = proteinPercentage,
                fatPercentage = fatPercentage,
                carbPercentage = carbPercentage,
                totalCurrent = totalCurrent,
                totalGoal = totalGoal
            )
        }
    }

    @Composable
    fun NutrientProgressBar(
        label: String,
        percentage: Int,
        current: Int,
        goal: Int,
        progressColor: Color,
        backgroundColor: Color
    ) {
        Column {
            Text(text = label, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$percentage%", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(backgroundColor),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(percentage / 100f)
                            .height(8.dp)
                            .background(progressColor)
                    )
                }
            }

            Text(
                text = "$current/$goal kcal",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }

    @Composable
    fun CircularNutritionProgress(
        proteinPercentage: Int,
        fatPercentage: Int,
        carbPercentage: Int,
        totalCurrent: Int,
        totalGoal: Int
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(120.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 10.dp.toPx()
                val startAngle = -90f

                // Draw Protein Ring
                drawArc(
                    color = Color(0xFFDC6E5A),
                    startAngle = startAngle,
                    sweepAngle = (proteinPercentage / 100f) * 360,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )

                // Draw Fat Ring
                drawArc(
                    color = Color(0xFFCF6CE7),
                    startAngle = startAngle + (proteinPercentage / 100f) * 360,
                    sweepAngle = (fatPercentage / 100f) * 360,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )

                // Draw Carb Ring
                drawArc(
                    color = Color(0xFF5ADAE7),
                    startAngle = startAngle + ((proteinPercentage + fatPercentage) / 100f) * 360,
                    sweepAngle = (carbPercentage / 100f) * 360,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Round)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$totalCurrent/$totalGoal",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Toplam",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }


    @Composable
    fun MobileDashboardNutritionScreenMealsComponent() {

        Column {
            MobileNutritionScreenMealCardItemComponent(
                mealTitle = "Kahvalti",
                mealDetail = "2 Yumurta ⋅ Peynirli Salata +2",
                mealCalorie = "800 kcal",
                onClickEdit = { }
            )
            Spacer(Modifier.height(16.dp))
            MobileNutritionScreenMealCardItemComponent(
                mealTitle = "Ogle Yemegi",
                onClickEdit = { }
            )
            Spacer(Modifier.height(16.dp))
            MobileNutritionScreenMealCardItemComponent(
                mealTitle = "Aksam Yemegi",
                mealDetail = "Et ⋅ Salata +2",
                mealCalorie = "600 kcal",
                onClickEdit = { }
            )
            Spacer(Modifier.height(16.dp))
            MobileNutritionScreenMealCardItemComponent(
                mealTitle = "Atistirmalik",
                onClickEdit = { }
            )
        }
    }

    @Composable
    fun MobileNutritionScreenMealCardItemComponent(
        mealTitle: String = "Akşam Yemeği",
        mealDetail: String? = null,
        mealCalorie: String? = null,
        onClickEdit: () -> Unit
    ) {

        Column(
            Modifier.fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = mealTitle,
                    style = SkyFitTypography.bodyLargeMedium,
                    modifier = Modifier.weight(1f)
                )

                if (mealDetail == null) {
                    SkyFitButtonComponent(
                        modifier = Modifier.wrapContentWidth(), text = "Ekle",
                        onClick = onClickEdit,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Micro,
                        state = ButtonState.Rest,
                        leftIconPainter = painterResource(Res.drawable.ic_plus)
                    )
                }
            }

            if (mealDetail != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = mealDetail,
                        style = SkyFitTypography.bodySmall.copy(SkyFitColor.text.secondary),
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = mealCalorie.toString(),
                        style = SkyFitTypography.bodyMediumRegular
                    )
                }
            }

        }
    }

    @Composable
    fun MobileDashboardNutritionScreenMealEditActionComponent(onClick: () -> Unit) {
        Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Ogun Ekle",
                onClick = onClick,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Medium,
                state = ButtonState.Rest,
                leftIconPainter = painterResource(Res.drawable.ic_plus)
            )
        }
    }
}