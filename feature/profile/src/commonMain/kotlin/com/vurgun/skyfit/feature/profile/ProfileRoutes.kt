package com.vurgun.skyfit.feature.profile

//@Composable
//fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
//
//    SkyFitMobileScaffold {
//
//        Box(Modifier.fillMaxSize()) {
//            if (isLoading) {
//                CircularLoader(Modifier.size(240.dp).align(Alignment.Center))
//            } else {
//                when (user?.userRole) {
//                    UserRole.User -> MobileUserProfileScreen(
//                        goToBack = { },
//                        goToSettings = { },
//                        goToAppointments = { },
//                        goToMeasurements = { },
//                        goToExercises = { },
//                        goToPhotoDiary = { },
//                        goToCreatePost = { }
//                    )
//
//                    UserRole.Trainer -> MobileTrainerProfileScreen(
//                        goToSettings = { },
//                        goToCreatePost = { }
//                    )
//
//                    UserRole.Facility -> MobileFacilityProfileScreen(
//                        goToManageLessons = { },
//                        goToSettings = { },
//                        goToCreatePost = { },
//                        goToVisitCalendar = { },
//                        goToVisitTrainerProfile = { },
//                        goToPhotoGallery = { },
//                        goToChat = { },
//                        viewMode = ProfileViewMode.OWNER,
//                        goToBack = { }
//                    )
//
//                    else -> Unit
//                }
//            }
//        }
//    }
//}