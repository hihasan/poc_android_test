# LedgerLite

A personal-finance tracker POC built to practice the Android testing stack. Kotlin · Jetpack
Compose · MVVM · Hilt · Room · Retrofit/OkHttp · Coroutines/Flow · Navigation Compose · Paging 3.

> The app is a working skeleton (all screens, real navigation, seeded data). **All tests are
> compiling, runnable stubs with `TODO()` bodies** — see [TESTING.md](TESTING.md).

## Screens

Login/Register (with biometric prompt) · Dashboard (balance + spending chart) · Transaction List
(paged) · Transaction Detail · Add Expense/Transfer (form validation) · Search/Filter · Settings
(theme, biometric toggle, "generate 10k demo transactions", logout).

## Architecture

Clean multi-module: `:core:{model,common,domain,designsystem,database,network,data,notifications,testing}`
+ `:feature:{auth,dashboard,transactions,addexpense,search,settings}` + `:app` +
`:macrobenchmark` + `:baselineprofile`. Gradle convention plugins live in `build-logic/`.

`data → domain ← ui`. Repositories are the single source of truth (Room); the network layer
talks to an in-process `MockWebServer` "for now".

## Build & run

```bash
./gradlew :app:installDebug          # build + install
./gradlew :app:assembleDebug         # APK only
```

Requires the Android SDK with **platform 37** and an AGP-9-aware Android Studio. See
[TESTING.md §3](TESTING.md) for the (deliberately bleeding-edge) toolchain and its known rough
edges.

## Testing

Everything — which test lives where, how to run it, and the fill-in checklist — is in
[TESTING.md](TESTING.md).
