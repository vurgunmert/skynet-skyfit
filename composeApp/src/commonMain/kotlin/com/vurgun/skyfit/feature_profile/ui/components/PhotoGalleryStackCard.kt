package com.vurgun.skyfit.feature_profile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.PhotoGalleryStackViewData

@Composable
fun PhotoGalleryStackCard(
    viewData: PhotoGalleryStackViewData,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
    ) {
        val imageSize = maxWidth
        val image2Size = maxWidth - 16.dp
        val image3Size = maxWidth - 32.dp

        AsyncImage(
            model = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(image3Size)
                .clip(RoundedCornerShape(16.dp))
        )

        AsyncImage(
            model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .size(image2Size)
                .clip(RoundedCornerShape(16.dp))
        )

        AsyncImage(
            model = "https://gymstudiohome.com/assets/img/slide/1.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .size(imageSize)
                .clip(RoundedCornerShape(16.dp))
        )

        Box(
            Modifier.align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column {
                if (!viewData.title.isNullOrEmpty()) {
                    Text(
                        text = viewData.title,
                        style = SkyFitTypography.heading4,
                    )
                }
                Spacer(Modifier.height(2.dp))
                if (!viewData.message.isNullOrEmpty()) {
                    Text(
                        text = viewData.message, style = SkyFitTypography.bodyMediumRegular,
                        color = SkyFitColor.text.secondary
                    )
                }
            }
        }
    }
}