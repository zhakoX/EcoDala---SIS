# Exact changed and added source paths

Paths are relative to `C:/Users/zhako/Desktop/Android/`. Original comparison: `original-checkpoint` (`92d2f5d`). Intentionally deleted files are listed separately in [SIS4_DELETED_FILES.md](SIS4_DELETED_FILES.md).

| File | Reason |
|---|---|
| `API_CONTRACT.md` | Keep historical document with an explicit pre-SIS4 notice. |
| `BACKEND_HANDOFF.md` | Keep historical document with an explicit pre-SIS4 notice. |
| `README.md` | Describe the actual local SIS4 version, architecture, build, persistence, limitations and recovery. |
| `app/build.gradle.kts` | Remove backend/CameraX/Google SDKs; add lifecycle collection and API desugaring; retain JVM 21 and signing. |
| `app/proguard-rules.pro` | Remove unused DTO/OkHttp rules; keep retained model/OSM rules. |
| `app/src/main/AndroidManifest.xml` | Keep INTERNET for OSM tiles; remove camera/GPS permissions, API key and backend cleartext placeholder. |
| `app/src/main/java/com/ecodala/MainActivity.kt` | Six lifecycle logs; restore DataStore before navigation; process-scoped settings/session observers survive rotation. |
| `app/src/main/java/com/ecodala/core/data/dummy/DummyEcoData.kt` | Expand to 12 clearly labelled demo points; remove secondary map/scanner fixtures; retain historical ledger and models. |
| `app/src/main/java/com/ecodala/core/data/local/EcoLocalCache.kt` | Replace Moshi/API cache with a small DataStore JSON snapshot of added submissions and favourite IDs. |
| `app/src/main/java/com/ecodala/core/data/repository/DemoAuthRepository.kt` | Reuse local demo identity; remove fake token writes; login/registration remain local. |
| `app/src/main/java/com/ecodala/core/data/repository/DemoPointsEconomyRepository.kt` | Adapt shared local repository to retained economy use cases, replacing static fixture reads. |
| `app/src/main/java/com/ecodala/core/data/repository/LocalEcoRepository.kt` | Shared source of truth for points/history/favourites; Result, delay, explicit errors, persistence and progress. |
| `app/src/main/java/com/ecodala/core/domain/model/EcoModels.kt` | Retain shared recycling/user/tree/challenge/ranking models; remove only deleted feature models. |
| `app/src/main/java/com/ecodala/core/domain/usecase/EngagementUseCases.kt` | Use live submissions for achievement/streak progress; grams conversion; tree milestone uses the existing points-per-level constant. |
| `app/src/main/java/com/ecodala/core/domain/usecase/PointsEconomyUseCases.kt` | Keep wallet logic; include ISO submission dates in monthly impact; convert grams/bags. |
| `app/src/main/java/com/ecodala/core/localization/EcoStrings.kt` | Preserve EN/RU/KK catalogue; remove unused deleted-feature entries; local demo wording and small localized labels. |
| `app/src/main/java/com/ecodala/core/navigation/EcoDalaNavGraph.kt` | Remove dead routes/callbacks; registration directly to Home; declare String pointId and preserve details SavedStateHandle. |
| `app/src/main/java/com/ecodala/core/navigation/EcoDalaRoute.kt` | Retain supported destinations; remove only approved obsolete routes. |
| `app/src/main/java/com/ecodala/core/session/SessionManager.kt` | Persist only logged-in flag, ID, name and email; no tokens, refresh or password storage. |
| `app/src/main/java/com/ecodala/core/settings/AppSettingsStore.kt` | Keep language/theme/unit persistence; remove camera/location/push/privacy options for removed functionality. |
| `app/src/main/java/com/ecodala/core/ui/components/EcoDalaBottomBar.kt` | Preserve existing five destinations and visuals; increase custom item hit areas to at least 48dp. |
| `app/src/main/java/com/ecodala/feature/achievements/presentation/AchievementsScreen.kt` | Preserve existing visuals; lifecycle-aware state collection in Route; remove obsolete callbacks where relevant. |
| `app/src/main/java/com/ecodala/feature/achievements/presentation/AchievementsViewModel.kt` | Keep existing class and calculations; replace API/static reads with observation of shared local data; expose read-only StateFlow. |
| `app/src/main/java/com/ecodala/feature/auth/presentation/AuthViewModel.kt` | Keep existing class and calculations; replace API/static reads with observation of shared local data; expose read-only StateFlow. |
| `app/src/main/java/com/ecodala/feature/auth/presentation/LoginScreen.kt` | Preserve existing visuals; lifecycle-aware state collection in Route; remove obsolete callbacks where relevant. |
| `app/src/main/java/com/ecodala/feature/auth/presentation/RegisterScreen.kt` | Preserve existing visuals; lifecycle-aware state collection in Route; remove obsolete callbacks where relevant. |
| `app/src/main/java/com/ecodala/feature/challenges/presentation/ChallengesScreen.kt` | Preserve existing visuals; lifecycle-aware state collection in Route; remove obsolete callbacks where relevant. |
| `app/src/main/java/com/ecodala/feature/challenges/presentation/ChallengesViewModel.kt` | Keep existing class and calculations; replace API/static reads with observation of shared local data; expose read-only StateFlow. |
| `app/src/main/java/com/ecodala/feature/home/presentation/HomeScreen.kt` | Preserve cards/tree/quick actions; remove notification button; 48dp language controls; cap displayed tree milestone. |
| `app/src/main/java/com/ecodala/feature/home/presentation/HomeViewModel.kt` | Observe shared progress and identity; reuse wallet/impact/streak use cases with the same repository; cap next tree level at 10. |
| `app/src/main/java/com/ecodala/feature/leaderboard/presentation/LeaderboardScreen.kt` | Keep podium/ranking/faculty components; consume prepared summary; show actual demo display name with ellipsis; lifecycle collection. |
| `app/src/main/java/com/ecodala/feature/leaderboard/presentation/LeaderboardViewModel.kt` | Retain faculty fixtures and ranking UI models; observe shared user points; prepare faculty aggregates outside Composable. |
| `app/src/main/java/com/ecodala/feature/map/presentation/MapScreen.kt` | Reuse OSM marker/card/filter artwork; recycling only; stateless screen and lifecycle-aware Route; LazyColumn/LazyRow, favourite toggle, four states, five previews. |
| `app/src/main/java/com/ecodala/feature/map/presentation/MapViewModel.kt` | Retain main ViewModel; immutable MapUiState, read-only flow, search/category/favourite/retry events, SavedStateHandle, init/onCleared logs. |
| `app/src/main/java/com/ecodala/feature/map/presentation/RecyclingPointDetailsScreen.kt` | Keep design; lifecycle-aware Route, loading/error, external geo Intent and existing share/dial actions. |
| `app/src/main/java/com/ecodala/feature/map/presentation/RecyclingPointDetailsViewModel.kt` | Keep pointId from SavedStateHandle; exact local lookup; loading/error; no unrelated-point fallback; constructor usable by saved-state factory. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/ProfileScreen.kt` | Preserve layout, stats and menus; remove notifications; render computed score/milestone instead of hardcoded 75/Level 5. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/ProfileViewModel.kt` | Shared identity and points; local logout/edit; compute demo score and next milestone from shared points. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/RecyclingHistoryScreen.kt` | Preserve existing visuals; lifecycle-aware state collection in Route; remove obsolete callbacks where relevant. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/RecyclingHistoryViewModel.kt` | Keep existing class and calculations; replace API/static reads with observation of shared local data; expose read-only StateFlow. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/SettingsScreen.kt` | Preserve theme/unit/language design; remove only obsolete sections. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/SettingsViewModel.kt` | Keep setters for retained preferences only. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/SupportScreen.kt` | Preserve support UI; lifecycle collection and larger custom chip hit areas. |
| `app/src/main/java/com/ecodala/feature/profile/presentation/SupportViewModel.kt` | Keep local support; illustrative contacts and recycling-history topic replace scanner references. |
| `app/src/main/java/com/ecodala/feature/splash/presentation/SplashViewModel.kt` | Keep existing class and calculations; replace API/static reads with observation of shared local data; expose read-only StateFlow. |
| `app/src/main/java/com/ecodala/feature/submit/presentation/SubmitWasteScreen.kt` | Preserve type/quantity/unit/comment form; remove capture panel and scanner callbacks; success then explicit Continue; 48dp stepper controls. |
| `app/src/main/java/com/ecodala/feature/submit/presentation/SubmitWasteViewModel.kt` | Remove camera/scanner state; retain quantity/unit/reward logic; validate and submit to shared local repository; prevent duplicate in-flight submits. |
| `app/src/main/java/com/ecodala/feature/tree/presentation/VirtualTreeScreen.kt` | Preserve existing visuals; lifecycle-aware state collection in Route; remove obsolete callbacks where relevant. |
| `app/src/main/java/com/ecodala/feature/tree/presentation/VirtualTreeViewModel.kt` | Keep existing class and calculations; replace API/static reads with observation of shared local data; expose read-only StateFlow. |
| `app/src/test/java/com/ecodala/core/data/repository/LocalEcoRepositoryTest.kt` | Retain/adapt meaningful tests for the retained feature; remove only checks for intentionally deleted functionality. |
| `app/src/test/java/com/ecodala/feature/SharedProgressViewModelsTest.kt` | Retain/adapt meaningful tests for the retained feature; remove only checks for intentionally deleted functionality. |
| `app/src/test/java/com/ecodala/feature/leaderboard/presentation/FacultyLeaderboardSummaryTest.kt` | Retain/adapt meaningful tests for the retained feature; remove only checks for intentionally deleted functionality. |
| `app/src/test/java/com/ecodala/feature/map/presentation/MapUiStateTest.kt` | Retain/adapt meaningful tests for the retained feature; remove only checks for intentionally deleted functionality. |
| `app/src/test/java/com/ecodala/feature/map/presentation/MapViewModelTest.kt` | Retain/adapt meaningful tests for the retained feature; remove only checks for intentionally deleted functionality. |
| `app/src/test/java/com/ecodala/feature/map/presentation/RecyclingPointDetailsViewModelTest.kt` | Retain/adapt meaningful tests for the retained feature; remove only checks for intentionally deleted functionality. |
| `gradle.properties` | Reduce heap to 1536 MB, two workers and in-process Kotlin after a real out-of-memory run; remove incorrect Gradle-9.4-specific setting. |
| `local.properties.example` | SDK configuration only; no backend URL or API key. |

Added documentation: `README.md`, `AI_USAGE.md`, `SIS4_DEFENSE_GUIDE.md`, `design/SIS4_MVVM.md`, and the files in `docs/`. Evidence includes actual Logcat, UI XML, screenshots and test/build results.
