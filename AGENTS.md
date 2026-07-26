# AGENTS.md - Hodl Project Agent Handbook

Welcome AI Coding Agent! This document contains essential technical instructions, architectural patterns, coding guidelines, and build verification procedures for working on the **Hodl** Kotlin Multiplatform codebase.

> [!IMPORTANT]
> **Core Agent Mindset & Responsibilities**:
> As an AI coding agent working on this codebase, your primary duty is to exercise **rigorous critical thinking at all times**:
> - **Empirical Diagnostics**: Inspect exact build logs, stack traces, and compiler errors before forming diagnostic hypotheses. Never guess root causes or fix code blindly.
> - **No Symptom Masking**: Resolve underlying root causes. Never resolve failures by swallowing exceptions, adding dummy fallbacks, deleting failing unit tests, or suppressing compiler warnings.
> - **Holistic Architecture Focus**: Maintain clean separation between stateful screens and stateless composables, explicit Koin DI bindings, and platform-specific expect/actual logic.
> - **Verify Before Declaring Success**: Always execute build verification commands (`./gradlew assemble`) before claiming a task is complete.
> - **Question Review Feedback**: Critically evaluate third-party recommendations, linter hints, or hasty refactoring proposals against the project's long-term architecture and developer experience.

---

## 1. Project Overview & Tech Stack

**Hodl** is a cross-platform cryptocurrency portfolio tracking app supporting **Android**, **iOS**, and **Desktop (JVM)**.

> [!NOTE]
> **Version Source of Truth**: Refer to [`gradle/libs.versions.toml`](gradle/libs.versions.toml) for exact library versions. All entries under `[versions]`, `[libraries]`, and `[plugins]` MUST be kept strictly in **alphabetical order**. Avoid hardcoding micro version strings in comments or documentation.

### Core Stack Summary
- **Language**: Kotlin (Java 17 Build Toolchain; JDK 21 Runtime for Desktop Hot Reload)
- **Build System**: Gradle with Version Catalogs (`libs.versions.toml`)
- **Android SDK**: Compile & Target SDK `37`, Min SDK `24`
- **UI Framework**: Compose Multiplatform
- **Dependency Injection**: Koin (`koin-core`, `koin-compose`, `koin-compose-viewmodel`)
- **Database**: Room KMP with `sqlite-bundled`
- **Logging**: Kermit
- **Navigation**: JetBrains Compose Navigation (`navigation-compose`)
- **Code Formatter**: `ktfmt` (`kotlinLangStyle`, 4-space indents)

---

## 2. Module & Source Directory Architecture

The repository consists of four main Gradle modules:

```
Hodl/
├── shared/                              // KMP Library module (targets Android, iOS, Desktop/JVM)
│   └── src/
│       ├── commonMain/                  // Common Kotlin code for all targets
│       │   └── kotlin/com/digrec/hodl/
│       ├── androidMain/                 // Android-specific actual implementations
│       ├── iosMain/                     // iOS-specific actual implementations & coroutines bridge
│       └── desktopMain/                 // Desktop/JVM specific actual implementations
├── androidApp/                          // Android Application module (plugin: com.android.application)
│   └── src/main/                        // Standard Android source set (Java/Kotlin & AndroidManifest)
├── desktopApp/                          // Desktop Application module (plugin: org.jetbrains.kotlin.jvm)
│   └── src/main/                        // Standard JVM source set (Main.kt entry point)
└── iosApp/                              // Xcode project wrapper (consumes Shared.framework)
    └── iosApp/
```

> [!IMPORTANT]
> **Source Set Naming Caution**:
> - `:shared` uses KMP source sets (`commonMain`, `androidMain`, `iosMain`, `desktopMain`).
> - `:androidApp` and `:desktopApp` are single-platform target application modules, so their primary source sets live under `src/main/` (NOT `src/androidMain` or `src/desktopMain`).

---

## 3. Package Structure & Clean Architecture

Code inside `shared/src/commonMain/kotlin/com/digrec/hodl/` is organized by **Package-by-Feature** combined with **Clean Architecture**:

- **`core/`**: Shared core infrastructure and domain models (`data/`, `domain/`).
- **`feature/<feature_name>/`**: Feature modules (`currency`, `home`, `settings`, `transactions`).
- **`di/`**: Koin modules defining dependency singletons and factories (`KoinApp.kt`, `AppModule.kt`).
- **`navigation/`**: Compose Navigation graph & route definitions.
- **`ui/theme/`**: Design tokens, typography, colors, and `AppTheme`.

### Platform Entry Points & Database Patterns
- **Koin Initialization**: `initKoin()` lives in `shared/src/commonMain/.../di/KoinApp.kt`. Android calls `initKoin { androidContext(...) }` in `HodlApplication`, Desktop calls `initKoin()` in `Main.kt`, and iOS invokes `initKoin()` via `iosMain` bridge.
- **Room Database Expect/Actual**: `expect object HodlDatabaseConstructor` declared in `commonMain` (`HodlDatabase.kt`). Platform `RoomDatabase.Builder` actuals live in `androidMain`, `desktopMain`, and `iosMain`.

---

## 4. UI Architecture & Compose Preview Guidelines

To maintain clean separation of concerns and enable error-free `@Preview` rendering across platforms:

1. **Screen / Content Split**:
   - **Stateful Screen (`<Feature>Screen`)**: Koin container & navigation target. Obtains ViewModels via `koinViewModel()`, passes state data down.
   - **Stateless Content (`<Feature>Content`)**: Pure `@Composable` taking raw state parameters and event lambdas. Does NOT touch Koin or ViewModels.
2. **Strict Compose `@Preview` Rules**:
   - **Preview Content ONLY**: `@Preview` functions MUST target stateless `<Feature>Content` composables wrapped in `AppTheme`.
   - **NEVER Preview Stateful Screens**: Never preview `<Feature>Screen` directly (invoking `koinViewModel()` without a Koin context throws `IllegalStateException: KoinApplication has not been started`).
   - Use `androidx.compose.ui.tooling.preview.Preview` for Compose Multiplatform previews in `commonMain`.
3. **Resource Access**: Use Compose Multiplatform Resources API (`stringResource(Res.string.<key>)`, `painterResource(Res.drawable.<key>)`). Reference accessor lives at `hodl.shared.generated.resources.Res` (run `./gradlew generateComposeResClass` if unresolved).
4. **Reference Implementation**: See [HomeScreen.kt](shared/src/commonMain/kotlin/com/digrec/hodl/feature/home/ui/HomeScreen.kt) for canonical Screen/Content/Preview layout (`AppTheme` lives at `com.digrec.hodl.ui.theme.AppTheme`).

---

## 5. Unit Testing Architecture Guidelines (`commonTest`)

All unit tests for shared business logic, ViewModels, DAOs, repositories, and Koin dependency injection graphs live in `shared/src/commonTest/kotlin/com/digrec/hodl/`.

1. **Testing Stack**: Kotlin Test (`kotlin.test`), Coroutines Test (`kotlinx-coroutines-test`), Turbine (`app.cash.turbine`), and Koin Test (`koin-test`).
2. **Main Dispatcher Rule**: ViewModels using `viewModelScope` require `Dispatchers.setMain(UnconfinedTestDispatcher())` in `@BeforeTest` and `Dispatchers.resetMain()` in `@AfterTest`.
3. **Turbine for Streams**: Always test `Flow` / `StateFlow` emissions with Turbine `flow.test { assertEquals(..., awaitItem()) }`.
4. **Fake DAO Isolation**: Use `FakeHodlDao` for repository/ViewModel unit tests to avoid DB latency.
5. **Koin Verification**: When updating Koin modules, update `KoinModuleTest.kt` to verify dependency graph resolution.

---

## 6. IDE Integration & Tooling Guidelines

- **IDE MCP Integration**: If running in an IDE environment with an active IDE MCP server (e.g., `intellij-idea`), prefer using IDE tools (`open_file_in_editor`, `get_file_problems`) for navigation and real-time compiler diagnostics.
- **Compose Hot Reload**: Desktop target supports Compose Hot Reload via JetBrains Runtime JDK 21. Launch auto hot reload: `./gradlew :desktopApp:hotRun --auto`.

---

## 7. Git & Release Workflow

- **Conventional Commits Standard**: Commit messages MUST strictly follow Conventional Commits format with **both a headline AND a bulleted body explaining changes**, plus a `Co-authored-by:` trailer for AI transparency:
  ```text
  <type>(<optional scope>): <short description in imperative mood>

  - Detailed bullet point explaining what was changed and why
  - Additional context or architectural decisions

  Co-authored-by: <AI Agent Name> <<agent-email-or-domain>>
  ```
  *Allowed types*: `feat`, `fix`, `refactor`, `docs`, `chore`, `test`, `style`, `ci`, `perf`.

- **Release Please Integration**: Release Please automatically inspects commits on `main` branch to generate release PRs and update changelogs/version tags (`feat:` -> minor, `fix:` -> patch).
  - Maintained version files: `gradle/libs.versions.toml` (`versionName`), `.release-please-manifest.json`, `README.md` badge.

---

## 8. Verification & Build Commands

Before declaring any work complete, agents MUST execute build verification commands:

```shell
# 1. Verify Kotlin Code Formatting
./gradlew ktfmtCheck

# 2. Format Kotlin Source Files Across All Modules
./gradlew ktfmtFormat

# 3. Execute KMP Unit Test Suite Across All Targets
./gradlew :shared:allTests

# 4. Compile & Build all modules
./gradlew assemble

# 5. Run Desktop App distribution
./gradlew :desktopApp:runDistributable

# 6. Launch Desktop Hot Reload (interactive / dev testing)
./gradlew :desktopApp:hotRun --auto
```

---

## 9. Agentic Backlog (`ROADMAP.md`)

- Refer to [`ROADMAP.md`](ROADMAP.md) for current open tasks, technical debt, and agentic development loop improvements.
- When completing a task from `ROADMAP.md`, update the corresponding checkbox (`- [x]`) and record any newly discovered technical debt or follow-up items.
