package com.vurgun.skyfit.feature_profile.ui.facility

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.feature_explore.ui.TrainerProfileCardItemViewData
import com.vurgun.skyfit.feature_profile.ui.fakePrivateTrainerClasses

class FacilityProfileVisitedViewModel: ViewModel() {

    val privateClasses = fakePrivateTrainerClasses

    val trainers = fakeTrainers
}

val fakeTrainers = listOf(
    TrainerProfileCardItemViewData("https://basebangkok.com/app/uploads/2021/12/Matt-WP.jpg", "Lucas Bennett", 1800, 13, 32, 4.8),
    TrainerProfileCardItemViewData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQTDhR7E86BCe_50BUZK3KU8N2Vafx9hnBy4mzuQ40v1MXYQ5dsTY8Zbn_kZ__Z0L3KBOA&usqp=CAU", "Olivia Hayes", 1500, 10, 20, 4.5),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/856797530/photo/portrait-of-a-beautiful-woman-at-the-gym.jpg?s=612x612&w=0&k=20&c=0wMa1MYxt6HHamjd66d5__XGAKbJFDFQyu9LCloRsYU=", "Mason Reed", 2000, 15, 40, 4.9),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/852401732/photo/happy-personal-trainer-working-at-the-gym.jpg?s=612x612&w=0&k=20&c=m4Wk3lVvjEFIHbiAfUuFNBwEhvvSgf4Vv5ib9JUsrJk=", "Sophia Hill", 1700, 12, 28, 4.7),
    TrainerProfileCardItemViewData("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqjbihw0jL4P6DvYHu4fI4oKlGqRSTL_gxjZ6LUoI4ZOU23m1VpHP9G__wigT5_4q-C1c&usqp=CAU", "Emma Johnson", 1600, 11, 25, 4.6),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/1018043738/photo/portrait-of-chinese-personal-trainer-in-gym.jpg?s=612x612&w=0&k=20&c=BpuYao5G3qr5SG7IUq15CESYHxunuvz66qfnDKJfX9s=", "James Smith", 1900, 14, 35, 4.8),
    TrainerProfileCardItemViewData("https://media.istockphoto.com/id/1018043738/photo/portrait-of-chinese-personal-trainer-in-gym.jpg?s=612x612&w=0&k=20&c=BpuYao5G3qr5SG7IUq15CESYHxunuvz66qfnDKJfX9s=", "Ava Brown", 1750, 13, 30, 4.7)
)