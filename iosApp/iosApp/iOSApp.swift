import SwiftUI
import Shared

/**
 * Created by Dejan Igrec
 */
@main
struct iOSApp: SwiftUI.App {

    init() {
        KoinAppKt.doInitKoin {
            $0.printLogger(level: Koin_coreLevel.debug)
        }
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
