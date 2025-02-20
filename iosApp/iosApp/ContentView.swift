import Shared
import SwiftUI
import UIKit

/// Created by Dejan Igrec
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(
        _ uiViewController: UIViewController, context: Context
    ) {
    }
}

/// Created by Dejan Igrec
struct ContentView: View {
    var body: some View {
        ComposeView()
            .preferredColorScheme(.dark)  // Force dark mode
            .ignoresSafeArea()  // Extend background behind safe areas
            .ignoresSafeArea(.keyboard)  // Compose has own keyboard handler
    }
}
