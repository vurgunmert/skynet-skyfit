package com.vurgun.skyfit.feature.persona.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserRole
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.persona.profile.facility.owner.FacilityProfileOwnerScreen
import com.vurgun.skyfit.feature.persona.profile.trainer.owner.TrainerProfileOwnerScreen
import com.vurgun.skyfit.feature.persona.profile.user.UserProfileScreen
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerScreen

class ProfileScreen : Screen {

    override val key: ScreenKey
        get() = "profile"

    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        val screen = when (userRole) {
            UserRole.Facility -> FacilityProfileOwnerScreen()
            UserRole.Trainer -> TrainerProfileOwnerScreen()
            UserRole.User -> UserProfileScreen()
            UserRole.Guest -> UnauthorizedAccessScreen()
        }

        Navigator(screen) { profileNavigation ->
            CrossfadeTransition(profileNavigation)
        }
    }
}

//
//Header; -> ProfileExpandedHeader & ProfileCompactHeader,
//Content -> ProfileExpandedContent & ProfileCompactContent;
//
//ProfileExpandedHeader
//        Box
//            Image
//            Row
//                Blur(base image)
//                Row, Row, Row
//            Image
//
//
//ProfileCompactHeader
//        Box
//            Image
//        Column
//            Blur
//            Row
//            Text?
//            Text?
//
//ProfileCompactNavigationMenu
//        Text -> ProfileAction.OnClickActivities
//        Text -> Profileaction.OnClickPosts
//        Icon? -> Profileaction.OnClickSettings & Profileaction.OnClickAddPost
//
//ProfileCompactActivitiesComponents(
//    FacilityMembershipPackage
//    MyAppointments
//    MyDiet
//    MyMeasurements
//    MyPosts
//    MyLessons
//    MyLessonsWeekly
//    TrainerProfilePreviewCards
//);
//
//TrainerScheduleCompactComponents
//    -Header;
//    +Calendar;
//    +InteractiveLessons
//    +BookAppointmentAction
//
//FacilityScheduleCompactComponents
//    -Header;
//    +Calendar;
//    +InteractiveLessons
//    +BookAppointmentAction