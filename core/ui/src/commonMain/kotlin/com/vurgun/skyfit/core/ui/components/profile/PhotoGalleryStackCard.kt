package com.vurgun.skyfit.core.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun PhotoGalleryStackCard(
    viewData: PhotoGalleryStackViewData,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
    ) {
        val imageSize = maxWidth
        val image2Size = maxWidth - 16.dp
        val image3Size = maxWidth - 32.dp

        NetworkImage(
            imageUrl = "https://opstudiohk.com/wp-content/uploads/2021/10/muscle-action.jpg",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(image3Size)
                .clip(RoundedCornerShape(16.dp))
        )

        NetworkImage(
            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjzqP-xQyE7dn40gt74e0fHTWbmnEIjnMJiw&s",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp)
                .size(image2Size)
                .clip(RoundedCornerShape(16.dp))
        )

        NetworkImage(
            imageUrl = "https://gymstudiohome.com/assets/img/slide/1.jpg",
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
                viewData.title.takeUnless { it.isNullOrEmpty() }?.let {
                    SkyText(
                        text = it,
                        styleType = TextStyleType.Heading4,
                    )
                }
                viewData.message.takeUnless { it.isNullOrEmpty() }?.let {
                    Spacer(Modifier.height(2.dp))
                    SkyText(
                        text = it,
                        styleType = TextStyleType.BodyMediumRegular,
                        color = SkyFitColor.text.secondary
                    )
                }
            }
        }
    }
}