# SIS4 MVVM Architecture Plan - EcoDala

## Purpose and status

This is the Part A plan for refactoring EcoDala to Model-View-ViewModel (MVVM). It describes the target architecture only; the repository, routes, ViewModels, and UiState classes below do **not** exist yet.

## Current implementation (verified)

The current navigation entry point is `app/src/main/java/kz/ecodala/navigation/AppNavigation.kt`, whose `AppNavigation()` composable calls these screen composables directly:

- `HomeScreen(onLeaderboardClick, onRecyclingPointClick)` in `app/src/main/java/kz/ecodala/screens/home/HomeScreen.kt`.
- `LeaderboardScreen(onBackClick)` in `app/src/main/java/kz/ecodala/screens/leaderboard/LeaderboardScreen.kt`.
- `RecyclingPointScreen(recyclingPointId, onBackClick)` in `app/src/main/java/kz/ecodala/screens/recycling/RecyclingPointScreen.kt`.

`HomeScreen` directly reads `currentUser`, `homeStats`, and `achievements`; `LeaderboardScreen` directly reads `users`; and `RecyclingPointScreen` directly reads `recyclingPoints`. Those values are currently top-level sample data in `app/src/main/java/kz/ecodala/data/EcoDalaData.kt`.

The existing recycling detail screen keeps its favorite value locally with `remember { mutableStateOf(false) }`. There are currently no Route composables, ViewModels, `StateFlow`s, repositories, or lifecycle-aware state collection calls.

## Target MVVM architecture (planned)

```mermaid
flowchart TD
    Nav[AppNavigation - existing] --> HR[HomeRoute - planned]
    Nav --> LR[LeaderboardRoute - planned]
    Nav --> RR[RecyclingPointRoute - planned]

    HR -->|collectAsStateWithLifecycle| HVM[HomeViewModel - planned]
    LR -->|collectAsStateWithLifecycle| LVM[LeaderboardViewModel - planned]
    RR -->|collectAsStateWithLifecycle| RVM[RecyclingPointViewModel - planned]

    HR --> HS[HomeScreen(HomeUiState, callbacks) - refactored]
    LR --> LS[LeaderboardScreen(LeaderboardUiState, callbacks) - refactored]
    RR --> RS[RecyclingPointScreen(RecyclingPointUiState, callbacks) - refactored]

    HVM --> HUS[HomeUiState - planned]
    LVM --> LUS[LeaderboardUiState - planned]
    RVM --> RUS[RecyclingPointUiState - planned]

    HVM --> Repo[EcoRepository - planned]
    LVM --> Repo
    RVM --> Repo
    Repo --> Data[EcoDalaData.kt - existing sample data]
```

Planned responsibilities:

- `HomeRoute`, `LeaderboardRoute`, and `RecyclingPointRoute` will obtain the appropriate ViewModel, collect its public `StateFlow` with `collectAsStateWithLifecycle()`, and pass state plus event callbacks to a stateless screen.
- `HomeViewModel`, `LeaderboardViewModel`, and `RecyclingPointViewModel` will each keep a private `MutableStateFlow`, expose it as read-only `StateFlow`, and update immutable state with `copy()` from `viewModelScope`.
- `EcoRepository` will be the only new layer that accesses `EcoDalaData.kt`. It will own the in-memory favorite IDs, simulate loading with `delay()`, and expose a configurable error flag for demonstrating error handling.
- Refactored screens will receive a UiState and callbacks only. They will not import the repository, sample data, or ViewModels.

## Planned UI state and screen behavior

All UiState properties will be `val` so every update creates a new immutable state instance.

### `HomeUiState` (planned)

```kotlin
data class HomeUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val stats: HomeStats? = null,
    val recentAchievements: List<Achievement> = emptyList(),
    val errorMessage: String? = null
)
```

- Loading: `isLoading` is true.
- Content: `user` and `stats` are non-null, and there is no error.
- Empty: loading is complete, there is no error, and `user` or `stats` is unavailable.
- Error: `errorMessage` is non-null; the screen shows a Retry action.

### `LeaderboardUiState` (planned)

```kotlin
data class LeaderboardUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val errorMessage: String? = null
)
```

- Loading: `isLoading` is true.
- Content: loading is complete, no error exists, and `users` is non-empty.
- Empty: loading is complete, no error exists, and `users` is empty.
- Error: `errorMessage` is non-null; the screen shows a Retry action.

### `RecyclingPointUiState` (planned)

```kotlin
data class RecyclingPointUiState(
    val isLoading: Boolean = false,
    val recyclingPoint: RecyclingPoint? = null,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null
)
```

- Loading: `isLoading` is true.
- Content: loading is complete, no error exists, and `recyclingPoint` is non-null.
- Empty: loading is complete, no error exists, and no recycling point matches the supplied ID.
- Error: `errorMessage` is non-null; the screen shows a Retry action.

## Planned user events and ViewModel handlers

| User event in the refactored UI | Planned handler | Intended result |
| --- | --- | --- |
| Favorite icon tap | `RecyclingPointViewModel.toggleFavorite(pointId)` | Ask the repository to change the favorite ID and publish a copied state with the new `isFavorite`. |
| Retry tap after an error | `retry()` on the screen's ViewModel | Start the relevant load again after clearing the error. |
| Leaderboard refresh tap | `LeaderboardViewModel.refreshLeaderboard()` | Reload ranked users through the repository. |
| Recycling detail route opened | `RecyclingPointViewModel.loadPoint(pointId)` | Load the requested point and publish loading, content, empty, or error state. |

## Trace one click: favorite icon

**Current behavior:** In `RecyclingPointScreen`, the favorite `IconButton` flips its own local `isFavorite` value. It does not call a callback, ViewModel, or repository, so the value belongs to that composable instance.

**Planned behavior:** The refactored `RecyclingPointScreen` will invoke an `onToggleFavorite` callback. `RecyclingPointRoute` will delegate that event to `RecyclingPointViewModel.toggleFavorite(pointId)`. The ViewModel will call the planned `EcoRepository`, which will update its in-memory favorite IDs. The ViewModel will publish a copied `RecyclingPointUiState`; the Route's lifecycle-aware collection will recompose the screen with the updated icon.

## Intended unidirectional data flow

Data flows in one direction: `EcoDalaData.kt` -> planned `EcoRepository` -> planned ViewModel `StateFlow<UiState>` -> planned Route -> refactored stateless screen. Events travel back in the opposite direction only as callbacks: screen -> Route -> ViewModel -> Repository. The screen never changes repository data or its displayed business state directly.
