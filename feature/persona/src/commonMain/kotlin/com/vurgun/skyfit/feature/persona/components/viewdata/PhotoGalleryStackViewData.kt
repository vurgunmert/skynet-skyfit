package com.vurgun.skyfit.feature.persona.components.viewdata

data class PhotoGalleryStackViewData(
    val title: String? = "Fotoğraf Günlüğüm",
    val message: String? = "Çarşamba, 28 Ağustos",
    val imageUrls: List<String> = listOf(
        "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
        "https://gymstudiohome.com/assets/img/slide/1.jpg",
    )
)