# SIS4 verification — 19 October 2026

## Environment and real commands

Windows 11; installed JetBrains JDK 21.0.11, Gradle 8.13, AGP 8.13.2, Android platform 35. The isolated `Pixel_7` emulator reported Android API **35**. It was launched headless/read-only with temporary writes, so the original AVD state was not saved over. No EcoDala backend was running or used.

```powershell
$env:JAVA_HOME = 'C:\Users\zhako\.jdks\jbr-21.0.11'
.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest --console=plain
```

Actual final build output is in [build-and-unit-tests.txt](evidence/build-and-unit-tests.txt); the [test summary](evidence/unit-test-summary.json) comes from JUnit XML. There are **23 tests** across 10 suites. Final result: **BUILD SUCCESSFUL in 4m 3s; 23 tests, 0 failures, 0 errors**. Source checkpoint: `e8d0ceb`.

A simultaneous Gradle/emulator run exhausted host memory. The emulator was stopped, Gradle changed to a 1536 MB heap/two workers/in-process Kotlin, and later builds/tests succeeded. Cold boot also briefly produced an Android **System UI** responsiveness warning; it was dismissed with Wait. It was not recorded as an EcoDala crash. An automation assertion initially expected `selected=true`; Android accessibility exports these Compose selection semantics as `checked=true`, so the harness was corrected. The final rotation assertions passed. One scroll sample skipped a point title: 11 distinct visible items were recorded, while the source/unit test verifies 12; this is not falsely reported as 12 visually counted items.

## Required quality checks

PASS below states its evidence type. It does not imply exhaustive device testing.

| # | Check | Status / evidence |
|---:|---|---|
| 1 | Splash works | PASS for navigation to Login/Home; existing animation retained. Full animation/pixel comparison NOT VERIFIED. |
| 2 | Login locally | PASS: demo login opened Home on the emulator; retained validation tests pass. |
| 3 | Registration locally | PASS: SIS4Student/student@ecodala.kz opened Home directly; Profile showed that identity. |
| 4 | Home | PASS: balance 845 → 1020, monthly +175, 13 kg and rank #6 observed. Final APK smoke check confirmed no Level 11 after reaching 1020 points. |
| 5 | Recycling map | PARTIAL: real OSMDroid MapView and coloured markers observed; online background tiles were not visually confirmed in the captured frame (native grid was visible). Online tile loading NOT VERIFIED; the attached OSM log only confirms Mapnik/cache activity. |
| 6 | 10+ LazyColumn items | PASS: 12 unique source items/unit test; 11 distinct rows observed across actual scroll snapshots. |
| 7 | Search | PASS: campus query and does-not-exist empty query tested on emulator and in ViewModel tests. |
| 8 | Category filter | PASS: Paper selection retained and filtered Campus Paper Hub; secondary accepted type tested in unit test. |
| 9 | Favourites | PASS: toggled; retained through rotation and process restart; checked=true in saved XML. |
| 10 | Details argument | PASS: Campus Paper Hub opened from its row; exact point-8 and invalid-ID tests pass. |
| 11 | Submission | PASS: 5 kg Plastic → Approved +175 pts, then Continue → Tree. |
| 12 | Shared EcoPoints | PASS: Home/Profile/current-user leaderboard 1020; cross-ViewModel unit test covers all progress features. |
| 13 | Tree | PASS: actual Level 10 and 1020 EcoPoints observed; original illustrations and 0–10 preview controls retained. |
| 14 | Challenges | PASS: visible; active submission challenge changed 3/5 → 4/5. |
| 15 | Leaderboard | PASS: top 3, user rows, current rank #6/1020 and faculty totals observed; current faculty 8065. |
| 16 | Achievements | PASS: screen visible; Plastic Saver unlocked after submission. |
| 17 | Profile | PASS: SIS4Student/1020 shown; edit callbacks retained. Fixed score/milestone labels now derive from points. Final APK smoke check confirmed computed score 100 and capped milestone. Profile editing itself NOT VERIFIED manually. |
| 18 | History | PASS: sixth submission +175 and 13 kg observed; waste-only points total 360. |
| 19 | Settings | PASS: theme/language controls and retained unit options visible. Changing the unit preference itself NOT VERIFIED manually. |
| 20 | Dark mode | PASS: real dark settings screenshot inspected. |
| 21 | No dead navigation | PASS for source route/reference audit and exercised flows; every possible navigation sequence/external Intent NOT VERIFIED. |
| 22 | Loading/Content/Empty/Error | PASS: loading flag/delay/unit tests and compiled UI; Content/Empty/Error/Retry observed. Loading screenshot timing NOT VERIFIED. |
| 23 | Rotation | PASS: portrait → landscape → portrait; search/category/favourite preserved; real Activity recreation logs. |
| 24 | Lifecycle logs | PASS: six Activity callbacks and VM init in actual Logcat; onCleared implemented but its runtime callback was NOT VERIFIED. |
| 25 | Previews | PASS in source/compilation: Loading, Content, Empty, Error and Dark; Android Studio preview rendering NOT VERIFIED. |
| 26 | Debug build | PASS: BUILD SUCCESSFUL in 4m 3s; JDK 21, no backend needed. |
| 27 | Unit tests | PASS: 23 tests, 0 failures, 0 errors; final JUnit-derived summary attached. |

## Lifecycle proof

[Raw Logcat](evidence/lifecycle-logcat.txt) was captured from the emulator without inventing or editing the sequence. Selected actual lines:

```text
10-19 07:04:04.746  4885  4885 D SIS4.MapViewModel: init instance=248868151
10-19 07:05:10.244  4885  4885 D SIS4.MainActivity: onPause instance=190115588
10-19 07:05:10.247  4885  4885 D SIS4.MainActivity: onStop instance=190115588
10-19 07:05:10.316  4885  4885 D SIS4.MainActivity: onDestroy instance=190115588 changingConfigurations=true
10-19 07:05:10.360  4885  4885 D SIS4.MainActivity: onCreate instance=165374331 restored=true
10-19 07:05:10.401  4885  4885 D SIS4.MainActivity: onStart instance=165374331
10-19 07:05:10.420  4885  4885 D SIS4.MainActivity: onResume instance=165374331
```

A second rotation created Activity instance 217184166. There was no new MapViewModel init between these rotations. The [rotation check output](evidence/rotation-check.txt) and three XML snapshots record campus, Paper and the checked favourite before/after. Emulator timestamps use its own UTC clock; the report date uses the user's local date. This proves Activity recreation, not merely recomposition.

## SIS3 source and runtime audit

| Requirement | Result |
|---|---|
| At least 3 screens | PASS: 15 retained navigation destinations, including Splash/auth/details. |
| LazyColumn with 10+ items | PASS: Map list, 12 fixtures / 11 observed rows. |
| LazyRow | PASS: recycling categories use LazyRow. |
| Scaffold/TopAppBar | PASS: app Scaffold; map Scaffold and Material TopAppBar. |
| Back navigation/Nav Compose/details argument | PASS: source and exercised details/history/tree flows. |
| Empty state | PASS: actual empty search. |
| Ellipsis | PASS: map names, leaderboard name and bottom-bar labels. |
| contentDescription | PASS for main actionable icon controls; decorative icons deliberately use null. Exhaustive accessibility audit NOT VERIFIED. |
| Custom theme/dark mode | PASS: retained theme/adaptive packages and actual dark settings screenshot. |
| Consistent spacing/48dp targets | PARTIAL: existing layouts preserved; map chips/hearts, stepper, bottom items, language controls and support chips improved. All existing targets/device sizes NOT VERIFIED exhaustively. |
| Three reusable Composables | PASS: EcoDalaBottomBar, EcoErrorState/EcoInlineLoading, FilterChip/RecyclingPointPreviewCard/InfoTile and existing shared components. |
| Screen and dark preview | PASS in source/compilation; preview renderer NOT VERIFIED. |
| Data classes | PASS: retained EcoModels plus UiState and local snapshot data classes. |

## Explicit limits

- API 24–25 device execution NOT VERIFIED. Core library desugaring was enabled because the retained code and local persistence use java.time; [Android documentation](https://developer.android.com/studio/write/java8-support#library-desugaring) describes this compatibility mechanism.
- Release signing/build, physical-device testing, all small/tablet sizes, full pixel comparison, external navigation/dial/share handlers, and support message submission were NOT VERIFIED manually.
- Captures in [runtime-observations.md](evidence/runtime-observations.md) include honest pre-fix observations that prompted final label corrections; they are not falsely described as final expected labels.
- No real provider authentication, secure multi-user account system, customer-support service or server sync exists. This is explicitly a local educational demo.

## Final APK post-fix smoke check

Source checkpoint `e8d0ceb`, same APK checksum as `final-apk.txt`: installed and run after the final build. The [actual smoke output](evidence/final-apk-smoke-check.txt) records three passing assertions: Home 1020/no Level 11, Profile actual identity/score 100/capped milestone, and leaderboard actual local name. The [final map screenshot](evidence/recycling-map-final.png) was inspected: its native viewport no longer covers search/category/demo labels. Online tiles were not visually confirmed in that frame; the [OSM log](evidence/osm-runtime-log.txt) records Mapnik and tile-cache growth, not a captured city background.

The temporary emulator was stopped after testing. Gradle daemons launched for these checks were stopped too.
