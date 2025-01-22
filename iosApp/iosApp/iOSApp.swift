import SwiftUI
import composeApp

@main
struct iOSApp: App {

    init() {
        AppDependencyManagerKt.loadSkyFitModules()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
