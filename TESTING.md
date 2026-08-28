# LedgerLite — Testing Guide

LedgerLite is a POC whose real purpose is to be a practice ground for the Android testing
stack. **Every test in the repo today is a stub**: the class, rules, and dependencies are wired
up and compile/run, but each `@Test` body is `TODO()` and throws `NotImplementedError` until you
fill it in. That is intentional — you write the assertions.

This document explains **where each kind of test lives**, **how to run it**, and **what is left
to do**.

---

## 1. Module & source-set map

```
LedgerLite
├── app                         Application, MainActivity, NavHost, DI entry point
│   ├── src/test                (none — app has no local unit tests)
│   └── src/androidTest
│       ├── …/uiautomator/      UI Automator: biometric prompt + notification-tap flows
│       └── …/e2e/              End-to-end: login → add expense → dashboard
├── build-logic/convention      Gradle convention plugins (ledgerlite.android.*)
│
├── core:model        (JVM)     Domain models. Unit tests: JUnit 5 · src/test
├── core:common       (JVM)     Result/dispatcher primitives
├── core:domain       (JVM)     Use cases + repository interfaces. Unit tests: JUnit 5 · src/test
├── core:designsystem (AGP)     Compose theme + components + `LedgerTestTags`
├── core:database     (AGP)     Room. Instrumented DAO tests · src/androidTest
├── core:network      (AGP)     Retrofit/OkHttp. `@ApiBaseUrl` from BuildConfig; tests swap it (§7)
├── core:data         (AGP)     Repository impls, paging, FakeDataFactory.
│                               Integration tests (Room + MockWebServer) · src/androidTest
├── core:notifications(AGP)     Local notification + deep link
├── core:testing      (AGP)     Shared test infra (see §7). Consumed by every module.
│
├── feature:auth              Login/Register + biometrics.  test/  androidTest/
├── feature:dashboard        Balance + spending chart.     test/  androidTest/  screenshotTest/
├── feature:transactions     Paged list + detail.          test/  androidTest/
├── feature:addexpense       Form + validation.            test/  androidTest/  screenshotTest/
├── feature:search           Search / filter.              test/  androidTest/
├── feature:settings         Settings + seed 10k data.     test/  androidTest/
│
├── macrobenchmark   (com.android.test)  Cold start + list-scroll Macrobenchmarks
└── baselineprofile  (com.android.test)  Baseline Profile generator (wired into :app)
```

`src/test` = local JVM unit tests (fast, no device). `src/androidTest` = instrumented tests
(device/emulator). `src/screenshotTest` = Compose Preview screenshot tests (JVM render).

### JUnit 4 vs JUnit 5

| Where | Framework | Why |
|---|---|---|
| `:core:model`, `:core:domain` (`src/test`) | **JUnit 5 (Jupiter)** | Pure-JVM modules — `useJUnitPlatform()` is trivial, no plugin needed. |
| Every Android module (`src/test`, `src/androidTest`) | **JUnit 4** | AGP's unit/instrumented runners are JUnit 4 native; adding JUnit 5 there needs an extra plugin that isn't worth the risk on this toolchain. |

So the repo exercises both, split along the JVM / Android line.

---

## 2. How to run each test type

| Test type | Gradle command | Needs a device? |
|---|---|---|
| JVM unit — domain/model | `./gradlew :core:model:test :core:domain:test` | no |
| JVM unit — ViewModels (all features) | `./gradlew testDebugUnitTest` | no |
| JVM unit — one module | `./gradlew :feature:auth:testDebugUnitTest` | no |
| Instrumented — DAO | `./gradlew :core:database:connectedDebugAndroidTest` | **yes** |
| Instrumented — repository integration | `./gradlew :core:data:connectedDebugAndroidTest` | **yes** |
| Compose UI — one screen module | `./gradlew :feature:dashboard:connectedDebugAndroidTest` | **yes** |
| Compose UI — all | `./gradlew connectedDebugAndroidTest` | **yes** |
| UI Automator (biometric, notification) | `./gradlew :app:connectedDebugAndroidTest` | **yes** |
| End-to-end | `./gradlew :app:connectedDebugAndroidTest --tests "*.e2e.*"` | **yes** |
| Screenshot — record refs | `./gradlew :feature:dashboard:updateDebugScreenshotTest` | no |
| Screenshot — verify (CI) | `./gradlew :feature:dashboard:validateDebugScreenshotTest` | no |
| Macrobenchmark | `./gradlew :macrobenchmark:connectedBenchmarkAndroidTest` | **yes (physical device recommended)** |
| Baseline Profile — generate | `./gradlew :app:generateBaselineProfile` | **yes (rooted emu / userdebug)** |
| Everything runnable without a device | `./gradlew test` | no |

`testTag` values for every important composable live in
`core/designsystem/…/testing/LedgerTestTags.kt` — reference them from Compose, UI Automator,
and E2E tests.

---

## 3. Toolchain notes (why some versions look odd)

This project deliberately tracks the **bleeding edge** (see `MEMORY.md` /
`.claude/.../memory/ledgerlite-toolchain.md`):

* **AGP 9.2.1 with its new DSL → built-in Kotlin.** The `org.jetbrains.kotlin.android` plugin is
  *not* applied anywhere; AGP drives Kotlin compilation.
* **KSP 2.3.11** (Hilt + Room codegen). Older KSP (`2.2.x-2.0.x`) is incompatible with AGP's
  built-in Kotlin.
* **compileSdk 37** — required transitively by `androidx.core` / `androidx.lifecycle`.
* `androidx.concurrent:concurrent-futures` is force-resolved to `1.2.0` (convention plugins) to
  reconcile `androidx.test` (`strictly 1.1.0`) with `hilt-android-testing`.

### Known rough edges on this preview toolchain

| Area | Status | Work-around |
|---|---|---|
| **Compose Preview Screenshot Testing** (`com.android.compose.screenshot:0.0.1-alpha16`) | Source set compiles and `update/validateDebugScreenshotTest` run, but `@Preview` **discovery finds 0 previews** on AGP 9.2.1, so no PNGs are produced yet. `failOnNoDiscoveredTests` is disabled in the two feature modules so this doesn't break `check`. | Bump the plugin when an AGP-9-compatible release lands, or run screenshot tasks against AGP 8.7.x. Paparazzi was *not* chosen because it has no AGP 9 support at all — see §6. |
| **`createAndroidComposeRule<Activity>()` on API 37** | Throws `NoSuchMethodException: android.hardware.input.InputManager.getInstance` (Espresso 3.7.0 vs. AOSP 37). Affects the E2E test only; `createComposeRule()` (all per-screen tests) is fine. | Run `:app:connectedDebugAndroidTest` on an **API ≤ 36** emulator until Espresso ships a fix. An `ledger_api34` AVD works. |
| `hiltViewModel()` deprecation warning | Cosmetic — `androidx.hilt:hilt-navigation-compose:1.4.0` moved the symbol. | Optional: switch imports to `androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel`. |

---

## 4. Verified state (as of scaffolding)

* `./gradlew :app:assembleDebug` — **passes**. Full app builds (16 modules).
* `./gradlew :macrobenchmark:assembleBenchmark`, `:baselineprofile:assemble` — **pass**.
* `./gradlew :core:model:test :core:domain:test` — **run**; 35 tests execute, all fail with
  `NotImplementedError` (they're `TODO()` stubs). JUnit 5 platform is wired.
* `./gradlew testDebugUnitTest` — **runs**; every ViewModel test executes and fails on `TODO()`.
  JUnit 4 + `MainDispatcherRule` wired.
* `:core:database`, `:core:data`, `:feature:*` `connectedDebugAndroidTest` — **run on an
  emulator**; Room in-memory DB, `MockWebServerRule`, `createComposeRule()`, and the
  `HiltTestRunner` all initialise; tests fail only at their `TODO()` lines.
* `:app:connectedDebugAndroidTest` — UI Automator + E2E stubs **run on API 34**; they reach the
  `TODO()` bodies (E2E needs API ≤ 36, see §3).
* Screenshot `updateDebugScreenshotTest` — **runs green**, produces no images yet (see §3).

In short: every framework compiles and its runner executes the stub classes. Nothing asserts
anything yet — that's your job.

---

## 5. Checklist — what you still need to fill in

### Unit tests — JVM (JUnit 5), `:core:*/src/test`
- [ ] `core:model` · `MoneyTest` — rounding, arithmetic, ordering, `sum`.
- [ ] `core:domain` · `ValidateTransactionFormUseCaseTest` — every validation branch.
- [ ] `core:domain` · `AddTransactionUseCaseTest` — persists + notifies; stops on invalid/failed.
- [ ] `core:domain` · `DeleteTransactionUseCaseTest`.
- [ ] `core:domain` · `GetSpendingSummaryUseCaseTest`, `GetDashboardUseCaseTest`.
- [ ] `core:domain` · `GetTransactionsUseCaseTest`, `SearchTransactionsUseCaseTest`,
      `GetTransactionDetailUseCaseTest`, `SeedTransactionsUseCaseTest`.
- [ ] `core:domain` · `LoginUseCaseTest`, `RegisterUseCaseTest`, `LogoutUseCaseTest`,
      `ObserveAuthSessionUseCaseTest`.
- [ ] `core:domain` · `ObserveSettingsUseCaseTest`, `SetDarkThemeUseCaseTest`,
      `SetBiometricUnlockUseCaseTest`, `SetDefaultCurrencyUseCaseTest`.
- [ ] `core:domain` · `GetAccountsUseCaseTest`, `GetTotalBalanceUseCaseTest`.

### Unit tests — ViewModels (JUnit 4), `:feature:*/src/test`
- [ ] `AuthViewModelTest`, `DashboardViewModelTest`, `TransactionListViewModelTest`,
      `TransactionDetailViewModelTest`, `AddTransactionViewModelTest`, `SearchViewModelTest`,
      `SettingsViewModelTest`. Provide fakes for the injected use cases; assert on the state flow
      via Turbine. `MainDispatcherRule` is already `@get:Rule`.

### Instrumented — `:core:*/src/androidTest`
- [ ] `core:database` · `TransactionDaoTest`, `AccountDaoTest` — in-memory DB is set up in
      `@Before`; assert on inserts, paging order, filtered query, aggregation, delete.
- [ ] `core:data` · `TransactionRepositoryIntegrationTest` — build
      `TransactionRepositoryImpl(dao, accountDao, api)` with a Retrofit `LedgerApi` pointed at
      `mockWebServer.baseUrl`; assert add/paging/seed/refresh.

### Compose UI — `:feature:*/src/androidTest`
- [ ] One class per screen: `LoginScreenTest`, `RegisterScreenTest`, `DashboardScreenTest`,
      `TransactionListScreenTest`, `TransactionDetailScreenTest`, `AddTransactionScreenTest`,
      `SearchScreenTest`, `SettingsScreenTest`. Each has `createComposeRule()`; render a
      stateless slice of the screen fed a fake state, drive it via `onNodeWithTag(LedgerTestTags.…)`.
      (A commented example is in `LoginScreenTest`.)
- [ ] Consider extracting stateless `XxxContent(state, callbacks)` composables from each `…Route`
      so UI + screenshot tests don't need Hilt.

### UI Automator — `:app/src/androidTest/…/uiautomator`
- [ ] `BiometricPromptFlowTest` — enrol a fingerprint on the emulator, tap
      `LOGIN_BIOMETRIC_BUTTON`, drive the system dialog, `adb -e emu finger touch 1`.
- [ ] `NotificationTapFlowTest` — add an expense, `device.openNotification()`, tap it, assert
      Transaction Detail for the right id (deep link `ledgerlite://transaction/{id}`).

### End-to-end — `:app/src/androidTest/…/e2e`
- [ ] `LoginToDashboardE2ETest` — full journey through the real NavHost/VMs/Room + MockWebServer.
      The server is already injected (from `:core:testing` `FakeNetworkModule`, see §7); script
      per-scenario responses by swapping `mockWebServer.dispatcher`. Run on API ≤ 36.

### Screenshot — `:feature:{dashboard,addexpense}/src/screenshotTest`
- [ ] Replace the placeholder `Text` in each `@Preview` with a real stateless content composable
      + fixed data. Keep the light / dark / error variants. Then `updateDebugScreenshotTest` to
      record and commit references under `src/debug/screenshotTest/reference/`.
- [ ] Re-check preview discovery after the next screenshot-plugin bump (see §3).

### Macrobenchmark — `:macrobenchmark`
- [ ] `ColdStartBenchmark` — `measureRepeated(StartupMode.COLD) { pressHome(); startActivityAndWait() }`.
- [ ] `TransactionListScrollBenchmark` — seed 10k rows, navigate to the list, fling with
      `FrameTimingMetric()`.
- [ ] `BaselineProfileGenerator` (`:baselineprofile`) — `baselineProfileRule.collect(...)` walking
      the critical journey; then wire the generated `baseline-prof.txt` (the plugin already
      does the `:app` side).

---

## 6. Screenshot framework choice — Paparazzi vs Compose Preview Screenshot Testing

**Chosen: Compose Preview Screenshot Testing (`com.android.compose.screenshot`).**

| | Compose Preview Screenshot Testing | Paparazzi |
|---|---|---|
| Vendor | Google / AGP team | Cash App |
| AGP 9 support | Yes (versioned with AGP; still `alpha`) | **None** — Paparazzi trails AGP by months and has no AGP 9 build |
| Test authoring | Reuse `@Preview` composables | Dedicated `@Test` + `paparazzi.snapshot { }` |
| Runs on | JVM (layoutlib) | JVM (layoutlib) |
| Light/dark | `@Preview(uiMode = …)` multipreview | `paparazzi(deviceConfig = …)` per test |

Because the project is pinned to AGP 9.2.1, Paparazzi simply won't apply. The official plugin is
the only viable option here; its `alpha` discovery quirk (see §3) is the price of the bleeding
edge.

---

## 7. Swapping in test doubles (Hilt)

`core:testing` (its **main** source set) carries the shared infra so every module can use it:

| Helper | Purpose |
|---|---|
| `HiltTestRunner` | `testInstrumentationRunner` — swaps in `HiltTestApplication`. Already set for `:app` and every `:feature:*`. |
| `MainDispatcherRule` | Replaces `Dispatchers.Main` with a `TestDispatcher` in ViewModel unit tests. |
| `MockWebServerRule` | Starts/stops a `MockWebServer` (bound to loopback); `baseUrl` + `enqueueDispatcher(...)`. For non-Hilt tests that build Retrofit by hand. |
| `network.FakeNetworkModule` | `@TestInstallIn` module — see below. |
| `network.MockApiDispatcher` | Happy-path canned API responses (`auth`, `accounts`, `transactions`). |
| `TestDatabaseFactory.create()` | In-memory `LedgerDatabase`. |
| `TestData` / `FakeDataFactory` | Fixed single records + deterministic 10k+ batches. |

### The network seam

`:core:network` no longer bundles `MockWebServer`. `NetworkModule` provides the Retrofit stack;
a **separate** `NetworkUrlModule` provides `@ApiBaseUrl` from `BuildConfig.API_BASE_URL`.

`:core:testing` ships `FakeNetworkModule`, which replaces **only** `NetworkUrlModule` for every
`@HiltAndroidTest`:

```kotlin
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [NetworkUrlModule::class])
object FakeNetworkModule {
    @Provides @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer().apply {
        dispatcher = MockApiDispatcher()
        start(InetAddress.getLoopbackAddress(), 0)
    }
    @Provides @ApiBaseUrl
    fun provideBaseUrl(server: MockWebServer): String = server.url("/").toString()
}
```

So any test class annotated `@HiltAndroidTest` with `@get:Rule val hiltRule = HiltAndroidRule(this)`
gets the real Retrofit/OkHttp stack pointed at an in-process server — no per-module setup. To
script responses, `@Inject lateinit var mockWebServer: MockWebServer` and swap its `dispatcher`
(or `enqueue(...)`) in `@Before` / per test. `LoginToDashboardE2ETest` shows the wiring.

For a module-specific double (e.g. a fake `LedgerApi`), still drop a `@TestInstallIn` module in
that module's own `src/androidTest`; it composes with `FakeNetworkModule` as long as they don't
both `replaces` the same module.
