import UIKit
import SwiftUI
import composeApp

struct ContentView: View {
    var body: some View {
        ZStack {
            
            Color(hex: 0x00171C).ignoresSafeArea(.all)
            ComposeView()
                .ignoresSafeArea(.keyboard)
        }
        .preferredColorScheme(.dark)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let vc = MainViewControllerKt.MainViewController()

        vc.modalPresentationStyle = .fullScreen
        vc.view.backgroundColor = UIColor(red: 0/255, green: 23/255, blue: 28/255, alpha: 1) // #00171C

        vc.edgesForExtendedLayout = .all
        vc.modalPresentationCapturesStatusBarAppearance = true

        if let windowScene = UIApplication.shared
            .connectedScenes
            .first(where: { $0.activationState == .foregroundActive }) as? UIWindowScene,
           let window = windowScene.windows.first {
            window.backgroundColor = vc.view.backgroundColor
        }

        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255,
            green: Double((hex >> 8) & 0xFF) / 255,
            blue: Double(hex & 0xFF) / 255,
            opacity: alpha
        )
    }
}
