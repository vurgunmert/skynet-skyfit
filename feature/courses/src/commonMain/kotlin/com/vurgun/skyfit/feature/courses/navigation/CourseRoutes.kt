//package com.vurgun.skyfit.feature.courses.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.vurgun.skyfit.feature.courses.screen.FacilityLessonEditViewModel
//import com.vurgun.skyfit.feature.courses.screen.MobileFacilityLessonCreatedScreen
//import com.vurgun.skyfit.feature.courses.screen.MobileFacilityEditLessonScreen
//import com.vurgun.skyfit.feature.courses.screen.MobileFacilityLessonListScreen
//import kotlinx.serialization.Serializable
//import org.koin.compose.viewmodel.koinViewModel
//
//internal sealed interface CourseLessonRoute {
//    val route: String
//
//    @Serializable
//    data object Listing : CourseLessonRoute {
//        override val route: String = "facility/courses/lessons/listing"
//    }
//
//    @Serializable
//    data object New : CourseLessonRoute {
//        override val route: String = "facility/courses/lessons/new"
//    }
//
//    @Serializable
//    data object Edit : CourseLessonRoute {
//        override val route: String = "facility/courses/lessons/edit"
//    }
//
//    @Serializable
//    data object Created : CourseLessonRoute {
//        override val route: String = "facility/courses/lessons/created"
//    }
//}
//
//@Serializable
//data object FacilityCoursesMainRoute
//
//fun NavGraphBuilder.courseLessonsRoutes(
//    onExit: () -> Unit,
//    onHome: () -> Unit
//) {
//    composable<FacilityCoursesMainRoute> {
//        FacilityManageCourseLessonGraph(onExit, onHome)
//    }
//}
//
//@Composable
//private fun FacilityManageCourseLessonGraph(
//    onExit: () -> Unit,
//    onHome: () -> Unit
//) {
//    val courseNavController = rememberNavController()
//    val editLessonViewModel: FacilityLessonEditViewModel = koinViewModel()
//
//    NavHost(
//        navController = courseNavController,
//        startDestination = CourseLessonRoute.Listing
//    ) {
//        composable<CourseLessonRoute.Listing> {
//            MobileFacilityLessonListScreen(
//                goToBack = onExit,
//                onNewLesson = {
//                    editLessonViewModel.loadLesson(null)
//                    courseNavController.navigate(CourseLessonRoute.New)
//                },
//                onEditLesson = {
//                    editLessonViewModel.loadLesson(it)
//                    courseNavController.navigate(CourseLessonRoute.Edit)
//                }
//            )
//        }
//
//        composable<CourseLessonRoute.New> {
//            MobileFacilityEditLessonScreen(
//                goToBack = courseNavController::popBackStack,
//                goToLessonCreated = { courseNavController.navigate(CourseLessonRoute.Created) },
//                viewModel = editLessonViewModel
//            )
//        }
//
//        composable<CourseLessonRoute.Edit> {
//            MobileFacilityEditLessonScreen(
//                goToBack = courseNavController::popBackStack,
//                goToLessonCreated = { courseNavController.navigate(CourseLessonRoute.Created) },
//                viewModel = editLessonViewModel
//            )
//        }
//
//        composable<CourseLessonRoute.Created> {
//            MobileFacilityLessonCreatedScreen(
//                goToListing = {
//                    courseNavController.navigate(CourseLessonRoute.Listing.route) {
//                        popUpTo(CourseLessonRoute.Listing.route) { inclusive = false }
//                        launchSingleTop = true
//                    }
//                },
//                goToNewLesson = {
//                    editLessonViewModel.loadLesson(null)
//                    courseNavController.navigate(CourseLessonRoute.New.route) {
//                        popUpTo(CourseLessonRoute.Listing.route) { inclusive = false }
//                    }
//                },
//                goToDashboard = onHome,
//                viewModel = editLessonViewModel,
//            )
//        }
//    }
//}