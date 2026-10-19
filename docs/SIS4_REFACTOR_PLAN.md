# SIS4 refactor plan — actual initial code inventory

## Safety and scope

`git status` originally failed: this folder had no Git history. A complete ZIP of every original file (including local.properties and build caches) was created in `.sis4-backup/original-project.zip` before edits. Keep that private: it contains local configuration. Git baseline: `92d2f5d`, branch `original-checkpoint`; implementation branch: `refactor/sis4`. No user changes were discarded. Existing JDK/JVM 21 fixes are part of the checkpoint.

All Kotlin sources were read to collect declarations and project dependencies below. Existing UI assets, themes, adaptive helpers and protected screens stay in place.

## Reversible steps

1. Replace remote repository with shared LocalEcoRepository; reuse data, validation, ratings, economy use cases and DataStore. Keep one ledger/history used by every feature.
2. Remove explicitly approved screens and routes, camera/DTO/JWT infrastructure and unused SDKs. Keep OSMDroid HTTPS tiles and external route intents.
3. Simplify MapViewModel/MapScreen to recycling only, preserve card/filter/marker appearance; add list mode, favourites, four states and previews. Filtering stays outside Composables.
4. Preserve SavedStateHandle pointId, add lifecycle logging, add meaningful state/repository tests. Keep all retained ViewModels and screen layouts.
5. Build/test on JDK 21; use an emulator if available. Mark unobserved runtime checks NOT VERIFIED. Document architecture and defense.

## Initial declarations and exact project dependencies

### `app/src/main/java/com/ecodala/core/auth/GoogleAuthScaffold.kt`

Declarations: SocialUser, AuthProvider, GoogleAuthClient, DemoGoogleAuthClient.

Project dependencies:


Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/dummy/DummyEcoData.kt`

Declarations: DummyEcoData.

Project dependencies:
- `com.ecodala.core.domain.model.Achievement`
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.BiotoiletReview`
- `com.ecodala.core.domain.model.BiotoiletStatus`
- `com.ecodala.core.domain.model.BiotoiletType`
- `com.ecodala.core.domain.model.Challenge`
- `com.ecodala.core.domain.model.ChallengeStatus`
- `com.ecodala.core.domain.model.ChallengeType`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoReportComment`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoReportStatus`
- `com.ecodala.core.domain.model.LeaderboardEntry`
- `com.ecodala.core.domain.model.PointsEvent`
- `com.ecodala.core.domain.model.PointsSource`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.model.TreeGrowthEvent`
- `com.ecodala.core.domain.model.VirtualTree`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.model.WaterStation`
- `com.ecodala.core.domain.model.WaterStationReview`
- `com.ecodala.core.domain.model.WaterStationStatus`
- `com.ecodala.core.domain.model.WaterStationType`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/data/local/EcoLocalCache.kt`

Declarations: EcoLocalCache, CachedMapData.

Project dependencies:
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.model.WaterStation`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/data/remote/dto/DtoMappers.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.domain.model.Achievement`
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.BiotoiletReview`
- `com.ecodala.core.domain.model.BiotoiletStatus`
- `com.ecodala.core.domain.model.BiotoiletType`
- `com.ecodala.core.domain.model.Challenge`
- `com.ecodala.core.domain.model.ChallengeStatus`
- `com.ecodala.core.domain.model.ChallengeType`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoReportComment`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoReportStatus`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.LeaderboardEntry`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.model.WaterStation`
- `com.ecodala.core.domain.model.WaterStationReview`
- `com.ecodala.core.domain.model.WaterStationStatus`
- `com.ecodala.core.domain.model.WaterStationType`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/remote/dto/EcoDalaDtos.kt`

Declarations: PaginatedResponseDto, LoginRequestDto, RegisterRequestDto, JwtTokenDto, RefreshTokenRequestDto, UserDto, WasteCategoryDto, RecyclingPointDto, BiotoiletDto, WaterStationDto, EcoReportDto, EcoReportCommentDto, WasteSubmissionRequestDto, WasteSubmissionDto, ChallengeDto, AchievementDto, AchievementRuleDto, LeaderboardUserDto, ScannerResultDto.

Project dependencies:


Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/remote/EcoDalaApi.kt`

Declarations: EcoDalaApi.

Project dependencies:
- `com.ecodala.core.data.remote.dto.AchievementDto`
- `com.ecodala.core.data.remote.dto.BiotoiletDto`
- `com.ecodala.core.data.remote.dto.ChallengeDto`
- `com.ecodala.core.data.remote.dto.EcoReportDto`
- `com.ecodala.core.data.remote.dto.JwtTokenDto`
- `com.ecodala.core.data.remote.dto.LeaderboardUserDto`
- `com.ecodala.core.data.remote.dto.LoginRequestDto`
- `com.ecodala.core.data.remote.dto.PaginatedResponseDto`
- `com.ecodala.core.data.remote.dto.RecyclingPointDto`
- `com.ecodala.core.data.remote.dto.RefreshTokenRequestDto`
- `com.ecodala.core.data.remote.dto.RegisterRequestDto`
- `com.ecodala.core.data.remote.dto.ScannerResultDto`
- `com.ecodala.core.data.remote.dto.UserDto`
- `com.ecodala.core.data.remote.dto.WasteCategoryDto`
- `com.ecodala.core.data.remote.dto.WasteSubmissionDto`
- `com.ecodala.core.data.remote.dto.WasteSubmissionRequestDto`
- `com.ecodala.core.data.remote.dto.WaterStationDto`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/remote/MultipartRequestFactory.kt`

Declarations: MultipartRequestFactory.

Project dependencies:


Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/remote/NetworkModule.kt`

Declarations: NetworkModule.

Project dependencies:
- `com.ecodala.BuildConfig`
- `com.ecodala.core.data.remote.dto.RefreshTokenRequestDto`
- `com.ecodala.core.logging.AppLogger`
- `com.ecodala.core.session.SessionManager`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/repository/ApiAuthRepository.kt`

Declarations: ApiAuthRepository.

Project dependencies:
- `com.ecodala.core.data.remote.EcoDalaApi`
- `com.ecodala.core.data.remote.NetworkModule`
- `com.ecodala.core.data.remote.dto.LoginRequestDto`
- `com.ecodala.core.data.remote.dto.RegisterRequestDto`
- `com.ecodala.core.data.remote.dto.toDomain`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.SocialAuthProvider`
- `com.ecodala.core.domain.repository.AuthRepository`
- `com.ecodala.core.session.SessionManager`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/repository/ApiEcoRepository.kt`

Declarations: ApiEcoRepository.

Project dependencies:
- `com.ecodala.core.data.remote.EcoDalaApi`
- `com.ecodala.core.data.local.EcoLocalCache`
- `com.ecodala.core.data.remote.MultipartRequestFactory`
- `com.ecodala.core.data.remote.NetworkModule`
- `com.ecodala.core.data.remote.dto.WasteSubmissionRequestDto`
- `com.ecodala.core.data.remote.dto.toApiValue`
- `com.ecodala.core.data.remote.dto.toDomain`
- `com.ecodala.core.domain.model.Achievement`
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.Challenge`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.LeaderboardEntry`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.model.WaterStation`
- `com.ecodala.core.session.SessionManager`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/data/repository/DemoAuthRepository.kt`

Declarations: DemoAuthRepository.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.SocialAuthProvider`
- `com.ecodala.core.domain.repository.AuthRepository`
- `com.ecodala.core.session.SessionManager`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/data/repository/DemoPointsEconomyRepository.kt`

Declarations: DemoPointsEconomyRepository.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.PointsEvent`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.repository.PointsEconomyRepository`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/data/repository/FakeWasteScannerRepository.kt`

Declarations: FakeWasteScannerRepository.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.repository.WasteScannerRepository`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/domain/model/AuthModels.kt`

Declarations: SocialAuthProvider.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/model/EcoModels.kt`

Declarations: EcoUser, RecyclingPoint, Biotoilet, BiotoiletStatus, BiotoiletType, BiotoiletReview, BiotoiletIssueType, WaterStation, WaterStationType, WaterStationStatus, WaterStationReview, WaterStationIssueType, EcoReport, EcoReportStatus, EcoReportSeverity, EcoReportComment, EcoReportIssueType, WasteType, WasteSubmission, VirtualTree, TreeGrowthEvent, Challenge, ChallengeStatus, ChallengeType, LeaderboardEntry, Achievement, ScannerResult.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/model/EcoRating.kt`

Declarations: EcoRating, EcoRatingCalculator.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/model/EngagementModels.kt`

Declarations: StreakSummary.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/model/PointsEconomy.kt`

Declarations: PointsEvent, PointsSource, PointsWallet, MonthlyImpact.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/repository/AuthRepository.kt`

Declarations: AuthRepository.

Project dependencies:
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.SocialAuthProvider`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/repository/PointsEconomyRepository.kt`

Declarations: PointsEconomyRepository.

Project dependencies:
- `com.ecodala.core.domain.model.PointsEvent`
- `com.ecodala.core.domain.model.WasteSubmission`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/repository/WasteScannerRepository.kt`

Declarations: WasteScannerRepository.

Project dependencies:
- `com.ecodala.core.domain.model.ScannerResult`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/domain/usecase/AuthUseCases.kt`

Declarations: LoginUseCase, RegisterUseCase, SocialLoginUseCase, LogoutUseCase.

Project dependencies:
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.model.SocialAuthProvider`
- `com.ecodala.core.domain.repository.AuthRepository`
- `com.ecodala.core.validation.FormValidator`
- `com.ecodala.core.validation.ValidationResult`
- `com.ecodala.core.validation.firstErrorOrNull`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/usecase/EngagementUseCases.kt`

Declarations: GetAchievementProgressUseCase, GetStreakSummaryUseCase.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.Achievement`
- `com.ecodala.core.domain.model.ChallengeStatus`
- `com.ecodala.core.domain.model.StreakSummary`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.repository.PointsEconomyRepository`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/usecase/PointsEconomyUseCases.kt`

Declarations: GetPointsWalletUseCase, GetMonthlyImpactUseCase.

Project dependencies:
- `com.ecodala.core.domain.model.MonthlyImpact`
- `com.ecodala.core.domain.model.EcoRatingCalculator`
- `com.ecodala.core.domain.model.PointsSource`
- `com.ecodala.core.domain.model.PointsWallet`
- `com.ecodala.core.domain.repository.PointsEconomyRepository`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/domain/usecase/ScanWastePhotoUseCase.kt`

Declarations: ScanWastePhotoUseCase.

Project dependencies:
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.repository.WasteScannerRepository`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/localization/EcoStrings.kt`

Declarations: EcoStrings.

Project dependencies:
- `com.ecodala.core.domain.model.BiotoiletIssueType`
- `com.ecodala.core.domain.model.BiotoiletStatus`
- `com.ecodala.core.domain.model.BiotoiletType`
- `com.ecodala.core.domain.model.ChallengeStatus`
- `com.ecodala.core.domain.model.ChallengeType`
- `com.ecodala.core.domain.model.EcoReportIssueType`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoReportStatus`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.model.WaterStationIssueType`
- `com.ecodala.core.domain.model.WaterStationStatus`
- `com.ecodala.core.domain.model.WaterStationType`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/logging/AppLogger.kt`

Declarations: AppLogger.

Project dependencies:
- `com.ecodala.BuildConfig`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/navigation/BottomNavDestination.kt`

Declarations: BottomNavDestination.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/navigation/EcoDalaNavGraph.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.session.SessionManager`
- `com.ecodala.core.ui.components.EcoDalaBottomBar`
- `com.ecodala.feature.achievements.presentation.AchievementsRoute`
- `com.ecodala.feature.auth.presentation.ForgotPasswordRoute`
- `com.ecodala.feature.auth.presentation.EmailVerificationRoute`
- `com.ecodala.feature.auth.presentation.LoginRoute`
- `com.ecodala.feature.auth.presentation.RegisterRoute`
- `com.ecodala.feature.challenges.presentation.ChallengesRoute`
- `com.ecodala.feature.home.presentation.HomeRoute`
- `com.ecodala.feature.leaderboard.presentation.LeaderboardRoute`
- `com.ecodala.feature.map.presentation.BiotoiletDetailsRoute`
- `com.ecodala.feature.map.presentation.EcoReportDetailsRoute`
- `com.ecodala.feature.map.presentation.MapRoute`
- `com.ecodala.feature.map.presentation.RecyclingPointDetailsRoute`
- `com.ecodala.feature.map.presentation.WaterStationDetailsRoute`
- `com.ecodala.feature.profile.presentation.NotificationsRoute`
- `com.ecodala.feature.profile.presentation.ProfileRoute`
- `com.ecodala.feature.profile.presentation.RecyclingHistoryRoute`
- `com.ecodala.feature.profile.presentation.SettingsRoute`
- `com.ecodala.feature.profile.presentation.SupportRoute`
- `com.ecodala.feature.scanner.presentation.AiWasteScannerRoute`
- `com.ecodala.feature.splash.presentation.SplashScreen`
- `com.ecodala.feature.submit.presentation.SubmitWasteRoute`
- `com.ecodala.feature.tree.presentation.VirtualTreeRoute`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/navigation/EcoDalaRoute.kt`

Declarations: EcoDalaRoute.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/session/SessionManager.kt`

Declarations: UserSession, SessionManager.

Project dependencies:
- `com.ecodala.core.domain.model.EcoUser`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/settings/AppSettingsStore.kt`

Declarations: AppSettings, AppSettingsStore.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/state/AsyncUiState.kt`

Declarations: AsyncUiState.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/ui/adaptive/EcoAdaptive.kt`

Declarations: Composable/functions.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/ui/components/EcoCameraCapturePanel.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/core/ui/components/EcoDalaBottomBar.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.navigation.EcoDalaRoute`
- `com.ecodala.core.navigation.bottomNavDestinations`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/ui/components/EcoStateViews.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/ui/theme/Color.kt`

Declarations: Composable/functions.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/ui/theme/Theme.kt`

Declarations: Composable/functions.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/core/validation/FormValidator.kt`

Declarations: FormValidator, ValidationResult.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/achievements/presentation/AchievementsScreen.kt`

Declarations: AchievementVisual.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.Achievement`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/achievements/presentation/AchievementsViewModel.kt`

Declarations: AchievementsViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.data.repository.DemoPointsEconomyRepository`
- `com.ecodala.core.domain.model.Achievement`
- `com.ecodala.core.domain.usecase.GetAchievementProgressUseCase`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/auth/presentation/AuthViewModel.kt`

Declarations: AuthUiState, AuthViewModel.

Project dependencies:
- `com.ecodala.core.data.repository.ApiAuthRepository`
- `com.ecodala.core.domain.model.SocialAuthProvider`
- `com.ecodala.core.domain.repository.AuthRepository`
- `com.ecodala.core.domain.usecase.LoginUseCase`
- `com.ecodala.core.domain.usecase.RegisterUseCase`
- `com.ecodala.core.domain.usecase.SocialLoginUseCase`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/auth/presentation/EmailVerificationScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/auth/presentation/ForgotPasswordScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.adaptive.horizontalScreenPadding`
- `com.ecodala.core.ui.adaptive.isCompactHeight`
- `com.ecodala.core.ui.components.EcoFormError`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/auth/presentation/ForgotPasswordViewModel.kt`

Declarations: ForgotPasswordUiState, ForgotPasswordViewModel.

Project dependencies:


Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/auth/presentation/LoginScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.domain.model.SocialAuthProvider`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.adaptive.horizontalScreenPadding`
- `com.ecodala.core.ui.adaptive.isCompactHeight`
- `com.ecodala.core.ui.adaptive.isExtraCompactHeight`
- `com.ecodala.core.ui.components.EcoFormError`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`
- `com.ecodala.R`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/auth/presentation/RegisterScreen.kt`

Declarations: PasswordRule.

Project dependencies:
- `com.ecodala.core.domain.model.SocialAuthProvider`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.adaptive.horizontalScreenPadding`
- `com.ecodala.core.ui.adaptive.isCompactHeight`
- `com.ecodala.core.ui.adaptive.isExtraCompactHeight`
- `com.ecodala.core.ui.components.EcoFormError`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`
- `com.ecodala.R`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/challenges/presentation/ChallengesScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.Challenge`
- `com.ecodala.core.domain.model.ChallengeStatus`
- `com.ecodala.core.domain.model.ChallengeType`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/challenges/presentation/ChallengesViewModel.kt`

Declarations: ChallengesViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.Challenge`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/home/presentation/HomeScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.settings.AppSettingsStore`
- `com.ecodala.core.ui.adaptive.horizontalScreenPadding`
- `com.ecodala.core.ui.adaptive.isCompactHeight`
- `com.ecodala.core.ui.adaptive.isExtraCompactHeight`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`
- `com.ecodala.core.ui.theme.EcoGreenLight`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/home/presentation/HomeViewModel.kt`

Declarations: HomeUiState, HomeAchievementUi, HomeViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.data.repository.DemoPointsEconomyRepository`
- `com.ecodala.core.domain.model.EcoRatingCalculator`
- `com.ecodala.core.domain.usecase.GetAchievementProgressUseCase`
- `com.ecodala.core.domain.usecase.GetMonthlyImpactUseCase`
- `com.ecodala.core.domain.usecase.GetPointsWalletUseCase`
- `com.ecodala.core.domain.usecase.GetStreakSummaryUseCase`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/leaderboard/presentation/LeaderboardScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.LeaderboardEntry`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.adaptive.horizontalScreenPadding`
- `com.ecodala.core.ui.adaptive.isCompactHeight`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/leaderboard/presentation/LeaderboardViewModel.kt`

Declarations: FacultyLeaderboardEntry, LeaderboardViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.LeaderboardEntry`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/map/presentation/BiotoiletDetailsScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.BiotoiletIssueType`
- `com.ecodala.core.domain.model.BiotoiletStatus`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.localization.accessible`
- `com.ecodala.core.localization.biotoiletIssueName`
- `com.ecodala.core.localization.biotoiletStatusName`
- `com.ecodala.core.localization.biotoiletTypeName`
- `com.ecodala.core.localization.biotoilets`
- `com.ecodala.core.localization.buildRouteWithMaps`
- `com.ecodala.core.localization.cleanliness`
- `com.ecodala.core.localization.communityVerified`
- `com.ecodala.core.localization.distance`
- `com.ecodala.core.localization.facilities`
- `com.ecodala.core.localization.familyFriendly`
- `com.ecodala.core.localization.hours`
- `com.ecodala.core.localization.no`
- `com.ecodala.core.localization.rateAndReport`
- `com.ecodala.core.localization.reportSavedWithPoints`
- `com.ecodala.core.localization.reviews`
- `com.ecodala.core.localization.submitReport`
- `com.ecodala.core.localization.type`
- `com.ecodala.core.localization.uploadUpdatedPhoto`
- `com.ecodala.core.localization.yes`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/map/presentation/EcoReportDetailsScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoReportIssueType`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoReportStatus`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.localization.comments`
- `com.ecodala.core.localization.communityActions`
- `com.ecodala.core.localization.distance`
- `com.ecodala.core.localization.ecoReportIssueName`
- `com.ecodala.core.localization.ecoReportSeverityName`
- `com.ecodala.core.localization.ecoReportStatusName`
- `com.ecodala.core.localization.ecoReports`
- `com.ecodala.core.localization.gps`
- `com.ecodala.core.localization.reportedBy`
- `com.ecodala.core.localization.sendUpdateToAdministration`
- `com.ecodala.core.localization.severity`
- `com.ecodala.core.localization.submitReportUpdate`
- `com.ecodala.core.localization.updateSavedWithPoints`
- `com.ecodala.core.localization.uploadUpdatedPhoto`
- `com.ecodala.core.localization.verifications`
- `com.ecodala.core.localization.verifiedWithPoints`
- `com.ecodala.core.localization.verifyCondition`
- `com.ecodala.core.ui.components.EcoCameraCapturePanel`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/map/presentation/MapScreen.kt`

Declarations: FilterChipColors, RouteMode, RouteEstimate, StreetRouteResult.

Project dependencies:
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.BiotoiletStatus`
- `com.ecodala.core.domain.model.BiotoiletType`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoReportStatus`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.model.WaterStation`
- `com.ecodala.core.domain.model.WaterStationStatus`
- `com.ecodala.core.domain.model.WaterStationType`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.localization.access`
- `com.ecodala.core.localization.accessible`
- `com.ecodala.core.localization.biotoiletStatusName`
- `com.ecodala.core.localization.biotoiletTypeName`
- `com.ecodala.core.localization.biotoilets`
- `com.ecodala.core.localization.distance`
- `com.ecodala.core.localization.ecoReportSeverityName`
- `com.ecodala.core.localization.ecoReportStatusName`
- `com.ecodala.core.localization.ecoReports`
- `com.ecodala.core.localization.family`
- `com.ecodala.core.localization.free`
- `com.ecodala.core.localization.freeOnly`
- `com.ecodala.core.localization.highSeverity`
- `com.ecodala.core.localization.highlyRated`
- `com.ecodala.core.localization.hours`
- `com.ecodala.core.localization.limited`
- `com.ecodala.core.localization.nearest`
- `com.ecodala.core.localization.openDetails`
- `com.ecodala.core.localization.openNow`
- `com.ecodala.core.localization.openReport`
- `com.ecodala.core.localization.paid`
- `com.ecodala.core.localization.refill`
- `com.ecodala.core.localization.standard`
- `com.ecodala.core.localization.type`
- `com.ecodala.core.localization.waterStationStatusName`
- `com.ecodala.core.localization.waterStationTypeName`
- `com.ecodala.core.localization.waterStations`
- `com.ecodala.core.ui.components.EcoErrorState`
- `com.ecodala.core.ui.components.EcoInlineLoading`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/map/presentation/MapViewModel.kt`

Declarations: MapUiState, MapLayer, BiotoiletFilter, WaterStationFilter, EcoReportFilter, MapViewModel, BiotoiletFilterOption, WaterStationFilterOption, EcoReportFilterOption.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.Biotoilet`
- `com.ecodala.core.domain.model.BiotoiletStatus`
- `com.ecodala.core.domain.model.BiotoiletType`
- `com.ecodala.core.domain.model.EcoReport`
- `com.ecodala.core.domain.model.EcoReportSeverity`
- `com.ecodala.core.domain.model.EcoReportStatus`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.domain.model.WaterStation`
- `com.ecodala.core.domain.model.WaterStationStatus`
- `com.ecodala.core.domain.model.WaterStationType`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/map/presentation/RecyclingPointDetailsScreen.kt`

Declarations: WasteVisualSpec.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/map/presentation/RecyclingPointDetailsViewModel.kt`

Declarations: RecyclingPointDetailsUiState, RecyclingPointDetailsViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.RecyclingPoint`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/map/presentation/WaterStationDetailsScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.WaterStation`
- `com.ecodala.core.domain.model.WaterStationIssueType`
- `com.ecodala.core.domain.model.WaterStationStatus`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.localization.buildRouteWithMaps`
- `com.ecodala.core.localization.distance`
- `com.ecodala.core.localization.gps`
- `com.ecodala.core.localization.rating`
- `com.ecodala.core.localization.reviews`
- `com.ecodala.core.localization.savedWithPoints`
- `com.ecodala.core.localization.submitReport`
- `com.ecodala.core.localization.type`
- `com.ecodala.core.localization.uploadUpdatedPhoto`
- `com.ecodala.core.localization.verifyStationInformation`
- `com.ecodala.core.localization.waterQualityAndReports`
- `com.ecodala.core.localization.waterStationIssueName`
- `com.ecodala.core.localization.waterStationStatusName`
- `com.ecodala.core.localization.waterStationTypeName`
- `com.ecodala.core.localization.waterStations`
- `com.ecodala.core.localization.workingHours`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/profile/presentation/NotificationsScreen.kt`

Declarations: NotificationVisual.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/profile/presentation/NotificationsViewModel.kt`

Declarations: EcoNotification, NotificationType, NotificationsViewModel.

Project dependencies:


Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/profile/presentation/ProfileScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.components.EcoErrorState`
- `com.ecodala.core.ui.components.EcoInlineLoading`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/profile/presentation/ProfileViewModel.kt`

Declarations: ProfileUiState, ProfileViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiAuthRepository`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.data.repository.DemoPointsEconomyRepository`
- `com.ecodala.core.domain.model.EcoRatingCalculator`
- `com.ecodala.core.domain.model.EcoUser`
- `com.ecodala.core.domain.usecase.GetMonthlyImpactUseCase`
- `com.ecodala.core.domain.usecase.GetPointsWalletUseCase`
- `com.ecodala.core.domain.usecase.GetStreakSummaryUseCase`
- `com.ecodala.core.domain.usecase.LogoutUseCase`
- `com.ecodala.core.session.SessionManager`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/profile/presentation/RecyclingHistoryScreen.kt`

Declarations: HistoryVisual.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.components.EcoErrorState`
- `com.ecodala.core.ui.components.EcoInlineLoading`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/profile/presentation/RecyclingHistoryViewModel.kt`

Declarations: RecyclingHistoryUiState, RecyclingHistoryViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.WasteSubmission`
- `com.ecodala.core.domain.model.WasteType`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/profile/presentation/SettingsScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.settings.AppSettings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/profile/presentation/SettingsViewModel.kt`

Declarations: SettingsViewModel.

Project dependencies:
- `com.ecodala.core.settings.AppSettings`
- `com.ecodala.core.settings.AppSettingsStore`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/profile/presentation/SupportScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/profile/presentation/SupportViewModel.kt`

Declarations: SupportUiState, SupportContact, SupportFaq, SupportViewModel.

Project dependencies:


Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/scanner/presentation/AiWasteScannerScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.components.EcoCameraCapturePanel`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/scanner/presentation/AiWasteScannerViewModel.kt`

Declarations: AiWasteScannerUiState, AiWasteScannerViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`

Planned: Remove after retained references migrate.

### `app/src/main/java/com/ecodala/feature/splash/presentation/SplashScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.adaptive.isCompactHeight`
- `com.ecodala.core.ui.theme.EcoDalaTheme`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/splash/presentation/SplashViewModel.kt`

Declarations: SplashViewModel.

Project dependencies:
- `com.ecodala.core.navigation.EcoDalaRoute`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/submit/presentation/SubmitWasteScreen.kt`

Declarations: Composable/functions.

Project dependencies:
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.model.WasteType`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.localization.acceptedByEcodalaPoints`
- `com.ecodala.core.localization.aiScannerUnavailable`
- `com.ecodala.core.localization.approvedPoints`
- `com.ecodala.core.localization.cameraCaptureFailed`
- `com.ecodala.core.localization.clear`
- `com.ecodala.core.localization.confidencePercent`
- `com.ecodala.core.localization.nearestAcceptedPoint`
- `com.ecodala.core.localization.recyclable`
- `com.ecodala.core.localization.submissionFailed`
- `com.ecodala.core.localization.submitCaptureAndScan`
- `com.ecodala.core.localization.submitFrameHint`
- `com.ecodala.core.localization.submitPhotoAttached`
- `com.ecodala.core.localization.submitPhotoScanSubtitle`
- `com.ecodala.core.localization.submitPhotoUploadSubtitle`
- `com.ecodala.core.localization.submitScanningWaste`
- `com.ecodala.core.localization.submittedForReview`
- `com.ecodala.core.localization.submittingWaste`
- `com.ecodala.core.ui.components.EcoFormError`
- `com.ecodala.core.ui.components.EcoCameraCapturePanel`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/submit/presentation/SubmitWasteViewModel.kt`

Declarations: SubmitWasteUiState, AiScanUiState, SubmitWasteMessage, SubmitWasteViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.EcoRatingCalculator`
- `com.ecodala.core.domain.model.RecyclingPoint`
- `com.ecodala.core.domain.model.ScannerResult`
- `com.ecodala.core.domain.model.WasteType`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/feature/tree/presentation/VirtualTreeScreen.kt`

Declarations: TreeLeaf, DayPeriod, TreeSeason, TreeScene.

Project dependencies:
- `com.ecodala.BuildConfig`
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.domain.model.TreeGrowthEvent`
- `com.ecodala.core.domain.model.VirtualTree`
- `com.ecodala.core.localization.LocalEcoStrings`
- `com.ecodala.core.ui.theme.EcoDalaTheme`
- `com.ecodala.core.ui.theme.EcoGreen`

Planned: Preserve visuals; remove only obsolete callbacks.

### `app/src/main/java/com/ecodala/feature/tree/presentation/VirtualTreeViewModel.kt`

Declarations: VirtualTreeViewModel.

Project dependencies:
- `com.ecodala.core.data.dummy.DummyEcoData`
- `com.ecodala.core.data.repository.ApiEcoRepository`
- `com.ecodala.core.domain.model.EcoRatingCalculator`
- `com.ecodala.core.domain.model.TreeGrowthEvent`
- `com.ecodala.core.domain.model.VirtualTree`

Planned: Retain and adapt local data/state dependencies.

### `app/src/main/java/com/ecodala/MainActivity.kt`

Declarations: MainActivity.

Project dependencies:
- `com.ecodala.core.data.local.EcoLocalCache`
- `com.ecodala.core.logging.AppLogger`
- `com.ecodala.core.localization.EcoLocalization`
- `com.ecodala.core.navigation.EcoDalaApp`
- `com.ecodala.core.session.SessionManager`
- `com.ecodala.core.settings.AppSettingsStore`
- `com.ecodala.core.ui.theme.EcoDalaTheme`

Planned: Retain and adapt local data/state dependencies.
