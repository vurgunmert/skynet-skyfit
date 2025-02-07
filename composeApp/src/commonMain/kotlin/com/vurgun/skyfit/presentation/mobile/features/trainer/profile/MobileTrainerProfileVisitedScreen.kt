package com.vurgun.skyfit.presentation.mobile.features.trainer.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileVisitedProfileActionsComponent
import com.vurgun.skyfit.presentation.mobile.features.user.profile.ProfilePreferenceItem
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.features.profile.ProfileCardVerticalDetailItemComponent
import com.vurgun.skyfit.presentation.shared.features.profile.VerticalDetailDivider
import com.vurgun.skyfit.presentation.shared.features.trainer.SkyFitTrainerProfileViewModel
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileTrainerProfileVisitedScreen(navigator: Navigator) {

    val viewModel = SkyFitTrainerProfileViewModel()
    val scrollState = rememberScrollState()
    var showPosts: Boolean = false
    val followed: Boolean = false
    val specialities: List<Any> = emptyList()
    val privateClasses = viewModel.privateClasses
    val posts = viewModel.posts

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            BoxWithConstraints {
                val width = maxWidth
                val imageHeight = width * 9 / 16
                val contentTopPadding = imageHeight * 3 / 10

                MobileTrainerProfileBackgroundImageComponent(imageHeight)

                Column(
                    Modifier
                        .padding(top = contentTopPadding)
                        .fillMaxWidth()
                ) {
                    MobileTrainerProfileInfoCardComponent(
                        name = "Trainer Solice",
                        social = "@dexteretymo",
                        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJq8Cfy_pOdcJOYIQew3rWrnwwxfc8bZIarg&s",
                        preferences = listOf(
                            ProfilePreferenceItem("Boy", "175"),
                            ProfilePreferenceItem("Kilo", "175"),
                            ProfilePreferenceItem("Vucut Tipi", "Ecto"),
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    MobileTrainerProfileVisitedScreenActionsComponent(
                        onClickAbout = { showPosts = false },
                        onClickPosts = { showPosts = true },
                        onClickMessage = {}
                    )
                }

                MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack = { navigator.popBackStack() })
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (showPosts) {
                MobileTrainerProfilePostsComponent(posts)
            } else {

                MobileTrainerProfileVisitedScreenSpecialitiesComponent(specialities)

                Spacer(Modifier.height(16.dp))

                MobileTrainerProfilePrivateClassesComponent(privateClasses)
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenToolbarComponent(onClickBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 24.dp)) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = onClickBack)
        )
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenInfoCardComponent(
    onClickFollow: () -> Unit,
    onClickUnFollow: () -> Unit,
    onClickCalendar: () -> Unit,
    onClickMessage: () -> Unit,
) {
    var isFollowing: Boolean = true

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "item.name",
                    style = SkyFitTypography.bodyLargeSemibold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "item.socialDisplayLink.orEmpty()",
                    style = SkyFitTypography.bodySmallMedium,
                    color = SkyFitColor.text.secondary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileCardVerticalDetailItemComponent(title = "${333}", subtitle = "Takipçi")
                VerticalDetailDivider()
                ProfileCardVerticalDetailItemComponent(title = "${22}", subtitle = "Ozel Ders")
                VerticalDetailDivider()
                ProfileCardVerticalDetailItemComponent(title = "${123}", subtitle = "Paylasimlar")
            }

            Text(
                text = "item.bio.orEmpty()",
                style = SkyFitTypography.bodySmall,
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.logo_skyfit),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = "item.location",
                    style = SkyFitTypography.bodySmallSemibold,
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
                )
            }

            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(),
                text = if (isFollowing) "Takipten Çık" else "Takip Et",
                onClick = if (isFollowing) onClickUnFollow else onClickFollow,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                SkyFitButtonComponent(
                    modifier = Modifier.weight(1f),
                    text = "Randevu Al",
                    onClick = onClickCalendar,
                    variant = ButtonVariant.Secondary,
                    size = ButtonSize.Large,
                    leftIconPainter = painterResource(Res.drawable.logo_skyfit)
                )
                if (isFollowing) {
                    Spacer(modifier = Modifier.width(10.dp))
                    SkyFitIconButton(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        modifier = Modifier.size(44.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenActionsComponent(
    onClickAbout: () -> Unit,
    onClickPosts: () -> Unit,
    onClickMessage: () -> Unit
) {

    Row(Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
        MobileVisitedProfileActionsComponent(
            Modifier.weight(1f),
            onClickAbout,
            onClickPosts
        )
        Spacer(Modifier.width(16.dp))
        Box(
            Modifier.size(56.dp)
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                .clickable(onClick = onClickMessage), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Message",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun MobileTrainerProfileVisitedScreenSpecialitiesComponent(specialities: List<Any>) {
    if (specialities.isNotEmpty()) {
        MobileTrainerProfileSpecialitiesComponent(specialities)
    }
}