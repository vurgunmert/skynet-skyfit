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
        FirebaseConfiguration.shared.setLoggerLevel(.debug)
        
        print("[AppDelegate] Firebase configured")

        Analytics.logEvent(AnalyticsEventAppOpen, parameters: nil)

        application.registerForRemoteNotifications()
        print("[AppDelegate] Registered for remote notifications")

        UNUserNotificationCenter.current().requestAuthorization(options: [.alert, .sound, .badge]) { granted, error in
            print("[AppDelegate] Notification permission granted: \(granted), error: \(String(describing: error))")
            Analytics.logEvent("notification_permission_result", parameters: [
                "granted": granted.description
            ])
        }

        UNUserNotificationCenter.current().delegate = self
        print("[AppDelegate] Notification center delegate set")

        Messaging.messaging().token { token, error in
            if let error = error {
                print("Error fetching FCM token: \(error)")
            } else if let token = token {
                print("FCM token manually fetched: \(token)")
                Analytics.logEvent("fcm_token_manual_fetch", parameters: [
                    "token": token
                ])
            }
        }

        Messaging.messaging().delegate = self
        print("[AppDelegate] Messaging delegate set")

        IOSNotifierKt.InitializeNotifier()
        print("[AppDelegate] Kotlin Notifier initialized")

        UNUserNotificationCenter.current().delegate = NotificationDelegate.shared
        print("[AppDelegate] NotificationDelegate.shared set")

        return true
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        print("[AppDelegate] applicationWillEnterForeground")
        Analytics.logEvent("app_foreground", parameters: nil)
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        print("[AppDelegate] applicationDidEnterBackground")
        Analytics.logEvent("app_background", parameters: nil)
    }

    func applicationWillTerminate(_ application: UIApplication) {
        print("[AppDelegate] applicationWillTerminate")
        Analytics.logEvent("app_terminate", parameters: nil)
    }

    func application(
        _ application: UIApplication,
        didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data
    ) {
        let tokenString = deviceToken.map { String(format: "%02.2hhx", $0) }.joined()
        Messaging.messaging().apnsToken = deviceToken
        print("[AppDelegate] APNs device token registered: \(tokenString)")

        Analytics.logEvent("apns_token_registered", parameters: [
            "token_length": tokenString.count
        ])
    }

    func messaging(_ messaging: Messaging, didReceiveRegistrationToken fcmToken: String?) {
        print("[AppDelegate] FCM token received: \(String(describing: fcmToken))")
        Analytics.logEvent("fcm_token_received", parameters: [
            "token_exists": (fcmToken != nil).description
        ])
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        willPresent notification: UNNotification,
        withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void
    ) {
        let userInfo = notification.request.content.userInfo
        print("[AppDelegate] Will present notification: \(userInfo)")
        Analytics.logEvent("notification_will_present", parameters: nil)

        completionHandler([.banner, .list, .sound, .badge])
    }

    func userNotificationCenter(
        _ center: UNUserNotificationCenter,
        didReceive response: UNNotificationResponse,
        withCompletionHandler completionHandler: @escaping () -> Void
    ) {
        let userInfo = response.notification.request.content.userInfo
        print("[AppDelegate] Did receive notification response: \(userInfo)")
        Analytics.logEvent("notification_opened", parameters: nil)

        NotificationCenter.default.post(name: Notification.Name("didReceiveRemoteNotification"),
                                        object: nil,
                                        userInfo: userInfo)
        completionHandler()
    }

    func application(
        _ application: UIApplication,
        didReceiveRemoteNotification userInfo: [AnyHashable: Any]
    ) async -> UIBackgroundFetchResult {
        print("[AppDelegate] NotifierManager handled remote notification: \(userInfo)")
        Analytics.logEvent("remote_notification_received", parameters: nil)
        NotifierManager.shared.onApplicationDidReceiveRemoteNotification(userInfo: userInfo)
        return .newData
    }
}
