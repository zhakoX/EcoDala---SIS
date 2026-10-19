# EcoDala — защита SIS4 за 5–7 минут

## 1. Демонстрация приложения — примерно 1 минута

Войти локально (`demo@ecodala.kz`, `DemoPass123!`), открыть Home. Показать Map → List: 12 демо-точек, LazyColumn. Выбрать категорию, ввести `campus`, отметить сердце. Открыть точку и вернуться назад. Открыть Submit, выбрать тип и количество, подтвердить сдачу: увидеть полученные баллы и нажать Continue для дерева. Открыть Home/Profile/History/Leaderboard и показать один общий баланс. Все точки учебные, сервер не требуется; только подложка карты загружается из интернета.

## 2. Как объяснить MVVM

Model — данные и правила: `LocalEcoRepository`, `DummyEcoData`, `EcoRatingCalculator`, DataStore. ViewModel — `MapViewModel`: получает события, запускает корутины, публикует `MapUiState`. View — `MapScreen`: получает состояние и callbacks, рисует существующий UI. `MapRoute` связывает ViewModel и экран. Это рефакторинг существующего проекта, остальные экраны и ViewModel сохранены.

## 3. StateFlow

`_uiState` — private MutableStateFlow. UI видит только `StateFlow` через `asStateFlow()`. `update { it.copy(...) }` создаёт новое неизменяемое состояние. Route использует `collectAsStateWithLifecycle()`, поэтому сбор состояния учитывает lifecycle. `viewModelScope` отменяется при окончательном удалении ViewModel, а не при каждом пересоздании Activity.

## 4. Repository

`LocalEcoRepository.shared` — один источник истины для истории, ledger и избранного. `delay(600)` показывает загрузку. Флаг simulateError возвращает явный Result.failure, без скрытых данных из API. Сдача сначала проверяет ввод и сохраняет локальное состояние, затем публикует новую историю и ledger вместе. ViewModel не обращается к UI и не содержит серверной авторизации.

## 5. Поворот и Logcat

1. Map → List. Ввести `campus`, выбрать Paper, отметить Campus Paper Hub.
2. Закрыть клавиатуру, повернуть эмулятор в landscape.
3. Сравнить текст, выбранную категорию и сердце.
4. В Logcat выбрать теги `SIS4.MainActivity` и `SIS4.MapViewModel`.
5. У Activity будут onPause/onStop/onDestroy и новый onCreate/onStart/onResume. В onDestroy указан changingConfigurations=true. Сравнить instance ID: Activity меняется, MapViewModel остаётся.
6. Вернуть portrait. `onCleared` ожидается при окончательном удалении ViewModel из навигации, а не из-за обычного поворота.

Поиск, категория и режим списка дополнительно записываются в SavedStateHandle. Избранное и сдачи — в репозитории/DataStore. Проверка пересоздания ViewModel unit-тестом полезна, но сама по себе не доказывает поворот Activity. Реальные результаты см. `docs/SIS4_VERIFICATION.md`.

## 6. Loading / Content / Empty / Error

Loading виден при загрузке. Content — 12 точек в List. Empty — ввести `does-not-exist`. Error — нажать значок жука. Retry повторяет текущий режим и снова показывает ошибку, пока флаг включён. Нажать «Turn off error mode» для успешной загрузки. При ошибке нет молчаливой подмены данными API или DummyEcoData.

## 7. Trace One Click

Сердце → callback `MapScreen` → `MapRoute` → `MapViewModel.onFavouriteClick` → `viewModelScope` → `LocalEcoRepository.setFavourite` → DataStore → новый LocalEcoData → collector ViewModel → `copy(favouriteIds=...)` → read-only StateFlow → lifecycle-aware Route → перерисовка сердца. Схема и шаги: `design/SIS4_MVVM.md`.

## 8. Какие файлы изучить

Пути от корня проекта:

1. `app/src/main/java/com/ecodala/feature/map/presentation/MapViewModel.kt` — UiState, события, SavedStateHandle, lifecycle.
2. `app/src/main/java/com/ecodala/feature/map/presentation/MapScreen.kt` — MapRoute отдельно от stateless MapScreen, LazyColumn/LazyRow, пять previews.
3. `app/src/main/java/com/ecodala/core/data/repository/LocalEcoRepository.kt` — общий ledger/history, delay, Result, Mutex.
4. `app/src/main/java/com/ecodala/core/data/local/EcoLocalCache.kt` — простой snapshot в DataStore.
5. `app/src/main/java/com/ecodala/feature/submit/presentation/SubmitWasteViewModel.kt` — проверка и отправка локальной сдачи.
6. `app/src/main/java/com/ecodala/feature/map/presentation/RecyclingPointDetailsViewModel.kt` и `core/navigation/EcoDalaNavGraph.kt` — pointId и бонус SavedStateHandle.
7. `app/src/main/java/com/ecodala/feature/leaderboard/presentation/LeaderboardViewModel.kt` — пример переноса расчётов из Composable.
8. `app/src/main/java/com/ecodala/MainActivity.kt` — шесть lifecycle callbacks.
9. `app/src/test/java/com/ecodala/feature/map/presentation/MapViewModelTest.kt` — поиск, избранное, Error/Retry.
10. `README.md`, `AI_USAGE.md`, `docs/SIS4_VERIFICATION.md` — факты о проекте и ограничения.

## 9. Возможные вопросы

- **Почему не remember для поиска?** Это состояние приложения, оно должно переживать пересоздание Activity. Поэтому ViewModel + SavedStateHandle.
- **Где фильтрация?** В derived property MapUiState.filteredPoints, рядом с ViewModel. Screen только отображает готовый результат.
- **Зачем Route?** Он получает ViewModel, собирает StateFlow и передаёт callbacks; stateless Screen удобно preview/test.
- **Почему один репозиторий?** Чтобы Home, дерево, история и рейтинг показывали результат одной сдачи, без независимых hardcoded totals.
- **Что хранится после перезапуска?** Session identity, тема/язык/единица, добавленные сдачи и избранное в DataStore. Это общая демо-учётная запись, не отдельные защищённые аккаунты.
- **Что такое recomposition?** Обновление Compose UI из-за состояния. Она не равна onDestroy/onCreate Activity.
- **Как приходит ID детали?** String-аргумент в nav graph → SavedStateHandle → существующий details ViewModel → local repository.
- **Почему не Room/Hilt?** Для учебного объёма хватает уже используемого DataStore и простых конструкторов.
- **Нужен ли интернет?** Для OSM tiles — да, для локальных учебных данных — нет.
- **Баллы челленджей начисляются автоматически?** Нет: общий баланс вычисляется только из записанных ledger events. Challenge reward labels — учебные milestones; новая сдача начисляет только рассчитанную награду за отходы.

## 10. Три задания для live coding

1. Добавить поиск по openingHours в MapUiState.filteredPoints и unit-тест с конкретной строкой времени.
2. Добавить ещё одну Demo-точку с уникальным ID в DummyEcoData; открыть её из списка и проверить детали.
3. Добавить фильтр «Только избранное» как поле UiState и событие ViewModel, не фильтруя список внутри Composable.

После правок: `.\gradlew.bat :app:assembleDebug :app:testDebugUnitTest`. Не менять protected layouts ради небольшого задания.
