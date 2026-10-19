# AI usage — this refactor

## Tools actually used

OpenAI Codex in the desktop app read the existing source, generated focused Kotlin edits, executed PowerShell/Python file transformations, ran Gradle on the installed JDK 21, inspected Android emulator output with ADB and wrote documentation. No claim is made about AI tools or human contributions before this conversation. No GitHub push was made.

## Three useful prompts

1. **Actual earlier user request (Russian):** fix the missing Java 17 toolchain error and use JVM 21 throughout. Result: Java/Kotlin targets and daemon criteria use 21; the original build succeeded before SIS4 work.
2. **Actual current user request:** “ECODALA ANDROID — SAFE SIS4 REFACTOR BASED ON THE EXISTING CODEBASE”, supplied as an attached text file. It asked to preserve protected screens, remove advanced features, reuse local data/ViewModels and verify SIS4.
3. **Suggested follow-up prompt, not claimed as a previously executed user prompt:** “Trace onFavouriteClick from MapScreen through MapRoute, MapViewModel and LocalEcoRepository back to StateFlow; explain which state survives rotation and test error/retry.”

## Generated code checked and corrected

- LocalEcoRepository and DataStore snapshot reuse the existing ledger, WasteSubmission and rating calculation. Tests verify one submission changes history, wallet and current-user leaderboard together, rejects invalid quantities, and converts grams.
- MapUiState originally matched a category only against a point's primary accepted type. The final predicate uses `selectedWasteType in point.acceptedWasteTypes`; tests include Campus Paper Hub accepting Plastic as a secondary type.
- The first compile of the refactor exposed unresolved `strings.close` and an icon import removed while the language selector still used it. The label was localized using the existing language helper and the required icon import restored; subsequent builds passed.
- The details ViewModel originally substituted the first recycling point for unknown IDs. That fallback was removed, an explicit error added, and tests verify exact point-8 selection and an invalid ID.
- During review, the existing submission unit selector offered `bag`; the repository was corrected to preserve it and the rating calculator's existing 2 kg/bag conversion. Grams were added without removing existing units.

## Real business logic moved out of a Composable

Before this refactor, `FacultyLeaderboardContent` in `feature/leaderboard/presentation/LeaderboardScreen.kt` sorted the faculty data, selected the leader and summed points/members during composition. The refactor moves aggregate preparation to `FacultyLeaderboardSummary.from` and ranking/update work to `LeaderboardViewModel`. The existing screen receives the summary through its Route and retains the hero, ranking rows and animation code. This is an observed change, not an invented historical example.

## Limits

AI assistance does not establish a teacher grade. Build/test and emulator claims are limited to recorded outputs in `docs/SIS4_VERIFICATION.md`. Previews exist in source and compile; an Android Studio preview rendering is a separate check. No human review or contributions are invented here.

## Runtime-driven corrections

Actual emulator output exposed a Home Level 11 label after reaching the maximum level, a fixed Profile score of 75/Level 5 milestone, and a hardcoded current-user name. These were corrected using UiState values and the actual local identity, without replacing the visual components. The shared-progress test now also checks capped milestones and the score at the maximum level. A map viewport clip was added after observing a native-view drawing overlap.

The final post-fix APK was then installed and these corrected labels were confirmed by actual accessibility XML assertions. Its map viewport clipping was visually inspected in a new screenshot.
