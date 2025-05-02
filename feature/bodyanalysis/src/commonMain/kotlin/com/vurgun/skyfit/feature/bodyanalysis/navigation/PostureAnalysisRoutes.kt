//package com.vurgun.skyfit.feature.bodyanalysis.navigation
//
//import androidx.navigation.NavController
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.compose.composable
//import com.vurgun.skyfit.feature.bodyanalysis.screen.PostureAnalysisRootScreen
//import kotlinx.serialization.Serializable
//
//sealed interface PostureAnalysisRoute {
//
//    @Serializable
//    data object Root : PostureAnalysisRoute
//
//    @Serializable
//    data object Info : PostureAnalysisRoute
//
//    @Serializable
//    data object Options : PostureAnalysisRoute
//
//    @Serializable
//    data class Preview(val type: Int, val gridEnabled: Boolean, val guideEnabled: Boolean) : PostureAnalysisRoute
//
//    @Serializable
//    data object Scanning : PostureAnalysisRoute
//
//    @Serializable
//    data object Report : PostureAnalysisRoute
//}
//
//fun NavGraphBuilder.postureAnalysisRoutes(
//    navController: NavController
//) {
//    composable<PostureAnalysisRoute.Root> {
//        PostureAnalysisRootScreen(onExit = {
//            navController.navigateUp()
//        })
//    }
//}