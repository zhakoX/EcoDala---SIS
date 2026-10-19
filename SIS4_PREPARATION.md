# Подготовка EcoDala к SIS4

Проверка: 19.10.2026. Основной пример для защиты: карта/список пунктов переработки.

## Что готово

| Требование | Где показать |
| --- | --- |
| Диаграмма, поля состояния, события, путь клика | `design/SIS4_MVVM_Architecture.md` |
| Repository, задержка 600 мс, имитация ошибки | `LocalEcoRepository.recyclingPoints(simulateError)` |
| Неизменяемый UiState, private MutableStateFlow, public StateFlow, copy | `MapViewModel.kt` |
| Поиск, фильтр, избранное, повторная загрузка | Методы `onSearchQueryChange`, `onCategorySelected`, `onFavouriteClick`, `onRetry` |
| Stateless Screen и lifecycle-aware Route | `MapScreen.kt`: `MapScreen`, `MapRoute` |
| Четыре состояния Preview | `MapLoadingPreview`, `MapContentPreview`, `MapEmptyPreview`, `MapErrorPreview` |
| Логи жизненного цикла | `MainActivity.kt`, `MapViewModel.kt` |
| Бонус: domain use cases | `PointsEconomyUseCases.kt`, `EngagementUseCases.kt` |
| Бонус: детали с SavedStateHandle | `RecyclingPointDetailsViewModel.kt`: `pointId` |
| Бонус: тест ViewModel | `MapViewModelTest.kt`: 3 теста |

## Результат проверки

Команда `./gradlew.bat :app:assembleDebug :app:testDebugUnitTest` завершилась `BUILD SUCCESSFUL`.
17 тестов, 0 ошибок, 0 падений. Отчёт: `app/build/reports/tests/testDebugUnitTest/index.html`.
APK: `app/build/outputs/apk/debug/app-debug.apk`.
Тест восстановления через SavedStateHandle не заменяет проверку реального поворота устройства.

## Проверка на эмуляторе перед сдачей

1. Запустить приложение из Android Studio. Открыть карту, переключить в список.
2. Ввести `campus`, выбрать Plastic, отметить пункт избранным.
3. Повернуть эмулятор кнопкой Rotate. Проверить сохранение запроса, фильтра, списка и избранного.
4. В Logcat использовать фильтр `tag:SIS4.MainActivity | tag:SIS4.MapViewModel`. Сохранить весь интервал: от открытия карты до завершения поворота.
5. Скопировать реальные логи в раздел README о повороте: старый Activity вызывает onPause/onStop/onDestroy с changingConfigurations=true, новый вызывает onCreate/onStart/onResume. Сопоставить instance и отсутствие нового init/onCleared MapViewModel во время поворота.
6. Нажать значок жука: увидеть ошибку. Retry повторяет ошибку, пока включён режим симуляции. Нажать «Отключить ошибку»: список загружается снова.
7. Ввести несуществующий запрос: увидеть Empty. Очистить запрос и фильтр: увидеть Content. Loading можно показать через Preview.
8. Открыть четыре Preview в Android Studio и проверить отображение.

Устройств при подготовке не было (`adb devices` вернул пустой список). Реальные логи поворота и проверка Preview остаются обязательными перед сдачей.

## Сценарий защиты на 5–7 минут

- 1 минута: карта/список, поиск, категория, избранное, поворот, ошибка и восстановление.
- 1 минута: пройти `MapScreen` → callback в `MapRoute` → `MapViewModel.onFavouriteClick` → `LocalEcoRepository.setFavourite` → `data` → `copy` → `StateFlow` → `collectAsStateWithLifecycle` → обновлённая иконка.
- 2–3 минуты: самостоятельно выполнить небольшое изменение через слои, например сортировку. Порядок сортировки добавить в UiState, действие в ViewModel, результат вычислять вне Screen, кнопку связать callback через Route. На защите по заданию AI использовать нельзя.
- 2 минуты: объяснить вопросы ниже своими словами.

## Вопросы

**Зачем private _uiState?** Только ViewModel изменяет состояние. Screen получает доступ только для чтения и отправляет события.

**Почему Activity пересоздаётся, а ViewModel сохраняется?** Конфигурация меняется, Android создаёт новый Activity. ViewModel хранится у своего ViewModelStoreOwner и переживает обычную смену конфигурации. SavedStateHandle дополнительно сохраняет запрос, категорию и режим списка. Избранное приходит из Repository.

**Почему Preview не нужен ViewModel?** Screen принимает готовый UiState и функции. Preview передаёт фиктивное состояние без загрузки данных.

**Что разрешено хранить в remember?** Временное отображение вроде анимации или раскрытого меню. Значимые данные, запрос, фильтр и избранное принадлежат ViewModel/Repository.

## Что нельзя подтвердить задним числом

Требование «план закоммичен до кода» проверяется настоящей Git-историей. Текущий документ описывает реализацию; переименование или новый коммит не докажут наличие раннего плана. В AI_USAGE нужно лично подтвердить реальный пример ошибки AI и свои ручные действия. После проверки подготовить осмысленный коммит текущих изменений и отправить ссылку на GitHub в Teams. В этой сессии коммиты и публикация не выполнялись.
