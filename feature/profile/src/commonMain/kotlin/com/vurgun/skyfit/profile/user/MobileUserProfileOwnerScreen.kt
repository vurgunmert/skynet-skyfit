package com.vurgun.skyfit.profile.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.profile.user.model.UserProfileAction
import com.vurgun.skyfit.profile.user.model.UserProfileUiState
import com.vurgun.skyfit.profile.user.model.UserProfileViewModel
import dev.chrisbanes.haze.*

class UserProfileOwnerScreen : Screen {

    @Composable
    override fun Content() {

    }
}


private object ProfileScreenExpandedComponent {

}

@Composable
fun MobileUserProfileOwnerScreen_Expanded(viewModel: UserProfileViewModel) {

    val hazeState = rememberHazeState()
    val hazeStyle = HazeStyle(
        backgroundColor = SkyFitColor.background.surfaceSecondary,
        tints = listOf(
            HazeTint(SkyFitColor.background.surfaceSecondary.copy(alpha = 0.5f))
        ),
        blurRadius = 20.dp,
        noiseFactor = 0f
    )

    Column(
        Modifier.fillMaxWidth().padding(end = 16.dp),
    ) {
        //Header
        Box(
            modifier = Modifier
                .width(1171.dp)
                .heightIn(min = 288.dp)
        ) {
            SkyImage(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 36.dp)
                    .fillMaxWidth()
                    .height(235.dp)
                    .hazeSource(state = hazeState),
                url = "https://picsum.photos/300/300",
                contentScale = ContentScale.Crop,
                shapeOverride = RoundedCornerShape(20.dp)
            )

            //blurred
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .clip(RoundedCornerShape(24.dp))
                    .widthIn(min = 1171.dp)
                    .heightIn(min = 124.dp)
                    .hazeEffect(hazeState, hazeStyle)
                    .padding(horizontal = 36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier.height(60.dp).wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        Modifier
                            .size(84.dp, 60.dp)
                            .background(Color.White)
                    )
                    Spacer(Modifier.width(24.dp))
                    VerticalDivider(Modifier.size(0.dp, 52.dp))
                    Spacer(Modifier.width(24.dp))
                    Box(
                        Modifier
                            .size(84.dp, 60.dp)
                            .background(Color.White)
                    )
                    Spacer(Modifier.width(24.dp))
                    VerticalDivider(Modifier.size(0.dp, 52.dp))
                    Spacer(Modifier.width(24.dp))
                    Box(
                        Modifier
                            .size(84.dp, 60.dp)
                            .background(Color.White)
                    )
                }

                Row(
                    modifier = Modifier.height(48.dp).wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(Color.Blue, CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(Color.Blue, CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(Color.Blue, CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(Color.Blue, CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        Modifier
                            .size(40.dp)
                            .background(Color.Blue, CircleShape)
                    )
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
}


@Composable
fun MobileUserProfileOwnerScreen(
    viewModel: UserProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    when (uiState) {
        is UserProfileUiState.Loading -> FullScreenLoaderContent()
        is UserProfileUiState.Error -> {
            val message = (uiState as UserProfileUiState.Error).message
            ErrorScreen(message = message, onConfirm = { viewModel.onAction(UserProfileAction.ClickBack) })
        }

        is UserProfileUiState.Content -> {
            val content = uiState as UserProfileUiState.Content
//            UserProfileCompactComponents.Content(content, viewModel::onAction)
        }
    }
}
