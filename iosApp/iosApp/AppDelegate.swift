//
//  AppDelegate.swift
//  iosApp
//
//  Created by Mert Vurgun on 28.06.2025.
//  Copyright © 2025 Vurgun Teknoloji. All rights reserved.
//

import UIKit
import Firebase
import composeApp

class AppDelegate: NSObject, UIApplicationDelegate,
                   UNUserNotificationCenterDelegate, MessagingDelegate {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {
        print("[AppDelegate] didFinishLaunchingWithOptions")
        
        FirebaseApp.configure()
        print("[AppDelegate] Firebase configured")

        application.registerForRemoteNotifications()
        print("[AppDelegate] Registered for remote notifications")

        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
            print("[AppDelegate] Notification permission granted: \(granted), error: \(String(describing: error))")
        }

        UNUserNotificationCenter.current().delegate = self
        print("[AppDelegate] Notification center delegate set")

        Messaging.messaging().delegate = self
        print("[AppDelegate] Messaging delegate set")

        IOSNotifierKt.InitializeNotifier()
        print("[AppDelegate] Kotlin Notifier initialized")

        UNUserNotificationCenter.current().delegate = NotificationDelegate.shared
        print("[AppDelegate] NotificationDelegate.shared set")

        return true
    }

    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        Messaging.messaging().apnsToken = deviceToken
        print("[AppDelegate] APNs device token registered: \(deviceToken.map { String(format: "%02.2hhx", $0) }.joined())")
    }

    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("[AppDelegate] FCM token received: \(String(describing: fcmToken))")
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        print("[AppDelegate] Will present notification: \(notification.request.content.userInfo)")
        completionHandler([.banner, .list, .sound, .badge])
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse,
        withCompletionHandler completionHandler: @escaping () -> Void
    ) {
        print("[AppDelegate] Did receive notification response: \(response.notification.request.content.userInfo)")
        NotificationCenter.default.post(name: Notification.Name("didReceiveRemoteNotification"),
                                        object: nil,
                                        userInfo: response.notification.request.content.userInfo)
        completionHandler()
    }
    
    // Called when a remote notification is received (iOS 13+ async API)
    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable: Any]
    ) async -> UIBackgroundFetchResult {
        await withCheckedContinuation { continuation in
            Task.detached {
                NotifierManager.shared.onApplicationDidReceiveRemoteNotification(userInfo: userInfo)
                continuation.resume(returning: .newData)
                print("[AppDelegate] NotifierManager handled remote notification")
            }
        }
    }
}
