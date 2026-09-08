# EcoDala

EcoDala is a mobile environmental platform that helps people recycle waste correctly and participate in sustainable everyday activities. The application allows users to find recycling points, identify waste types, submit recycling activities, and track their environmental progress through EcoPoints and achievements.

## Functionality

1. User can create an account and log in.
2. User can view recycling points on a map.
3. User can open a recycling point and view its details, including accepted waste types.
4. User can scan or select a waste type and view recycling instructions.
5. User can submit a recycling activity with waste type and optional details.
6. App confirms a successful recycling submission and awards EcoPoints.
7. User can view their recycling history and previously submitted activities.
8. User can view achievements and their EcoPoints progress.

## Project Structure

```text
EcoDala-SIS/
├── app/
│   ├── src/
│   │   ├── androidTest/
│   │   ├── main/
│   │   │   ├── java/kz/ecodala/
│   │   │   └── res/
│   │   └── test/
│   └── build.gradle.kts
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md
```


## Build and Run

1. Open the project in Android Studio.
2. Wait for Gradle synchronization to complete.
3. Connect an Android device or start an Android Emulator.
4. Select the `app` run configuration.
5. Run the application.

## Technology

- Kotlin
- Jetpack Compose
- Android Studio
- Gradle