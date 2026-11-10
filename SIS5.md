# SIS5: Retrofit forecast

Open Home → Weather forecast. The screen requests seven forecast days for Kyzylorda.
API documentation: https://open-meteo.com/en/docs
Base URL: https://api.open-meteo.com/

## How the files work

| File | Responsibility |
| --- | --- |
| data/remote/WeatherApiClient.kt | Builds one Retrofit client with JSON conversion and a 20-second request timeout. |
| data/remote/WeatherApi.kt | Declares the suspend GET request and DTOs matching the JSON response. Coordinates and Celsius units are fixed; GPS is not used. |
| data/repository/WeatherRepositoryImpl.kt | Calls the API, validates matching arrays, and maps each day to WeatherDay. Exceptions propagate to the ViewModel. |
| domain/model/WeatherDay.kt | Plain Kotlin model used by the UI. |
| domain/repository/WeatherRepository.kt | Plain Kotlin contract: suspend fun getItems(): List<WeatherDay>. No Android or Retrofit dependencies. |
| presentation/weather/WeatherViewModel.kt | Starts Loading, launches the request in viewModelScope, then publishes Success or Error. Cancellation is rethrown; duplicate concurrent retries are ignored. |
| presentation/weather/WeatherScreen.kt | Collects state with lifecycle awareness and uses when to show a spinner, list, or error with Retry. Creates the ViewModel through a factory. |
| core/navigation/EcoDalaNavGraph.kt | Registers the weather destination; HomeScreen provides its entry button. |

Retrofit's suspend request runs asynchronously; it does not block the main thread.
No cached or dummy forecast replaces network failures. INTERNET permission already exists.
The existing recycling and leaderboard features still use their existing local repositories.

## Verification

1. With internet enabled, open Home → Weather forecast and confirm seven dated rows.
2. Disable Wi-Fi AND mobile data (or use airplane mode).
3. Press Retry in the forecast toolbar. Loading should become Error within 20 seconds, without a crash.
4. Restore connectivity and press Retry. The list should return.
5. Back returns to Home.

Automated tests cover DTO mapping, malformed responses, and Loading → Success → offline Error → recovered Success.
A fake repository tests state transitions deterministically; the device steps above verify real connectivity.

Run: gradlew.bat :app:assembleDebug :app:testDebugUnitTest
