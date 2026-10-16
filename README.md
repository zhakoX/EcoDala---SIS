# EcoDala

EcoDala is a mobile application designed to encourage students and young people to recycle through gamification.

The application allows users to discover recycling points, check accepted waste types, track their EcoPoints and achievements, and compare their progress with other users through a leaderboard.

## SIS3 Project

This project was developed as part of the SIS3 Jetpack Compose assignment.

The project focuses on:

- Jetpack Compose UI development
- Material 3 design
- Reusable composables
- Navigation Compose
- Data-driven UI
- List and detail screens
- Light and Dark themes
- State management with Compose

## Screens

The application consists of three main screens.

### 1. Home Screen

The Home Screen provides an overview of the user's environmental progress.

It displays:

- Current EcoPoints
- User level
- Global ranking
- Virtual tree progress
- Quick actions
- Recent achievements
- Bottom navigation

<table>
<tr>
<th align="center">Light Mode</th>
<th align="center">Dark Mode</th>
</tr>
<tr>
<td align="center">
<img src="screens/home_light.png" width="280">
</td>
<td align="center">
<img src="screens/home_dark.png" width="280">
</td>
</tr>
</table>

---

### 2. Leaderboard Screen

The Leaderboard Screen displays users ranked by their EcoPoints.

The screen uses a `LazyColumn` and contains 10 users.

Each leaderboard item displays:

- Rank
- User name
- Faculty
- EcoPoints

<table>
<tr>
<th align="center">Light Mode</th>
<th align="center">Dark Mode</th>
</tr>
<tr>
<td align="center">
<img src="screens/leaderboard_light.png" width="280">
</td>
<td align="center">
<img src="screens/leaderboard_dark.png" width="280">
</td>
</tr>
</table>

---

### 3. Recycling Point Detail Screen

The Recycling Point Detail Screen provides detailed information about a recycling center.

It includes:

- Recycling center image
- Address
- Phone number
- Opening hours
- Accepted waste types
- Sustainability information
- Route action
- Call action
- Share action
- Favorite toggle

<table>
<tr>
<th align="center">Light Mode</th>
<th align="center">Dark Mode</th>
</tr>
<tr>
<td align="center">
<img src="screens/recycling_point_light.png" width="280">
</td>
<td align="center">
<img src="screens/recycling_point_dark.png" width="280">
</td>
</tr>
</table>

## Design: From Sketch to Application

The initial UI sketches were created before implementing the final application.

The sketches are stored in the `/design` directory.

The final implementation keeps the main structure of the original sketches while refining spacing, colors, icons, typography, component layouts, and Light/Dark theme support.

### Home Screen

#### Initial Sketch

<p align="center">
<img src="design/Home%20Dashboard.jpg" width="500">
</p>

#### Final Application

The final Home Screen implements the main structure of the original dashboard sketch with EcoPoints, virtual tree progress, quick actions, recent achievements, and bottom navigation.

<p align="center">
<img src="screens/home_light.png" width="280">
</p>

### Leaderboard Screen

#### Initial Sketch

<p align="center">
<img src="design/List%20Screen.jpg" width="500">
</p>

#### Final Application

The final Leaderboard Screen transforms the list concept from the original sketch into a data-driven `LazyColumn` containing 10 users with their rank, faculty, and EcoPoints.

<p align="center">
<img src="screens/leaderboard_light.png" width="280">
</p>

### Recycling Point Detail Screen

#### Initial Sketch

<p align="center">
<img src="design/Detail%20Screen.jpg" width="500">
</p>

#### Final Application

The final Recycling Point Detail Screen keeps the main information hierarchy of the original detail sketch and adds accepted waste types, sustainability information, route, call, share, and favorite actions.

<p align="center">
<img src="screens/recycling_point_light.png" width="280">
</p>

## Main Features

- 3 Jetpack Compose screens
- Navigation Compose
- `LazyColumn` with 10 leaderboard users
- `LazyRow` for Quick Actions
- ID-based navigation to the recycling point detail screen
- Back navigation
- Long-title handling with `TextOverflow.Ellipsis`
- Friendly empty state for the leaderboard
- Resource-based recycling center image
- Meaningful image `contentDescription`
- Favorite state exposed through `RecyclingPointUiState` and toggled by `RecyclingPointViewModel`
- Reusable parameterized composables
- Custom EcoDala color scheme
- Light Mode
- Dark Mode
- Material 3 components
- Local data models and sample data
- Bottom navigation

## SIS3 Requirements

The project implements the main SIS3 requirements:

| Requirement | Implementation |
|---|---|
| 3+ screens | Home, Leaderboard, Recycling Point Detail |
| List screen | Leaderboard |
| Detail screen | Recycling Point Detail |
| `LazyColumn` | Leaderboard with 10 users |
| `LazyRow` | Home Quick Actions |
| `Scaffold` | Used on all screens |
| `TopAppBar` | Used on all screens |
| Back navigation | Leaderboard and Recycling Point Detail |
| ID navigation | Recycling Point Detail receives `id` |
| Long-title handling | `TextOverflow.Ellipsis` |
| Empty state | Leaderboard |
| Image resource | Recycling center image |
| Image description | Meaningful `contentDescription` |
| Custom color scheme | EcoDala Light/Dark theme |
| Dark Mode | Dedicated Dark theme |
| Reusable composables | Components package |
| Screen state | Immutable UiState collected from ViewModel StateFlow |
| Navigation | Navigation Compose |
| Data classes | User, Achievement, RecyclingPoint |

## Project Structure

```text
EcoDala---SIS/
├── app/
│   └── src/
│       └── main/
│           ├── java/kz/ecodala/
│           │   ├── components/
│           │   ├── data/
│           │   ├── model/
│           │   ├── navigation/
│           │   ├── screens/
│           │   └── ui/
│           │       └── theme/
│           │
│           └── res/
│               └── drawable/
│
├── design/
│   ├── Detail Screen.jpg
│   ├── Home Dashboard.jpg
│   └── List Screen.jpg
│
├── screens/
│   ├── home_light.png
│   ├── home_dark.png
│   ├── leaderboard_light.png
│   ├── leaderboard_dark.png
│   ├── recycling_point_light.png
│   └── recycling_point_dark.png
│
├── AI_USAGE.md
├── README.md
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Reusable Components

The project uses reusable parameterized Compose components, including:

- `ActionItem`
- `EcoCard`
- `EcoBottomBar`
- `LeaderboardItem`
- `RankingHeader`
- `RecentAchievementItem`
- `VirtualTreeCard`
- `WasteTypeChip`

These components separate repeated UI elements from the screen implementations and make the interface easier to maintain.

## Technology Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Android Studio**
- **Gradle**

## Application Architecture

The project is organized into separate packages according to their responsibilities:

- `screens` — application screens
- `components` — reusable UI components
- `model` — data classes
- `data` — local application data
- `navigation` — Navigation Compose configuration
- `ui.theme` — application theme and color scheme

This structure keeps UI, data, navigation, and reusable components separated.

## SIS4: Implemented MVVM Architecture

The current SIS4 implementation uses a Route composable as the boundary between Navigation Compose and each stateless screen. `EcoRepository` is currently an in-memory data source over `EcoDalaData.kt`; the planned Django backend is not implemented and would be integrated behind this repository layer rather than called by screens.

```mermaid
flowchart LR
    Data[EcoDalaData.kt sample data] --> Repo[EcoRepository]

    Nav[AppNavigation] --> HR[HomeRoute]
    Nav --> LR[LeaderboardRoute]
    Nav --> RR[RecyclingPointRoute]

    HR --> HVM[HomeViewModel]
    LR --> LVM[LeaderboardViewModel]
    RR --> RVM[RecyclingPointViewModel]

    HVM --> HState[HomeUiState]
    LVM --> LState[LeaderboardUiState]
    RVM --> RState[RecyclingPointUiState]

    HVM --> Repo
    LVM --> Repo
    RVM --> Repo

    HR -->|state + callbacks| HS[HomeScreen]
    LR -->|state + callbacks| LS[LeaderboardScreen]
    RR -->|state + callbacks| RS[RecyclingPointScreen]

    HState --> HR
    LState --> LR
    RState --> RR
```

`HomeRoute`, `LeaderboardRoute`, and `RecyclingPointRoute` obtain their ViewModel and collect its read-only `StateFlow` with `collectAsStateWithLifecycle()`. Each ViewModel owns a private `MutableStateFlow` of its immutable UiState, calls `EcoRepository`, and publishes copied state. The screens receive only UiState values and callbacks, so they do not access the repository or sample data directly.

### Click trace: Recycling Point favorite

1. In `RecyclingPointScreen`, the favorite `IconButton` invokes its `onToggleFavorite` callback.
2. `RecyclingPointRoute` supplies that callback as `RecyclingPointViewModel::toggleFavorite`.
3. `RecyclingPointViewModel.toggleFavorite()` gets the displayed point ID, calls `EcoRepository.toggleFavorite(pointId)`, and copies the returned value into `RecyclingPointUiState.isFavorite`.
4. `RecyclingPointRoute` observes the updated `uiState` with `collectAsStateWithLifecycle()`, then passes it back to `RecyclingPointScreen`, which selects `Favorite` or `FavoriteBorder` from `uiState.isFavorite`.

This is unidirectional data flow: state moves from the repository through a ViewModel and Route to a screen, while user events move back as callbacks from the screen to the ViewModel. The screen does not mutate business state itself.

## Build and Run

1. Open the project in Android Studio.
2. Wait for Gradle synchronization to complete.
3. Start an Android Emulator or connect an Android device.
4. Select the `app` run configuration.
5. Run the application.

## SIS4 B4: Lifecycle Logging and Rotation Proof

Use Android Studio Logcat with the app process selected and this filter query:

```text
tag:SIS4_LIFECYCLE | tag:SIS4_VIEWMODEL
```

To inspect a configuration change manually:

1. Run the app on a device or emulator with auto-rotate enabled.
2. Open any screen that creates the ViewModel you want to observe, then clear Logcat.
3. Rotate the device or emulator from portrait to landscape (for an emulator, use the Rotate Left or Rotate Right toolbar action).
4. Rotate it back if desired and inspect the filtered Logcat entries.
5. Confirm that the Activity destruction/creation callbacks appear; a ViewModel scoped to the retained navigation entry normally does not log a new initialization or `onCleared()` solely because of rotation.

**Expected log ordering example (illustrative; not captured output):**

```text
SIS4_LIFECYCLE: MainActivity.onPause
SIS4_LIFECYCLE: MainActivity.onStop
SIS4_LIFECYCLE: MainActivity.onDestroy
SIS4_LIFECYCLE: MainActivity.onCreate
SIS4_LIFECYCLE: MainActivity.onStart
SIS4_LIFECYCLE: MainActivity.onResume
```

Android recreates an Activity during a configuration change so it can reload configuration-dependent resources and layout state. A ViewModel is retained across that recreation when its owner, such as the Activity or retained navigation back-stack entry, is retained by the framework. As a result, the existing ViewModel normally remains available after rotation and keeps its UI state. `onCleared()` is instead expected when that owner is permanently removed, such as when its navigation entry is popped or the Activity is finishing.

## AI Usage

AI was used as a development assistant during the project for:

- understanding Jetpack Compose concepts;
- reviewing the project structure;
- refactoring UI components;
- implementing Navigation Compose;
- debugging;
- checking SIS3 requirements;
- reviewing Light and Dark themes;
- improving the UI.

The final application structure, UI decisions, data, testing, and manual adjustments were reviewed by the developer.

Detailed information about AI usage, prompts, corrections, and manually implemented changes is available in [AI_USAGE.md](AI_USAGE.md).

## Repository

The source code is available in this GitHub repository:

https://github.com/zhakoX/EcoDala---SIS
