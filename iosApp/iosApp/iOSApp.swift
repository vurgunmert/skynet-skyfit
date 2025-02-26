import SwiftUI
import composeApp

@main
struct iOSApp: App {

    init() {
        DependencyInjectionInitializerKt.initKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
