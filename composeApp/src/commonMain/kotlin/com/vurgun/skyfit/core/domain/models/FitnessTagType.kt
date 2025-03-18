package com.vurgun.skyfit.core.domain.models

sealed class FitnessTagType(val id: Int, val label: String) {
    data object CARDIO : FitnessTagType(1, "Kardiyo")
    data object MUSCLE_GROWTH : FitnessTagType(2, "Kas Gelişimi")
    data object FLEXIBILITY : FitnessTagType(3, "Esneklik")
    data object FUNCTIONAL_TRAINING : FitnessTagType(4, "Fonksiyonel Antrenman")
    data object HIIT : FitnessTagType(5, "HIIT")
    data object ENDURANCE : FitnessTagType(6, "Dayanıklılık")
    data object BODYBUILDING : FitnessTagType(7, "Vücut Geliştirme")
    data object SPORTS_NUTRITION : FitnessTagType(8, "Sporcu Beslenmesi")
    data object PROTEIN_INTAKE : FitnessTagType(9, "Protein Tüketimi")
    data object HYDRATION : FitnessTagType(10, "Hidrasyon")
    data object WORKOUT_PROGRAM : FitnessTagType(11, "Egzersiz Programı")
    data object METABOLISM_RATE : FitnessTagType(12, "Metabolizma Hızı")
    data object POSTURE_CORRECTION : FitnessTagType(13, "Postür Düzeltme")
    data object CORE_STRENGTHENING : FitnessTagType(14, "Çekirdek Güçlendirme")
    data object MENTAL_HEALTH : FitnessTagType(15, "Mental Sağlık")
    data object PHYSICAL_FITNESS : FitnessTagType(16, "Fiziksel Form")
    data object LOW_CARB : FitnessTagType(17, "Düşük Karbonhidrat")
    data object MUSCLE_RECOVERY : FitnessTagType(18, "Kas Onarımı")
    data object FUNCTIONAL_MOVEMENTS : FitnessTagType(19, "Fonksiyonel Hareketler")
    data object BALANCED_NUTRITION : FitnessTagType(20, "Dengeli Beslenme")

    companion object {
        fun from(id: Int): FitnessTagType? {
            return when (id) {
                1 -> CARDIO
                2 -> MUSCLE_GROWTH
                3 -> FLEXIBILITY
                4 -> FUNCTIONAL_TRAINING
                5 -> HIIT
                6 -> ENDURANCE
                7 -> BODYBUILDING
                8 -> SPORTS_NUTRITION
                9 -> PROTEIN_INTAKE
                10 -> HYDRATION
                11 -> WORKOUT_PROGRAM
                12 -> METABOLISM_RATE
                13 -> POSTURE_CORRECTION
                14 -> CORE_STRENGTHENING
                15 -> MENTAL_HEALTH
                16 -> PHYSICAL_FITNESS
                17 -> LOW_CARB
                18 -> MUSCLE_RECOVERY
                19 -> FUNCTIONAL_MOVEMENTS
                20 -> BALANCED_NUTRITION
                else -> null
            }
        }
        
        fun getAllTags(): List<FitnessTagType> = listOf(
            CARDIO, MUSCLE_GROWTH, FLEXIBILITY, FUNCTIONAL_TRAINING, HIIT, ENDURANCE, BODYBUILDING,
            SPORTS_NUTRITION, PROTEIN_INTAKE, HYDRATION, WORKOUT_PROGRAM, METABOLISM_RATE,
            POSTURE_CORRECTION, CORE_STRENGTHENING, MENTAL_HEALTH, PHYSICAL_FITNESS, LOW_CARB,
            MUSCLE_RECOVERY, FUNCTIONAL_MOVEMENTS, BALANCED_NUTRITION
        )
    }
}
