package com.vurgun.skyfit.core.utils

//import androidx.compose.runtime.Composable
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import kotlinx.cinterop.ExperimentalForeignApi
//import kotlinx.cinterop.useContents
//import platform.UIKit.UIApplication
//import platform.UIKit.UIWindow
//import platform.UIKit.UIWindowScene
//
//@OptIn(ExperimentalForeignApi::class)
//@Composable
//actual fun getStatusBarHeight(): Dp {
//    val keyWindow = UIApplication.sharedApplication
//        .connectedScenes
//        .filterIsInstance<UIWindowScene>()
//        .firstOrNull()?.windows
//        ?.firstOrNull { (it as? UIWindow)?.isKeyWindow() == true } as? UIWindow
//
//    val statusBarHeight = keyWindow?.safeAreaInsets?.useContents { top } ?: 44.0 // Extracts `.top` correctly
//
//    return statusBarHeight.dp
//}
