import SwiftUI
import composeApp

@main
struct iOSApp: App {

    init() {
        DependencyInjectionInitializerKt.doInitKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
