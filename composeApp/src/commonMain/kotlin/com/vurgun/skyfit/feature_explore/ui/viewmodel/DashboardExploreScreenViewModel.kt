package com.vurgun.skyfit.feature_explore.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.feature_explore.ui.FacilityProfileCardItemViewData
import com.vurgun.skyfit.feature_explore.ui.TrainerProfileCardItemViewData

class DashboardExploreScreenViewModel : ViewModel() {

    val trainers = listOf(
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-5.jpg?updatedAt=1740259432295", "Lucas Bennett", 1800, 13, 32, 4.8),
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-9.jpg?updatedAt=1740259432298", "Olivia Hayes", 1500, 10, 20, 4.5),
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-9.jpg?updatedAt=1740259432298", "Mason Reed", 2000, 15, 40, 4.9),
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-11.jpg?updatedAt=1740259432273", "Sophia Hill", 1700, 12, 28, 4.7),
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-7.jpg?updatedAt=1740259432143", "Emma Johnson", 1600, 11, 25, 4.6),
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-6.jpg?updatedAt=1740259432118", "James Smith", 1900, 14, 35, 4.8),
        TrainerProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-10.jpg?updatedAt=1740259432035", "Ava Brown", 1750, 13, 30, 4.7)
    )

    val facilities = listOf(
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-3.jpg?updatedAt=1740259189441", "ironstudio", 2500, 12, 3.5),
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-4.jpg?updatedAt=1740259189394", "ironstudio2", 2500, 12, 3.5),
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-1.jpg?updatedAt=1740259189362", "ironstudio3", 2500, 12, 3.5),
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-2.jpg?updatedAt=1740259189345", "ironstudio4", 2500, 12, 3.5),
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download.jpg?updatedAt=1740259189264", "ironstudio5", 2500, 12, 3.5),
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/fake_facility_background.png?updatedAt=1739637015088", "ironstudio6", 2500, 12, 3.5),
        FacilityProfileCardItemViewData("https://ik.imagekit.io/skynet2skyfit/download-3.jpg?updatedAt=1740259189441", "ironstudio7", 2500, 12, 3.5),
    )
}