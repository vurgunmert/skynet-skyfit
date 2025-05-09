package com.vurgun.skyfit.feature.dashboard.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.logo_skyfit

@Composable
fun MobileExploreBlogArticleDetailScreen(goToBack: () -> Unit) {

    var title: String = "5 Simple Habits to Boost Your..."

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(title, onClickBack = goToBack)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(22.dp))
            MobileExploreBlogArticlesComponent()
            Spacer(Modifier.height(24.dp))
            MobileExploreBlogArticleDetailScreen()
        }
    }
}

@Composable
private fun MobileExploreBlogArticleDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Article Header
        Text(
            text = "Achieving your fitness goals doesn’t always require drastic changes...",
            style = SkyFitTypography.bodyMediumRegular
        )

        // Article Content
        MobileExploreBlogArticleDetailScreenContent()

        // Article Image
        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier
                .width(382.dp)
                .height(178.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // More Articles Section
        Text(
            text = "Daha fazla makale",
            style = SkyFitTypography.bodyMediumRegular,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        MobileExploreBlogArticleDetailScreenFeaturedArticles()
    }
}

@Composable
private fun MobileExploreBlogArticleDetailScreenContent() {
    Text(
        text = """
        Staying hydrated is essential for overall performance and recovery. Water helps regulate body temperature, lubricates joints, and supports muscle function. 

        Warming up prepares your body for exercise by gradually increasing your heart rate and improving circulation to your muscles. Cooling down, on the other hand, helps reduce muscle soreness and improve flexibility.

        Consistency is key when it comes to seeing results. Try to set specific days and times for your workouts and stick to them as much as possible. Having a regular schedule not only builds discipline but also ensures your body has enough time to adapt and recover.

        Your diet fuels your workouts and plays a vital role in your progress. Make sure you're eating balanced meals that include lean protein, healthy fats, and complex carbohydrates.

        Muscle recovery happens during rest, especially when you're sleeping. Aim for 7-9 hours of quality sleep each night to allow your body to repair and recharge. Poor sleep can lead to fatigue, reduce motivation, and negatively affect performance.

        These five simple habits can have a huge impact on your workout results. Stay consistent, and the results will follow! 💪
        """.trimIndent(),
        style = SkyFitTypography.bodyMediumRegular
    )
}

@Composable
private fun MobileExploreBlogArticleDetailScreenFeaturedArticles() {
    val articles = MobileExploreBlogArticleDetailScreenGetFeaturedArticles()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(articles) { article ->
            MobileExploreBlogArticleDetailScreenFeaturedArticleItem(article)
        }
    }
}

@Composable
private fun MobileExploreBlogArticleDetailScreenFeaturedArticleItem(article: MobileExploreBlogArticleDetailScreenArticle) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .padding(12.dp)
    ) {
        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = article.title, style = SkyFitTypography.bodyMediumSemibold)
        Text(text = article.author, style = SkyFitTypography.bodySmall, color = SkyFitColor.text.secondary)
    }
}

private data class MobileExploreBlogArticleDetailScreenArticle(
    val title: String,
    val author: String,
    val imageResId: DrawableResource
)

private fun MobileExploreBlogArticleDetailScreenGetFeaturedArticles(): List<MobileExploreBlogArticleDetailScreenArticle> {
    return listOf(
        MobileExploreBlogArticleDetailScreenArticle("5 Simple Habits to Boost Your Workout Results", "Sarah L.", Res.drawable.logo_skyfit),
        MobileExploreBlogArticleDetailScreenArticle("The Ultimate Guide to Staying Motivated", "Jake Brown", Res.drawable.logo_skyfit)
    )
}
