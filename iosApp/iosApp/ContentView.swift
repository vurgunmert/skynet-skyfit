import UIKit
import SwiftUI
import composeApp

struct ContentView: View {
    var body: some View {
        ZStack {
//            Color.black.ignoresSafeArea(.all)
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
        vc.view.backgroundColor = .clear // Ensure background is transparent

        // Force drawing behind status bar
//        vc.edgesForExtendedLayout = .all
//        vc.modalPresentationCapturesStatusBarAppearance = true
        //TODO: Investigate how to get status height
        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
