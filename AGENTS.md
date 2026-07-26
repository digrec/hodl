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

- **`core/`**: Shared core infrastructure and domain models.
  - `data/`: DB instances, Room DAOs (`HodlDao`), repositories (`HodlRepositoryImpl`), network/util helpers.
  - `domain/`: Business models, Repository interfaces (`HodlRepository`), use cases.
- **`feature/<feature_name>/`**: Feature modules (`home`, `currency`, `settings`, `transactions`).
  - `data/`: Feature-specific data providers or local sources.
  - `domain/`: Feature-specific domain models or logic.
  - `ui/`: Feature ViewModels and Compose screens.
- **`di/`**: Koin modules defining dependency singletons and factories (`KoinApp.kt`, `AppModule.kt`).
- **`navigation/`**: Compose Navigation graph & route definitions.
- **`ui/theme/`**: Design tokens, typography, colors, and `AppTheme`.

### A. Platform Entry Points & Koin Initialization
Koin DI initialization is defined in `shared/src/commonMain/kotlin/com/digrec/hodl/di/KoinApp.kt` via `initKoin()`. Each target platform initializes Koin in its entry point:
- **Android**: `HodlApplication` (`androidApp/src/main/`) calls `initKoin { androidContext(...) }`.
- **Desktop**: `Main.kt` (`desktopApp/src/main/`) calls `initKoin()`.
- **iOS**: Exposed via `initKoin()` bridge in `iosMain` and invoked during iOS app launch.

### B. Room KMP Database Expect/Actual Pattern
- Common database definition `HodlDatabase` lives in `shared/src/commonMain/.../core/data/db/HodlDatabase.kt`.
- `expect object HodlDatabaseConstructor : RoomDatabaseConstructor<HodlDatabase>` is declared in `commonMain`.
- Platform-specific `RoomDatabase.Builder` actual implementations live in:
  - `shared/src/androidMain/.../HodlDatabase.android.kt` (uses `Context.getDatabasePath()`)
  - `shared/src/desktopMain/.../HodlDatabase.desktop.kt` (uses user data directory)
  - `shared/src/iosMain/.../HodlDatabase.ios.kt` (uses `NSDocumentDirectory`)

---

## 4. UI Architecture & Compose Preview Guidelines

To maintain clean separation of concerns and enable error-free `@Preview` rendering across platforms:

### A. Screen / Content Component Split
Every UI screen MUST be split into two distinct composables:
1. **Stateful Screen (`<Feature>Screen`)**:
   - Acts as the Koin container and navigation target.
   - Retrieves ViewModels via `koinViewModel()`.
   - Collects state flows and passes raw state data & action lambdas down to `<Feature>Content`.
2. **Stateless Content (`<Feature>Content`)**:
   - Pure `@Composable` rendering component.
   - Accepts raw state parameters (e.g. `greetingState: String`, `currencies: List<Currency>`) and event lambdas (e.g. `onCurrencyClick: (String) -> Unit`).
   - Does NOT touch Koin or ViewModels directly.

### B. Strict Compose `@Preview` Rules
- **Preview Content ONLY**: `@Preview` functions MUST target the stateless `<Feature>Content` composables wrapped inside `AppTheme`.
- **NEVER Preview Stateful Screens**: Never place `@Preview` on `<Feature>Screen` directly. Doing so invokes `koinViewModel()` without an active Koin context during preview rendering, causing `IllegalStateException: KoinApplication has not been started`.
- **Preview Imports**: Use `androidx.compose.ui.tooling.preview.Preview` for Compose Multiplatform previews in `commonMain`.

### C. Compose Multiplatform Resource Access
All UI strings and drawables MUST use the Compose Multiplatform Resources API (`org.jetbrains.compose.resources`):
- Access strings via `stringResource(Res.string.<key>)`.
- Access drawables via `painterResource(Res.drawable.<key>)`.
- Generated resource accessors live under `hodl.shared.generated.resources.Res`.

#### Example Pattern (`HomeScreen.kt`):
```kotlin
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: HomeViewModel = koinViewModel()
    val greetingState by viewModel.greetingState

    HomeContent(
        greetingState = greetingState,
        modifier = modifier
    )
}

@Composable
fun HomeContent(
    greetingState: String,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Text(text = stringResource(Res.string.home))
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeContent(greetingState = "Hello Preview!")
    }
}
```

---

## 5. Unit Testing Architecture & Guidelines (`commonTest`)

All unit tests for shared business logic, ViewModels, DAOs, repositories, and Koin dependency injection graphs MUST live in `shared/src/commonTest/kotlin/com/digrec/hodl/`.

### A. Testing Stack & Libraries
- **Kotlin Test (`kotlin.test`)**: Standard assertion API (`assertEquals`, `assertTrue`, `assertNotNull`).
- **Coroutines Test (`kotlinx-coroutines-test`)**: Controlled coroutine execution via `runTest` and `UnconfinedTestDispatcher`.
- **Turbine (`app.cash.turbine:turbine`)**: Non-flaky testing of `Flow` and `StateFlow` streams (`flow.test { assertEquals(..., awaitItem()) }`).
- **Koin Test (`koin-test`)**: Verification of dependency injection graphs (`KoinTest`, `startKoin`, `checkModules`).

### B. Mandatory Unit Testing Rules for Agents & Developers
1. **Coroutine Main Dispatcher Setup**:
   - ViewModels utilizing `viewModelScope` require `Dispatchers.Main`. In test classes targeting ViewModels, always set the main dispatcher in `@BeforeTest` and reset it in `@AfterTest`:
     ```kotlin
     private val testDispatcher = UnconfinedTestDispatcher()

     @BeforeTest
     fun setUp() {
         Dispatchers.setMain(testDispatcher)
     }

     @AfterTest
     fun tearDown() {
         Dispatchers.resetMain()
     }
     ```
2. **Turbine for Flow & StateFlow Emissions**:
   - Always test `Flow` emissions (such as `HodlRepository.getCurrencies()` and `CurrenciesViewModel.currencies`) with Turbine inside `runTest`:
     ```kotlin
     viewModel.currencies.test {
         assertEquals(listOf(btc), awaitItem())
         cancelAndIgnoreRemainingEvents()
     }
     ```
3. **Fake DAO Isolation Pattern**:
   - Use `FakeHodlDao` (`shared/src/commonTest/.../core/data/db/dao/FakeHodlDao.kt`) for pure unit tests of repositories and ViewModels to avoid database setup latency.
4. **Koin Dependency Resolution Verification**:
   - When introducing or updating Koin modules (`appModule`, `coreModule`, feature modules), update `KoinModuleTest.kt` to ensure the Koin graph resolves cleanly without missing bindings.

---

## 6. IDE Integration & Tooling Guidelines

- **IDE MCP Integration**:
  - If running in an IDE environment with an active IDE MCP server (e.g., `intellij-idea`), prefer using available IDE tools (such as `open_file_in_editor` or `get_file_problems`) for navigation, active file inspection, and real-time compiler diagnostics.
- **Compose Hot Reload**:
  - Desktop target supports Compose Hot Reload via JetBrains Runtime JDK 21.
  - Launch auto hot reload: `./gradlew :desktopApp:hotRun --auto`.

---

## 7. Git & Release Workflow

- **Conventional Commits Standard**: Commit messages MUST strictly follow Conventional Commits format with **both a headline AND a bulleted body explaining changes**:
  ```text
  <type>(<optional scope>): <short description in imperative mood>

  - Detailed bullet point explaining what was changed and why
  - Additional context or architectural decisions
  ```
  *Allowed types*: `feat`, `fix`, `refactor`, `docs`, `chore`, `test`, `style`, `ci`, `perf`.

- **Release Please Integration**:
  - Release Please automatically inspects commits on `main` branch to generate release PRs and update changelogs/version tags based on Conventional Commit types (`feat:` -> minor, `fix:` -> patch).
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
