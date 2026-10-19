# EcoDala SIS4 refactor — final report

The existing EcoDala application was simplified in place. It uses local demo repositories, keeps the protected feature screens and their visual components, and remains on JDK/JVM 21. No backend service, new database or dependency-injection framework was introduced.

## 1. Safety and Git

The starting folder was not a Git repository. A complete private ZIP (2,095 entries) was created before source edits, then a source checkpoint and the requested branch were created:

- Original: `original-checkpoint` / `92d2f5d`.
- Main refactor: `refactor/sis4` / `81be0d2`.
- Final compatibility/runtime corrections: `e8d0ceb`. See `git log --oneline` for the documentation checkpoint too.
- Backup location/checksum and safe recovery instructions: [SIS4_BACKUP.md](SIS4_BACKUP.md).

No prior user changes were discarded, no history was rewritten, and nothing was pushed remotely.

## 2. Exact deleted files

[All 23 exact paths](SIS4_DELETED_FILES.md) are listed separately. They comprise approved email verification/password reset, scanner/camera, notifications, secondary map detail screens/models, API/DTO/multipart/JWT infrastructure, unused Google auth scaffold, and the password-reset-only test. No protected feature screen was deleted.

## 3. Important modifications and exact paths

The [complete file-by-file report](SIS4_CHANGED_FILES.md) explains every changed/added source path relative to the project root. The [initial plan](SIS4_REFACTOR_PLAN.md) inventories all 80 original Kotlin source files, their declarations and project dependencies.

Key changes:

- `core/data/repository/LocalEcoRepository.kt`: one private shared ledger/history/favourite source; 600 ms loading, explicit Result failures, controlled error demo, local persistence and existing rating calculations.
- `core/data/local/EcoLocalCache.kt`: small Preferences DataStore snapshot using platform JSON, replacing the production Moshi/API cache.
- `core/session/SessionManager.kt`: logged-in state, ID, name and email only. DemoAuthRepository reuses the existing validated login/registration flow without tokens or real provider claims.
- `feature/map/presentation/MapViewModel.kt`: immutable UiState, private MutableStateFlow/public asStateFlow, viewModelScope, search/category/favourite/retry events, SavedStateHandle and lifecycle logging.
- `feature/map/presentation/MapScreen.kt`: recycling-only OSMDroid, preserved marker/card/filter artwork, lifecycle-aware Route and stateless Screen, list/favourites/four states/five previews. Original **2830** lines → **822** lines. It remains a real interactive map, not an image.
- `feature/map/presentation/RecyclingPointDetailsViewModel.kt`: existing pointId/SavedStateHandle mechanism retained; exact local lookup, error for invalid IDs, constructor usable by Navigation's saved-state factory.
- `feature/submit/presentation/SubmitWasteViewModel.kt` and screen: type/quantity/kg/g/pcs/bag/comment/reward/success retained; camera/scanner state removed; submission updates every progress observer.
- Existing Home, Profile, History, Tree, Challenges, Achievements and Leaderboard ViewModels observe the same local source. Unit tests exercise one submission across all of them.
- Faculty ranking/total calculations were moved out of `FacultyLeaderboardContent` into `LeaderboardViewModel`/`FacultyLeaderboardSummary`; podium, user/faculty rows and existing visuals remain.
- Runtime verification exposed stale max-level/profile/name labels. Home now caps next level at 10; Profile computes score/milestone from points; leaderboard displays the actual local name; the Tree Keeper threshold uses the existing 100 points/level constant.
- `MainActivity.kt`: all six lifecycle logs, local startup restore and process-scoped preference/session observers. Edge-to-edge, localization, dark mode, theme and navigation remain.
- `app/build.gradle.kts`, manifest and preferences: remove Retrofit/Moshi/OkHttp, Google location/maps and CameraX; retain OSM INTERNET; add lifecycle Compose and java.time desugaring for minSdk 24. All compilation targets remain 21. Memory defaults were reduced after a real host-memory failure.

## 4. Preserved features

| Feature | Result |
|---|---|
| Splash, Login, Registration | Kept; local validated demo; registration opens Home directly |
| Home | Existing dashboard/cards/quick actions retained; shared balance |
| Recycling map/list/details | OSMDroid retained; 12 labelled demo points, search, category LazyRow, LazyColumn, favourites, pointId details |
| Waste Submission | Kept, functional locally; reward and confirmation; no camera |
| Virtual Tree | Existing levels 0–10, illustrations, animations and preview controls retained |
| Leaderboard | Both files retained; podium, ranks, faculties, current position and visual components |
| Challenges | Both files retained; deterministic local progress |
| Achievements | Both files retained; live local progress, scanner achievement replaced |
| Profile and History | Kept; shared local identity/history/points |
| Settings | Theme, units and language kept; removed only obsolete controls |
| Support | Kept as an explicit local demonstration; no real service or message delivery |
| Localization/theme/adaptive | Existing EN/RU/KK, dark theme and adaptive helpers kept |

The exact UI was not claimed to be pixel-identical on every device. Protected screen layouts and artwork were reused; focused visual/accessibility adjustments and removal of obsolete controls are visible in the Git diff.

## 5. SIS4 rubric assessment

This is an evidence assessment, not a promise of a teacher grade.

| Requirement | Rubric points | Assessment | Evidence |
|---|---:|---|---|
| Architecture planning | 10 | PASS | Initial class/dependency inventory, backup, requested branch, actual MVVM mapping |
| Repository | 15 | PASS | Local shared source, delay/Result/error mode, private mutation, persistent history/favourites |
| ViewModel and state | 25 | PASS | Immutable MapUiState, private mutable/read-only flow, scope/events/copy, saved state, meaningful tests |
| Stateless screens | 20 | PASS | MapRoute collects lifecycle-aware; MapScreen takes state/lambdas; five fake-state previews compile |
| Lifecycle proof | 10 | PASS | Real Activity recreation logs and before/landscape/after assertions; init/onCleared implemented |
| README, AI log, Git | 20 | PASS | Accurate README, actual AI usage with explicitly suggested third prompt, defense guide, local commits and recovery |

SIS3 audit and the 27 requested quality checks are in [SIS4_VERIFICATION.md](SIS4_VERIFICATION.md). Global 48dp/device-size/accessibility coverage is **PARTIAL / NOT VERIFIED exhaustively**; several important custom targets were corrected to 48dp. Previews exist/compile, but Android Studio preview rendering is **NOT VERIFIED**.

## 6. Bonus opportunities

- **SavedStateHandle details: PASS** — String pointId is declared in Navigation, existing ViewModel mechanism retained; exact/invalid ID tests pass and Campus Paper Hub was opened from its list row.
- **ViewModel tests: PASS** — search/category, favourites/saved state, error/retry, details and shared progress are tested.
- **Optional use cases: present** — retained Login/Register/SocialLogin/Logout and wallet/impact/streak/achievement use cases; no unnecessary new framework.

## 7. Actual verification and limitations

**Final result: BUILD SUCCESSFUL in 4m 3s; 23 tests, 0 failures, 0 errors.** The final Gradle output and JUnit summary are attached under `docs/evidence/`. Debug APK: `app/build/outputs/apk/debug/app-debug.apk`; checksum/size are recorded in `docs/evidence/final-apk.txt`. The real emulator run demonstrated registration, Home, 11 visible distinct list items (12 in fixture/unit test), search/category/favourite, correct details, Error/Retry/Empty, two actual rotations, +175 from 5 kg Plastic, shared 1020 balance, Level 10 tree, rank #6, faculty 8065, history/achievements/challenges, dark mode, EN/RU/KK settings and persistence across process restart.

OSM native map/markers were observed, but **online map tile loading was NOT VERIFIED**; the emulator showed the native grid background. Android API 24–25 execution, release signing/build, physical-device tests, external map/dial/share apps, every navigation permutation and every preview/device size were **NOT VERIFIED**. These limits are not represented as passing runtime tests. The debug app works locally without an EcoDala backend; OSM tiles still require internet.

## 8. Defense files

Study [SIS4_DEFENSE_GUIDE.md](../SIS4_DEFENSE_GUIDE.md) and [design/SIS4_MVVM.md](../design/SIS4_MVVM.md), then demonstrate:

1. `feature/map/presentation/MapViewModel.kt` — UiState/events/StateFlow and saved state.
2. `feature/map/presentation/MapScreen.kt` — Route versus stateless Screen, lists and previews.
3. `core/data/repository/LocalEcoRepository.kt` — shared model data, delay and explicit errors.
4. `feature/submit/presentation/SubmitWasteViewModel.kt` — one submission and shared update.
5. `feature/map/presentation/RecyclingPointDetailsViewModel.kt` and `core/navigation/EcoDalaNavGraph.kt` — pointId bonus.
6. `MainActivity.kt` plus `docs/evidence/lifecycle-logcat.txt` — real lifecycle proof.
7. `app/src/test/java/com/ecodala/feature/SharedProgressViewModelsTest.kt` and MapViewModelTest — real automated checks.

Kotlin paths above are relative to `app/src/main/java/com/ecodala/` unless fully prefixed. The defense guide has the exact project paths, likely questions and three small live-coding practice tasks.

## Final APK recheck

After the final build, the same APK was installed again. Actual smoke assertions passed for Home 1020/no Level 11, Profile score 100/capped milestone/actual identity, and the corrected leaderboard name. The map viewport clipping correction was visually confirmed. See [final-apk-smoke-check.txt](evidence/final-apk-smoke-check.txt) and [recycling-map-final.png](evidence/recycling-map-final.png). Online tile appearance remains NOT VERIFIED in the captured frame; OSM logs show Mapnik/cache activity. The test emulator and task Gradle daemons were stopped.
