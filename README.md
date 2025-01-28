# Hodl

Kotlin Multiplatform project targeting Android, iOS and Desktop.

## Project Structure

This KMP project utilizes a modular architecture, combining traditional KMP module organization with
Clean Architecture principles and a package-by-feature structure for improved maintainability and scalability.


```
Hodl/
├── shared/                              // Code that's shared across KMP applications
│   └── src/
│       ├── commonMain/                  // Code that’s common for all targets
│       │   └── kotlin/com/digrec/hodl/
│       │       ├── HodlApp.kt           // Entry point for shared UI
│       │       ├── core/                // Core business logic, use cases, interfaces
│       │       │   ├── domain/
│       │       │   └── data/
│       │       ├── feature/             // Feature modules
│       │       │   ├── authentication/
│       │       │   │   ├── data/
│       │       │   │   ├── domain/
│       │       │   │   └── ui/          // UI Components, screens and ViewModels
│       │       │   ├── settings/
│       │       │   │    └── ...         // Same structure as authentication
│       │       │   └── ...
│       │       ├── di/                  // Dependency injection
│       │       ├── ui/                  // Shared UI components and theme
│       │       │   ├── component/
│       │       │   ├── theme/
│       │       │   └── util/
│       │       └── util/                // Shared utility functions
│       ├── androidMain/                 // Kotlin code compiled for Android platform
│       │   └── kotlin/com/digrec/hodl/
│       ├── iosMain/                     // Kotlin code compiled for iOS platform
│       │   └── kotlin/com/digrec/hodl/
│       └── desktopMain/                 // Kotlin code compiled for desktop platforms
│           └── kotlin/com/digrec/hodl/
├── androidApp/                          // Android app that depends on shared module
│   └── src/androidMain/
├── iosApp/                              // iOS app that depends on shared module
│   └── iosApp/
└── desktopApp/                          // Desktop app that depends on shared module
    └── src/desktopMain/
```

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
