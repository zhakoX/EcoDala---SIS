# Карта проекта EcoDala

Откройте app/src/main/java/com/ecodala. В проекте один модуль app.

| Папка | Что здесь искать |
| --- | --- |
| MainActivity.kt | Запуск приложения, тема, инициализация локального состояния |
| data/remote | Retrofit, API, DTO — формат ответа сервера |
| data/local | Сохранение профиля и истории на устройстве |
| data/dummy | Исходные демонстрационные данные |
| data/repository | Реализации репозиториев, получение и преобразование данных |
| domain/model | Модели и правила расчётов без Android и Compose |
| domain/repository | Контракты доступа к данным |
| domain/usecase | Операции бизнес-логики |
| presentation/<раздел> | Screen, Route, ViewModel и состояние конкретного раздела |
| core/navigation | Маршруты, граф переходов, пункты нижней навигации |
| core/ui | Общие компоненты, тема, адаптивные размеры и визуальные настройки |
| core/localization | Переводы |
| core/settings | Настройки приложения |
| core/logging | Логирование |

## Как найти нужный экран

presentation/home — главная; map — карта и карточка пункта; submit — сдача отходов;
leaderboard — рейтинг; profile — профиль, история, настройки и поддержка;
achievements — достижения; challenges — задания; tree — дерево;
weather — прогноз; splash — стартовый экран.

Внутри раздела:
- XxxScreen.kt: Route связывает ViewModel с экраном, Screen отображает состояние и вызывает callbacks.
- XxxViewModel.kt: состояние экрана, обработка действий и загрузка данных.

Не создавайте дополнительную папку presentation внутри каждого раздела.
Общий компонент размещайте в core/ui/components, а компонент одного экрана оставляйте рядом с экраном.

## Зависимости

presentation → domain ← data

domain не импортирует data, Android, Retrofit или Compose.
Иконки и цвета WasteType находятся в core/ui/model/WasteTypeVisuals.kt.
Число завершённых заданий приходит в use case через PointsEconomyRepository.
Создание конкретных репозиториев пока выполняется в Route/ViewModel; отдельного DI-фреймворка нет.
Большинство экранов используют LocalEcoRepository; только weather получает реальные сетевые данные.

## Пример сетевого запроса

WeatherScreen → WeatherViewModel → WeatherRepository → WeatherRepositoryImpl → WeatherApi.
Ответ DTO преобразуется в WeatherDay. Экран показывает Loading, Success или Error и кнопку Retry.
Подробности учебного задания: SIS5.md.

## Тесты и проверка

app/src/test/java/com/ecodala повторяет структуру production-кода.
Общие средства тестирования находятся в core/testing.

Команда: gradlew.bat :app:assembleDebug :app:testDebugUnitTest

При добавлении функции: модель/контракт в domain → реализация в data → экран и ViewModel в presentation → маршрут в core/navigation.
