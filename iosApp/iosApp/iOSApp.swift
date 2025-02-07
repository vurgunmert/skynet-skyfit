import SwiftUI
import composeApp

@main
struct iOSApp: App {

    init() {
        AppDependencyManagerKt.loadKoinDependencyModules()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
