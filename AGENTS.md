# AGENTS.md - Hodl Project Agent Handbook

Welcome AI Coding Agent! This handbook defines technical instructions, architecture patterns, and verification procedures for the **Hodl** Kotlin Multiplatform codebase.

> [!IMPORTANT]
> **Core Agent Responsibilities**:
> - **Empirical Diagnostics**: Inspect exact build logs, stack traces, and compiler errors before forming hypotheses. Never fix code blindly or mask symptoms.
> - **Holistic Architecture**: Maintain strict separation between stateful screens and stateless composables, explicit Koin DI bindings, and platform expect/actual logic.
> - **Verify Before Declaring Success**: Always execute verification commands (`./scripts/ktfmt.sh --dry-run`, `./kotlin build`, `./kotlin test`) before completing tasks.

---

## 1. Project Overview & Tech Stack

**Hodl** is a cross-platform cryptocurrency portfolio tracking app supporting **Android**, **iOS**, and **Desktop (JVM)**.

- **Build System**: Standalone JetBrains Kotlin Toolchain CLI (`kotlin` / `project.yaml` / `module.yaml`, Amper-powered).
- **Language & Runtime**: Kotlin (JDK 21 toolchain for compilation and Desktop Hot Reload).
- **Target SDKs**: Android compile/target `37`, min `24`; iOS `iosSimulatorArm64` / `iosArm64`.
- **UI & Navigation**: Compose Multiplatform with `navigation-compose`.
- **DI & Database**: Koin (`koin-compose-viewmodel`) and Room KMP (`sqlite-bundled`).
- **Dependencies**: Centralized in root [`libs.versions.toml`](libs.versions.toml) catalog and referenced via `$libs.<name>`.
- **Code Formatter**: `ktfmt` (`kotlinLangStyle`, 4-space indents; `brew install ktfmt`, run via `./scripts/ktfmt.sh`).

---

## 2. Module & Source Directory Architecture

The repository consists of four modules managed by `project.yaml`:

```
Hodl/
├── project.yaml / libs.versions.toml / scripts/
├── shared/                              // KMP Library (Android, iOS, JVM)
│   ├── composeResources/ & schemas/     // Multiplatform resources & Room schemas
│   ├── src/ & src@{android,jvm,ios}/   // Common & platform implementations
│   └── test/ & test@jvm/               // Common unit tests & JVM DB migration tests
├── androidApp/                          // Android application (android/app)
├── desktopApp/                          // Desktop application (jvm/app)
└── iosApp/                              // iOS application (ios/app, Swift + SwiftUI)
```

> [!IMPORTANT]
> **Source Set Naming Convention**: Kotlin Toolchain uses `src/` for common sources and platform qualifiers with `@` syntax (`src@android/`, `src@jvm/`, `src@ios/`). Unit tests follow the same pattern (`test/` and `test@jvm/`).

---

## 3. Package Structure & Clean Architecture

Code inside `shared/src/com/digrec/hodl/` follows **Package-by-Feature** and **Clean Architecture**:
- `core/`: Shared data, repository, domain models, and Room DB (`HodlDatabase.kt`).
- `feature/<name>/`: Isolated feature modules (`currency`, `home`, `settings`, `transactions`).
- `di/`: Koin dependency injection modules (`KoinApp.kt`, `AppModule.kt`).
- `navigation/` & `ui/theme/`: Route definitions, design tokens, and theme.

**Platform Entry Points & Database**:
- **Koin Initialization**: `initKoin()` lives in `com.digrec.hodl.di.KoinApp`. Android calls `initKoin { androidContext(...) }` in `HodlApplication`, Desktop in `Main.kt`, and iOS via `iosApp/src/iOSApp.swift` (`src@ios`).
- **Room Database Expect/Actual**: `expect object HodlDatabaseConstructor` in `shared/src/`; platform `RoomDatabase.Builder` actuals live in `shared/src@android/`, `src@jvm/`, and `src@ios/`.

---

## 4. UI Architecture & Compose Preview Guidelines

1. **Screen / Content Split**:
   - **Stateful Screen (`<Feature>Screen`)**: Koin container & navigation target. Injects ViewModels (`koinViewModel()`), passes state and events down.
   - **Stateless Content (`<Feature>Content`)**: Pure `@Composable` taking raw state parameters and event lambdas. Never touches Koin or ViewModels.
2. **Strict Compose `@Preview` Rules**:
   - **Preview Content ONLY**: `@Preview` functions MUST target stateless `<Feature>Content` wrapped in `AppTheme`.
   - **NEVER Preview Stateful Screens**: Invoking `koinViewModel()` outside a Koin context crashes previews.
   - Use `androidx.compose.ui.tooling.preview.Preview` in common code (`shared/src/`).
3. **Resource Access**: Use Compose Multiplatform Resources API (`Res.string.<key>`, `Res.drawable.<key>`).
4. **Reference Implementation**: See [HomeScreen.kt](shared/src/com/digrec/hodl/feature/home/ui/HomeScreen.kt) for canonical pattern.

---

## 5. Unit Testing Architecture Guidelines (`shared/test`)

Unit tests live in `shared/test/` (common) and `shared/test@jvm/` (JVM migration tests).
1. **Testing Stack**: `kotlin.test`, `kotlinx-coroutines-test`, Turbine (`app.cash.turbine`), `koin-test`, and `androidx.room.testing`.
2. **Main Dispatcher**: ViewModels require `Dispatchers.setMain(UnconfinedTestDispatcher())` in `@BeforeTest` and `Dispatchers.resetMain()` in `@AfterTest`.
3. **Turbine for Streams**: Always test `Flow` / `StateFlow` emissions with Turbine `flow.test { assertEquals(..., awaitItem()) }`.
4. **Fake DAO Isolation**: Use `FakeHodlDao` for repository/ViewModel unit tests to avoid DB latency.
5. **Koin Verification**: Verify DI resolution via `KoinModuleTest.kt`.
6. **Database Migration Testing**: Migration tests live in `test@jvm/.../HodlDatabaseMigrationTest.kt` using `MigrationTestHelper` and `BundledSQLiteDriver()`.

---

## 6. IDE Integration & Tooling Guidelines

- **Kotlin Toolchain CLI Priority**: Always use `./kotlin` (`build`, `test`, `run`, `clean`). Never invoke Gradle or raw compiler binaries directly.
- **Android CLI Tooling**: When managing virtual devices (AVDs), emulators, or SDK components, ALWAYS use the installed `android` CLI (`android virtual-device ...`, `android sdk ...`) instead of raw SDK binaries (`emulator`, `adb`, `sdkmanager`).
- **IDE MCP Integration**: When active, prefer IDE tools (`open_file_in_editor`, `get_file_problems`) for navigation and compiler diagnostics.
- **Compose Hot Reload**: Run Desktop directly with `./kotlin run -m desktopApp`. AI agents can inspect live UI state using `./kotlin compose-hot-reload-mcp-server`.

---

## 7. Git & Release Workflow

- **Conventional Commits**: Messages MUST follow format with headline, bulleted body, and AI trailer:
  ```text
  <type>(<scope>): <summary in imperative mood>
  - Detailed explanation of changes
  Co-authored-by: <AI Agent Name> <<agent-email-or-domain>>
  ```
- **Version Synchronization (`./scripts/sync-versions.sh <version>`)**: Calculates `versionCode = major*10000 + minor*100 + patch` and synchronizes `libs.versions.toml`, `androidApp/module.yaml`, and iOS configurations.
  - **Mandatory Agent Triggers**: AI agents MUST run this script: (1) After `git pull` if semver changed or release PR was merged; (2) Before release builds and packaging; (3) After `./kotlin clean`; (4) Whenever bumping the application version.

---

## 8. Verification & Build Commands

Before declaring tasks complete, agents MUST run the verification pipeline:

```shell
# Mandatory Verification Pipeline:
./scripts/sync-versions.sh 1.0.0    # Run if semver changed, after release pull, or clean setup
./scripts/ktfmt.sh --dry-run        # Check formatting (run ./scripts/ktfmt.sh to format)
./kotlin build                      # Build all modules across all targets
./kotlin test                       # Run shared unit & migration tests

# Application Launch & Dev Commands:
./kotlin run -m desktopApp          # Desktop app (with Compose Hot Reload)
./kotlin run -m androidApp          # Android app on connected device/emulator
./kotlin run -m iosApp -p iosSimulatorArm64 # iOS app on simulator
./kotlin compose-hot-reload-mcp-server # Compose UI inspection MCP server
./kotlin clean                      # Clean build output and caches
```

---

## 9. Agentic Backlog (`ROADMAP.md`)

- Refer to [`ROADMAP.md`](ROADMAP.md) for open tasks, technical debt, and agentic loop enhancements.
- Update the corresponding checkbox (`- [x]`) upon task completion.
