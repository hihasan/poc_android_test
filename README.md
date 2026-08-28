# POC Testing

Android app scaffold built with Jetpack Compose.

## Requirements

- Android Studio (or JDK 11+ and the Android SDK)
- Compile/target SDK 36, min SDK 33

## Getting Started

Build and install a debug build via Gradle:

```bash
./gradlew installDebug
```

Or open the project in Android Studio and run the `app` configuration.

## Project Structure

- `app/` — Android application module (`xyz.hihasan.testing`)
- `app/src/main` — application source and Compose UI theme
- `app/src/test` — unit tests
- `app/src/androidTest` — instrumented tests
