package com.vurgun.skyfit.feature.social.components.viewdata

data class SocialPostItemViewData(
    val postId: String,
    val username: String,
    val socialLink: String?,
    val timeAgo: String?,
    val profileImageUrl: String?,
    val content: String,
    val imageUrl: String?,
    val favoriteCount: Int,
    val commentCount: Int,
    val shareCount: Int,
)


val fakePosts: List<SocialPostItemViewData> = List(6) { index ->
    SocialPostItemViewData(
        postId = "post_${index + 1}",
        username = listOf("JohnDoe", "FitnessQueen", "MikeTrainer", "EmmaRunner", "DavidGym", "SophiaYoga").random(),
        socialLink = listOf("https://instagram.com/user", "https://twitter.com/user", "https://linkedin.com/user", null).random(),
        timeAgo = listOf("5 min ago", "2 hours ago", "1 day ago", "3 days ago", "1 week ago").random(),
        profileImageUrl = listOf(
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxNUUshHHqOs2sWxJAZPctGScPNewNivZn-w&s",
            "https://images.squarespace-cdn.com/content/v1/63f59136c5b45330af8a1b13/be8fe8de-0eec-49c6-9140-82a501e0422e/Screen+Shot+2023-04-19+at+3.01.08+PM.png",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxNUUshHHqOs2sWxJAZPctGScPNewNivZn-w&s",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0HX6fpDHt9hoOC5XPVJtHMGgLiXpcICKXfA&s",
            null
        ).random(),
        content = listOf(
            "Just finished an amazing workout! 💪",
            "Morning yoga session done! 🧘‍♀️",
            "Any tips for increasing stamina? 🏃‍♂️",
            "Trying out a new HIIT routine. 🔥",
            "Recovery day with some light stretching.",
            "Nutrition is key! What’s your go-to meal?"
        ).random(),
        imageUrl = listOf(
            "https://www.teatatutoasted.co.nz/cdn/shop/articles/Copy_of_Blog_pics_3_195ecc96-5c21-4363-b9c4-1e9458217175_768x.png?v=1727827333",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPfpidl_dC5LY4ASphW2qVybLkXqW5bI-BXg&s",
            "https://images.squarespace-cdn.com/content/v1/5885cce9e6f2e17ade281ea3/1729523033091-3GQ5CLQPRB5Z056ZKBYU/4.png?format=2500w",
            null,
            null,
            null,
            null
        ).random(),
        favoriteCount = (0..500).random(),
        commentCount = (0..200).random(),
        shareCount = (0..100).random(),
    )
}