package com.vurgun.skyfit.core.data.utility

import kotlin.math.abs
import kotlin.math.pow

class HealthUtils {

    fun calculateBMI(weightKg: Double, heightCm: Double): Double {
        return weightKg / ((heightCm / 100).pow(2))
    }

    fun calculateWHR(waist: Double, hip: Double): Double {
        return waist / hip
    }

    fun calculateShoulderWaistRatio(shoulder: Double, waist: Double): Double {
        return shoulder / waist
    }

    fun calculateChestWaistRatio(chest: Double, waist: Double): Double {
        return chest / waist
    }

    fun calculateArmSymmetry(rightArm: Double, leftArm: Double): Double {
        return abs(rightArm - leftArm)
    }

    fun calculateLegSymmetry(rightLeg: Double, leftLeg: Double): Double {
        return abs(rightLeg - leftLeg)
    }

    fun interpretFlexibility(sitReach: Double): String {
        return when {
            sitReach >= 25 -> "İyi"
            sitReach in 15.0..24.9 -> "Orta"
            else -> "Geliştirilebilir"
        }
    }

    fun interpretPushUp(pushUpCount: Int): String {
        return when (pushUpCount) {
            in 30..100 -> "Çok İyi"
            in 15..29 -> "İyi"
            in 0..14 -> "Geliştirilebilir"
            else -> "Yetersiz"
        }
    }

    fun calculateHLS(bmi: Double?, whr: Double?, pushUp: Int?, symmetry: Double?): Int {
        var score = 0
        if (bmi != null) score += if (bmi in 18.5..24.9) 10 else 5
        if (whr != null) score += if (whr < 0.9) 10 else 5
        if (pushUp != null) score += when (pushUp) {
            in 20..100 -> 10
            in 10..19 -> 7
            else -> 4
        }
        if (symmetry != null) score += if (symmetry < 2.0) 10 else 5
        return (score + 60).coerceAtMost(100)
    }

}