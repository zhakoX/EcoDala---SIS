package com.ecodala.core.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.ecodala.core.domain.model.ChallengeStatus
import com.ecodala.core.domain.model.ChallengeType
import com.ecodala.core.domain.model.WasteType

data class EcoStrings(
    val languageTag: String,
    val home: String,
    val map: String,
    val submit: String,
    val leads: String,
    val profile: String,
    val login: String,
    val splashTagline: String,
    val splashExperience: String,
    val registerNow: String,
    val createAccount: String,
    val welcomeBack: String,
    val signInSubtitle: String,
    val emailAddress: String,
    val password: String,
    val continueWithGoogle: String,
    val or: String,
    val noAccountPrefix: String,
    val fullName: String,
    val joinCommunity: String,
    val confirmPassword: String,
    val fairlyStrongPassword: String,
    val termsPrefix: String,
    val terms: String,
    val termsAnd: String,
    val privacyPolicy: String,
    val orSignUpWith: String,
    val alreadyHaveAccount: String,
    val invalidEmail: String,
    val signingIn: String,
    val creatingAccount: String,
    val rememberMe: String,
    val continueAction: String,
    val cancel: String,
    val save: String,
    val profileUpdated: String,
    val editProfile: String,
    val editAccountInfo: String,
    val termsPrivacy: String,
    val acceptAndContinue: String,
    val hello: (String) -> String,
    val quickActions: String,
    val currentEcoRating: String,
    val points: (Int) -> String,
    val pointsLabel: String,
    val level: (Int) -> String,
    val globalRank: (Int) -> String,
    val virtualTreeTitle: String,
    val progressToLevel: (Int, Int) -> String,
    val recentAchievements: String,
    val searchLocation: String,
    val openUntil: String,
    val distanceAway: (Double, Int) -> String,
    val acceptedItems: String,
    val reward: String,
    val acceptedWasteTypes: String,
    val open: String,
    val buildRoute: String,
    val call: String,
    val share: String,
    val sustainableFact: String,
    val submitWaste: String,
    val wasteType: String,
    val quantity: String,
    val commentOptional: String,
    val addNote: String,
    val submitWithPoints: (Int) -> String,
    val myEcoTree: String,
    val progressToNextLevel: (Int) -> String,
    val xpStatus: (Int, Int) -> String,
    val xpToGo: (Int) -> String,
    val nextGoal: String,
    val collectXpGoal: (Int, Int) -> String,
    val growthHistory: String,
    val challenges: String,
    val daily: String,
    val weekly: String,
    val special: String,
    val dailyChallenges: String,
    val weeklyChallenges: String,
    val left: (Int) -> String,
    val active: String,
    val completed: String,
    val locked: String,
    val ecoSpecialTitle: String,
    val ecoSpecialSubtitle: String,
    val leaderboard: String,
    val students: String,
    val faculties: String,
    val topFaculty: String,
    val thisWeek: (Int) -> String,
    val total: String,
    val members: String,
    val topContributor: (Int, String) -> String,
    val memberSince: (String) -> String,
    val kgWaste: String,
    val trees: String,
    val sustainabilityScore: String,
    val topContributorText: String,
    val nextMilestone: String,
    val myAchievements: String,
    val recyclingHistory: String,
    val settings: String,
    val support: String,
    val logout: String,
    val all: String,
    val achievements: String,
    val ecoProgress: String,
    val achievementsUnlocked: (Int, Int) -> String,
    val completePercent: (Int) -> String,
    val recentSubmissions: String,
    val yourImpact: String,
    val impactSubtitle: String,
    val supportHeroTitle: String,
    val supportHeroSubtitle: String,
    val contactUs: String,
    val sendRequest: String,
    val describeIssue: String,
    val requestSaved: String,
    val faq: String,
    val settingsDisplayUnits: String,
    val settingsDarkMode: String,
    val settingsDarkModeSubtitle: String,
    val settingsLanguage: String,
    val settingsLanguageSubtitle: String,
    val settingsPreferredUnit: String,
    val settingsPreferredUnitSubtitle: String,
    val settingsInfo: String,
    val thisMonthImpact: String,
    val streakSummary: (Int, Int) -> String,
    val recycled: String,
    val submits: String,
    val wasteLabel: String,
    val currentUserLeaderboardName: String,
    val ecoRatingTitle: (Int) -> String,
    val localizedDate: (String) -> String,
    val localizedAchievementTitle: (String) -> String,
    val localizedAchievementDescription: (String) -> String,
    val localizedTreeTitle: (String) -> String,
    val localizedTreeDescription: (String) -> String,
    val supportTopic: (String) -> String,
    val supportContactTitle: (String) -> String,
    val supportContactSubtitle: (String) -> String,
    val supportFaqQuestion: (String) -> String,
    val supportFaqAnswer: (String) -> String,
    val wasteTypeName: (WasteType) -> String,
    val challengeTypeName: (ChallengeType) -> String,
    val challengeStatusName: (ChallengeStatus) -> String
)

val LocalEcoStrings = staticCompositionLocalOf { englishEcoStrings }

@Composable
fun EcoLocalization(
    languageTag: String,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalEcoStrings provides ecoStringsFor(languageTag),
        content = content
    )
}

fun ecoStringsFor(languageTag: String): EcoStrings {
    return when (languageTag) {
        "ru" -> russianEcoStrings
        "kk" -> kazakhEcoStrings
        else -> englishEcoStrings
    }
}

private fun EcoStrings.localized(en: String, ru: String, kk: String): String {
    return when (languageTag) {
        "ru" -> ru
        "kk" -> kk
        else -> en
    }
}

val EcoStrings.rating: String
    get() = localized("Rating", "Рейтинг", "Баға")

val EcoStrings.type: String
    get() = localized("Type", "Тип", "Түрі")

val EcoStrings.no: String
    get() = localized("No", "Нет", "Жоқ")

val EcoStrings.access: String
    get() = localized("Access", "Доступ", "Қолжетімділік")

val EcoStrings.submittingWaste: String
    get() = localized("Submitting...", "Отправка...", "Жіберіліп жатыр...")

val EcoStrings.clear: String
    get() = localized("Clear", "Очистить", "Тазалау")


val EcoStrings.recyclable: String
    get() = localized("Recyclable", "Перерабатывается", "Қайта өңделеді")





val EcoStrings.submissionFailed: String
    get() = localized("Could not save the submission locally. Try again.", "Не удалось сохранить сдачу локально. Повторите.", "Тапсырысты сақтау мүмкін болмады. Қайта көріңіз.")

fun EcoStrings.approvedPoints(points: Int): String =
    localized("Approved +$points pts", "Подтверждено +$points баллов", "Расталды +$points балл")

val EcoStrings.submittedForReview: String
    get() = localized(
        "Submitted for review. Points will be added after approval.",
        "Отправлено на проверку. Баллы добавятся после подтверждения.",
        "Тексеруге жіберілді. Балл расталғаннан кейін қосылады."
    )

private val englishEcoStrings = EcoStrings(
    languageTag = "en",
    home = "Home",
    map = "Map",
    submit = "Submit",
    leads = "Leads",
    profile = "Profile",
    login = "Login",
    splashTagline = "Recycle • Track • Grow",
    splashExperience = "ECO-CONSCIOUS DIGITAL EXPERIENCE",
    registerNow = "Register Now",
    createAccount = "Create Account",
    welcomeBack = "Login",
    signInSubtitle = "Sign in to continue your eco journey",
    emailAddress = "Email address",
    password = "Password",
    continueWithGoogle = "Continue with Google",
    or = "or",
    noAccountPrefix = "Don't have an account? ",
    fullName = "Full Name",
    joinCommunity = "Join the eco community",
    confirmPassword = "Confirm Password",
    fairlyStrongPassword = "Fairly strong password",
    termsPrefix = "I agree to the ",
    terms = "Terms of Service",
    termsAnd = " and ",
    privacyPolicy = "Privacy Policy.",
    orSignUpWith = "OR SIGN UP WITH",
    alreadyHaveAccount = "Already have an account? ",
    invalidEmail = "Enter a valid email address",
    signingIn = "Signing in...",
    creatingAccount = "Creating account...",
    rememberMe = "Remember me",
    continueAction = "Continue",
    cancel = "Cancel",
    save = "Save",
    profileUpdated = "Profile updated",
    editProfile = "Edit profile",
    editAccountInfo = "Edit account info",
    termsPrivacy = "Terms & Privacy",
    acceptAndContinue = "Accept and continue",
    hello = { "Hello, $it!" },
    quickActions = "QUICK ACTIONS",
    currentEcoRating = "Eco rating",
    points = { "$it pts" },
    pointsLabel = "Points",
    level = { "Level $it" },
    globalRank = { "Leaderboard rank #$it" },
    virtualTreeTitle = "Your Virtual Tree",
    progressToLevel = { progress, level -> "$progress% to Level $level" },
    recentAchievements = "Recent Achievements",
    searchLocation = "Search location...",
    openUntil = "Open until 8:00 PM",
    distanceAway = { rating, meters -> "$rating • $meters m away" },
    acceptedItems = "Accepted Items",
    reward = "Reward",
    acceptedWasteTypes = "Accepted Waste Types",
    open = "OPEN",
    buildRoute = "Build Route",
    call = "Call",
    share = "Share",
    sustainableFact = "Sustainable Fact",
    submitWaste = "Submit Waste",
    wasteType = "Waste Type",
    quantity = "Quantity",
    commentOptional = "Comment (optional)",
    addNote = "Add a note...",
    submitWithPoints = { "Submit +$it pts" },
    myEcoTree = "My Eco Tree",
    progressToNextLevel = { "Progress to Level $it" },
    xpStatus = { current, next -> "$current / $next XP" },
    xpToGo = { "$it XP to go" },
    nextGoal = "Next Goal",
    collectXpGoal = { xp, level -> "Collect $xp more XP to reach Level $level" },
    growthHistory = "Growth History",
    challenges = "Challenges",
    daily = "Daily",
    weekly = "Weekly",
    special = "Special",
    dailyChallenges = "Daily Challenges",
    weeklyChallenges = "Weekly Challenges",
    left = { "$it left" },
    active = "Active",
    completed = "Completed",
    locked = "Locked",
    ecoSpecialTitle = "Eco-Special: Park Clean-up",
    ecoSpecialSubtitle = "Join the community this Saturday for double points!",
    leaderboard = "Leaderboard",
    students = "Students",
    faculties = "Faculties",
    topFaculty = "Top Faculty",
    thisWeek = { "+$it% this week" },
    total = "Total",
    members = "Members",
    topContributor = { members, name -> "$members members • Top: $name" },
    memberSince = { "Member since $it" },
    kgWaste = "kg Waste",
    trees = "Trees",
    sustainabilityScore = "Sustainability Score",
    topContributorText = "You're in the top 12%\nof contributors this\nmonth!",
    nextMilestone = "Next Milestone: Level 5",
    myAchievements = "My Achievements",
    recyclingHistory = "Recycling History",
    settings = "Settings",
    support = "Support",
    logout = "Logout",
    all = "All",
    achievements = "Achievements",
    ecoProgress = "Eco Progress",
    achievementsUnlocked = { unlocked, total -> "$unlocked of $total achievements unlocked" },
    completePercent = { "$it% complete" },
    recentSubmissions = "Recent Submissions",
    yourImpact = "Your Impact",
    impactSubtitle = "Track what you recycled and how many points you earned.",
    supportHeroTitle = "EcoDala Kazakhstan Care",
    supportHeroSubtitle = "Local support demonstration. Contacts are illustrative.",
    contactUs = "Contact us",
    sendRequest = "Send a request",
    describeIssue = "Describe your issue...",
    requestSaved = "Demo request acknowledged locally. No message was sent.",
    faq = "FAQ",
    settingsDisplayUnits = "Display & Units",
    settingsDarkMode = "Dark Mode",
    settingsDarkModeSubtitle = "Switch app theme instantly",
    settingsLanguage = "Language",
    settingsLanguageSubtitle = "Choose the app language",
    settingsPreferredUnit = "Preferred Unit",
    settingsPreferredUnitSubtitle = "Used in submissions and history",
    settingsInfo = "Settings, demo submissions and favourites are stored locally with DataStore.",
    thisMonthImpact = "This Month Impact",
    streakSummary = { days, points -> "$days day streak - next +$points pts" },
    recycled = "Recycled",
    submits = "Submits",
    wasteLabel = "Waste",
    currentUserLeaderboardName = "You (EcoWarrior)",
    ecoRatingTitle = {
        when (it) {
            0 -> "Eco Starter"
            1, 2 -> "Seedling Helper"
            3, 4 -> "Recycling Builder"
            5, 6 -> "Green Guardian"
            7, 8 -> "Eco Champion"
            9 -> "Forest Keeper"
            else -> "EcoDala Legend"
        }
    },
    localizedDate = {
        when (it) {
            "Today" -> "Today"
            "Yesterday" -> "Yesterday"
            "2 days ago" -> "2 days ago"
            "3 days ago" -> "3 days ago"
            "Last week" -> "Last week"
            "Now" -> "Now"
            "Goal" -> "Goal"
            "EcoPoints" -> "EcoPoints"
            else -> it
        }
    },
    localizedAchievementTitle = {
        when (it) {
            "First Recycling" -> "First Recycling"
            "100 Points" -> "100 Points"
            "Challenge Joined" -> "Challenge Joined"
            "Tree Keeper" -> "Tree Keeper"
            "Map Explorer" -> "Map Explorer"
            else -> it
        }
    },
    localizedAchievementDescription = {
        when (it) {
            "Completed first recycling task" -> "Completed first recycling task"
            "Reached 100 eco points" -> "Reached 100 eco points"
            "Joined your first challenge" -> "Joined your first challenge"
            "Reach virtual tree level 5" -> "Reach virtual tree level 5"
            "Visit 5 recycling points" -> "Visit 5 recycling points"
            else -> it
        }
    },
    localizedTreeTitle = {
        when {
            it.startsWith("Level ") -> it
            it == "EcoPoints" -> "EcoPoints"
            it == "Goal" -> "Goal"
            it == "Tree" -> "Tree"
            it == "Young Plant" -> "Young Plant"
            it == "Seedling" -> "Seedling"
            else -> it
        }
    },
    localizedTreeDescription = {
        when {
            it == "Every action grows your tree" -> "Every action grows your tree"
            it == "Reached level 4" -> "Reached level 4"
            it == "New leaves unlocked" -> "New leaves unlocked"
            it == "Started eco journey" -> "Started eco journey"
            else -> it
        }
    },
    supportTopic = {
        when (it) {
            "Recycling point issue" -> "Recycling point issue"
            "Waste submission" -> "Waste submission"
            "Eco points" -> "Eco points"
            "Account and profile" -> "Account and profile"
            else -> it
        }
    },
    supportContactTitle = {
        when (it) {
            "EcoDala Hotline" -> "EcoDala Hotline"
            "WhatsApp Support" -> "WhatsApp Support"
            "Email" -> "Email"
            else -> it
        }
    },
    supportContactSubtitle = {
        when (it) {
            "Daily, 09:00 - 20:00" -> "Daily, 09:00 - 20:00"
            "Fast replies in Kazakhstan" -> "Fast replies in Kazakhstan"
            "Response within 24 hours" -> "Response within 24 hours"
            else -> it
        }
    },
    supportFaqQuestion = {
        when (it) {
            "How are EcoPoints counted?" -> "How are EcoPoints counted?"
            "Can I suggest a new recycling point?" -> "Can I suggest a new recycling point?"
            "Which cities are supported?" -> "Which cities are supported?"
            else -> it
        }
    },
    supportFaqAnswer = {
        when (it) {
            "Points are added after a confirmed waste submission or completed challenge." -> "Points are added after a confirmed waste submission or completed challenge."
            "Yes. Send the address, photo and accepted waste types through support." -> "Yes. Send the address, photo and accepted waste types through support."
            "The demo flow is prepared for Kazakhstan, starting with Almaty and Astana." -> "The demo flow is prepared for Kazakhstan, starting with Almaty and Astana."
            else -> it
        }
    },
    wasteTypeName = { it.title },
    challengeTypeName = { it.name },
    challengeStatusName = {
        when (it) {
            ChallengeStatus.Active -> "Active"
            ChallengeStatus.Completed -> "Completed"
            ChallengeStatus.Locked -> "Locked"
        }
    }
)

private val russianEcoStrings = englishEcoStrings.copy(
    languageTag = "ru",
    home = "Главная",
    map = "Карта",
    submit = "Сдать",
    leads = "Рейтинг",
    profile = "Профиль",
    login = "Войти",
    splashTagline = "Сдавайте • Отслеживайте • Развивайтесь",
    splashExperience = "ЭКОЛОГИЧНЫЙ ЦИФРОВОЙ ОПЫТ",
    registerNow = "Регистрация",
    createAccount = "Создать аккаунт",
    welcomeBack = "Вход",
    signInSubtitle = "Войдите, чтобы продолжить эко-путь",
    emailAddress = "Email адрес",
    password = "Пароль",
    continueWithGoogle = "Продолжить с Google",
    or = "или",
    noAccountPrefix = "Нет аккаунта? ",
    fullName = "Полное имя",
    joinCommunity = "Присоединяйтесь к эко-сообществу",
    confirmPassword = "Подтвердите пароль",
    fairlyStrongPassword = "Пароль достаточно надежный",
    termsPrefix = "Я принимаю ",
    terms = "Условия сервиса",
    termsAnd = " и ",
    privacyPolicy = "Политику конфиденциальности.",
    orSignUpWith = "ИЛИ ЗАРЕГИСТРИРУЙТЕСЬ ЧЕРЕЗ",
    alreadyHaveAccount = "Уже есть аккаунт? ",
    invalidEmail = "Введите корректный email",
    signingIn = "Вход...",
    creatingAccount = "Создание аккаунта...",
    rememberMe = "Запомнить меня",
    continueAction = "Продолжить",
    cancel = "Отмена",
    save = "Сохранить",
    profileUpdated = "Профиль обновлен",
    editProfile = "Редактировать профиль",
    editAccountInfo = "Изменить данные аккаунта",
    termsPrivacy = "Условия и конфиденциальность",
    acceptAndContinue = "Принять и продолжить",
    hello = { "Привет, $it!" },
    quickActions = "БЫСТРЫЕ ДЕЙСТВИЯ",
    currentEcoRating = "Эко-рейтинг",
    points = { "$it баллов" },
    pointsLabel = "Баллы",
    level = { "Уровень $it" },
    globalRank = { "Место в рейтинге #$it" },
    virtualTreeTitle = "Ваше виртуальное дерево",
    progressToLevel = { progress, level -> "$progress% до уровня $level" },
    recentAchievements = "Последние достижения",
    searchLocation = "Поиск места...",
    openUntil = "Открыто до 20:00",
    distanceAway = { rating, meters -> "$rating • $meters м от вас" },
    acceptedItems = "Принимают",
    reward = "Награда",
    acceptedWasteTypes = "Принимаемые отходы",
    open = "ОТКРЫТО",
    buildRoute = "Построить маршрут",
    call = "Позвонить",
    share = "Поделиться",
    sustainableFact = "Эко-факт",
    submitWaste = "Сдать отходы",
    wasteType = "Тип отходов",
    quantity = "Количество",
    commentOptional = "Комментарий (необязательно)",
    addNote = "Добавьте заметку...",
    submitWithPoints = { "Сдать +$it баллов" },
    myEcoTree = "Мое эко-дерево",
    progressToNextLevel = { "Прогресс до уровня $it" },
    xpStatus = { current, next -> "$current / $next XP" },
    xpToGo = { "осталось $it XP" },
    nextGoal = "Следующая цель",
    collectXpGoal = { xp, level -> "Соберите еще $xp XP до уровня $level" },
    growthHistory = "История роста",
    challenges = "Челленджи",
    daily = "Ежедневные",
    weekly = "Еженедельные",
    special = "Особые",
    dailyChallenges = "Ежедневные челленджи",
    weeklyChallenges = "Еженедельные челленджи",
    left = { "осталось $it" },
    active = "Активно",
    completed = "Выполнено",
    locked = "Закрыто",
    ecoSpecialTitle = "Эко-спец: уборка парка",
    ecoSpecialSubtitle = "Присоединяйтесь в субботу и получите двойные баллы!",
    leaderboard = "Рейтинг",
    students = "Студенты",
    faculties = "Факультеты",
    topFaculty = "Лучший факультет",
    thisWeek = { "+$it% за неделю" },
    total = "Всего",
    members = "Участники",
    topContributor = { members, name -> "$members участников • Топ: $name" },
    memberSince = { "Участник с $it" },
    kgWaste = "кг отходов",
    trees = "Деревья",
    sustainabilityScore = "Индекс устойчивости",
    topContributorText = "Вы в топ 12%\nучастников этого\nмесяца!",
    nextMilestone = "Следующая цель: уровень 5",
    myAchievements = "Мои достижения",
    recyclingHistory = "История переработки",
    settings = "Настройки",
    support = "Поддержка",
    logout = "Выйти",
    all = "Все",
    achievements = "Достижения",
    ecoProgress = "Эко-прогресс",
    achievementsUnlocked = { unlocked, total -> "$unlocked из $total достижений открыто" },
    completePercent = { "$it% выполнено" },
    recentSubmissions = "Последние сдачи",
    yourImpact = "Ваш вклад",
    impactSubtitle = "Следите, что вы сдали и сколько баллов получили.",
    supportHeroTitle = "Поддержка EcoDala Казахстан",
    supportHeroSubtitle = "Локальная демонстрация поддержки. Контакты условные.",
    contactUs = "Связаться с нами",
    sendRequest = "Отправить запрос",
    describeIssue = "Опишите проблему...",
    requestSaved = "Демо-запрос обработан локально. Сообщение не отправлялось.",
    faq = "FAQ",
    settingsDisplayUnits = "Экран и единицы",
    settingsDarkMode = "Темная тема",
    settingsDarkModeSubtitle = "Мгновенно меняет тему приложения",
    settingsLanguage = "Язык",
    settingsLanguageSubtitle = "Выберите язык приложения",
    settingsPreferredUnit = "Единица измерения",
    settingsPreferredUnitSubtitle = "Для сдачи отходов и истории",
    settingsInfo = "Настройки, демо-сдачи и избранное сохраняются локально через DataStore.",
    thisMonthImpact = "Вклад за месяц",
    streakSummary = { days, points -> "$days дн. серия - далее +$points баллов" },
    recycled = "Переработано",
    submits = "Сдачи",
    wasteLabel = "Отходы",
    currentUserLeaderboardName = "Вы (EcoWarrior)",
    ecoRatingTitle = {
        when (it) {
            0 -> "Эко-старт"
            1, 2 -> "Помощник ростков"
            3, 4 -> "Строитель переработки"
            5, 6 -> "Зеленый защитник"
            7, 8 -> "Эко-чемпион"
            9 -> "Хранитель леса"
            else -> "Легенда EcoDala"
        }
    },
    localizedDate = {
        when (it) {
            "Today" -> "Сегодня"
            "Yesterday" -> "Вчера"
            "2 days ago" -> "2 дня назад"
            "3 days ago" -> "3 дня назад"
            "Last week" -> "На прошлой неделе"
            "Now" -> "Сейчас"
            "Goal" -> "Цель"
            "EcoPoints" -> "Эко-баллы"
            "24 May" -> "24 мая"
            "18 May" -> "18 мая"
            "15 May" -> "15 мая"
            else -> it
        }
    },
    localizedAchievementTitle = {
        when (it) {
            "First Recycling" -> "Первая переработка"
            "100 Points" -> "100 баллов"
            "Challenge Joined" -> "Участие в челлендже"
            "Tree Keeper" -> "Хранитель дерева"
            "Map Explorer" -> "Исследователь карты"
            else -> it
        }
    },
    localizedAchievementDescription = {
        when (it) {
            "Completed first recycling task" -> "Выполнена первая задача по переработке"
            "Reached 100 eco points" -> "Набрано 100 эко-баллов"
            "Joined your first challenge" -> "Вы присоединились к первому челленджу"
            "Reach virtual tree level 5" -> "Достигните 5 уровня виртуального дерева"
            "Visit 5 recycling points" -> "Посетите 5 пунктов переработки"
            else -> it
        }
    },
    localizedTreeTitle = {
        when {
            it.startsWith("Level ") -> it.replace("Level", "Уровень")
            it == "EcoPoints" -> "Эко-баллы"
            it == "Goal" -> "Цель"
            it == "Tree" -> "Дерево"
            it == "Young Plant" -> "Молодое растение"
            it == "Seedling" -> "Росток"
            else -> it
        }
    },
    localizedTreeDescription = {
        when {
            it == "Every action grows your tree" -> "Каждое действие выращивает ваше дерево"
            it == "Reached level 4" -> "Достигнут 4 уровень"
            it == "New leaves unlocked" -> "Открылись новые листья"
            it == "Started eco journey" -> "Начало эко-пути"
            it.startsWith("Reach ") -> it.replace("Reach", "Достигните").replace("EcoPoints", "эко-баллов")
            it.contains("unlocked with") -> it
                .replace("Twig", "Веточка")
                .replace("Seedling", "Росток")
                .replace("Young tree", "Молодое дерево")
                .replace("Growing tree", "Растущее дерево")
                .replace("Dense tree", "Густое дерево")
                .replace("Eco forest", "Эко-лес")
                .replace("unlocked with", "открыто с")
                .replace("EcoPoints", "эко-баллами")
            else -> it
        }
    },
    supportTopic = {
        when (it) {
            "Recycling point issue" -> "Проблема с пунктом переработки"
            "Waste submission" -> "Сдача отходов"
            "Eco points" -> "Эко-баллы"
            "Account and profile" -> "Аккаунт и профиль"
            else -> it
        }
    },
    supportContactTitle = {
        when (it) {
            "EcoDala Hotline" -> "Горячая линия EcoDala"
            "WhatsApp Support" -> "Поддержка WhatsApp"
            "Email" -> "Email"
            else -> it
        }
    },
    supportContactSubtitle = {
        when (it) {
            "Daily, 09:00 - 20:00" -> "Ежедневно, 09:00 - 20:00"
            "Fast replies in Kazakhstan" -> "Быстрые ответы по Казахстану"
            "Response within 24 hours" -> "Ответ в течение 24 часов"
            else -> it
        }
    },
    supportFaqQuestion = {
        when (it) {
            "How are EcoPoints counted?" -> "Как начисляются EcoPoints?"
            "Can I suggest a new recycling point?" -> "Можно предложить новый пункт переработки?"
            "Which cities are supported?" -> "Какие города поддерживаются?"
            else -> it
        }
    },
    supportFaqAnswer = {
        when (it) {
            "Points are added after a confirmed waste submission or completed challenge." -> "Баллы добавляются после подтвержденной сдачи отходов или выполненного челленджа."
            "Yes. Send the address, photo and accepted waste types through support." -> "Да. Отправьте адрес, фото и принимаемые типы отходов через поддержку."
            "The demo flow is prepared for Kazakhstan, starting with Almaty and Astana." -> "Демо-сценарий подготовлен для Казахстана, начиная с Алматы и Астаны."
            else -> it
        }
    },
    wasteTypeName = {
        when (it) {
            WasteType.Plastic -> "Пластик"
            WasteType.Paper -> "Бумага"
            WasteType.Glass -> "Стекло"
            WasteType.Batteries -> "Батарейки"
            WasteType.Electronics -> "Электроника"
            WasteType.Organic -> "Органика"
            WasteType.Metal -> "Металл"
        }
    },
    challengeTypeName = {
        when (it) {
            ChallengeType.Daily -> "Ежедневные"
            ChallengeType.Weekly -> "Еженедельные"
            ChallengeType.Special -> "Особые"
        }
    },
    challengeStatusName = {
        when (it) {
            ChallengeStatus.Active -> "Активно"
            ChallengeStatus.Completed -> "Выполнено"
            ChallengeStatus.Locked -> "Закрыто"
        }
    }
)

private val kazakhEcoStrings = englishEcoStrings.copy(
    languageTag = "kk",
    home = "Басты",
    map = "Карта",
    submit = "Тапсыру",
    leads = "Рейтинг",
    profile = "Профиль",
    login = "Кіру",
    splashTagline = "Тапсыр • Бақыла • Өсір",
    splashExperience = "ЭКО-САНАЛЫ ЦИФРЛЫҚ ТӘЖІРИБЕ",
    registerNow = "Тіркелу",
    createAccount = "Аккаунт ашу",
    welcomeBack = "Кіру",
    signInSubtitle = "Эко сапарыңызды жалғастыру үшін кіріңіз",
    emailAddress = "Email мекенжайы",
    password = "Құпиясөз",
    continueWithGoogle = "Google арқылы жалғастыру",
    or = "немесе",
    noAccountPrefix = "Аккаунтыңыз жоқ па? ",
    fullName = "Толық аты",
    joinCommunity = "Эко қауымдастыққа қосылыңыз",
    confirmPassword = "Құпиясөзді растау",
    fairlyStrongPassword = "Құпиясөз жеткілікті мықты",
    termsPrefix = "Мен ",
    terms = "Қызмет шарттарын",
    termsAnd = " және ",
    privacyPolicy = "Құпиялылық саясатын қабылдаймын.",
    orSignUpWith = "НЕМЕСЕ АРҚЫЛЫ ТІРКЕЛУ",
    alreadyHaveAccount = "Аккаунтыңыз бар ма? ",
    invalidEmail = "Дұрыс email енгізіңіз",
    signingIn = "Кіру...",
    creatingAccount = "Аккаунт ашылуда...",
    rememberMe = "Мені есте сақтау",
    continueAction = "Жалғастыру",
    cancel = "Бас тарту",
    save = "Сақтау",
    profileUpdated = "Профиль жаңартылды",
    editProfile = "Профильді өзгерту",
    editAccountInfo = "Аккаунт деректерін өзгерту",
    termsPrivacy = "Шарттар және құпиялылық",
    acceptAndContinue = "Қабылдау және жалғастыру",
    hello = { "Сәлем, $it!" },
    quickActions = "ЖЫЛДАМ ӘРЕКЕТТЕР",
    currentEcoRating = "Эко рейтинг",
    points = { "$it балл" },
    pointsLabel = "Балл",
    level = { "$it-деңгей" },
    globalRank = { "Рейтингтегі орын #$it" },
    virtualTreeTitle = "Виртуалды ағашыңыз",
    progressToLevel = { progress, level -> "$progress% $level-деңгейге дейін" },
    recentAchievements = "Соңғы жетістіктер",
    searchLocation = "Орын іздеу...",
    openUntil = "20:00 дейін ашық",
    distanceAway = { rating, meters -> "$rating • $meters м қашық" },
    acceptedItems = "Қабылдайды",
    reward = "Сыйақы",
    acceptedWasteTypes = "Қабылданатын қалдықтар",
    open = "АШЫҚ",
    buildRoute = "Маршрут құру",
    call = "Қоңырау",
    share = "Бөлісу",
    sustainableFact = "Эко-факт",
    submitWaste = "Қалдық тапсыру",
    wasteType = "Қалдық түрі",
    quantity = "Саны",
    commentOptional = "Пікір (міндетті емес)",
    addNote = "Ескертпе қосыңыз...",
    submitWithPoints = { "Тапсыру +$it балл" },
    myEcoTree = "Менің эко-ағашым",
    progressToNextLevel = { "$it-деңгейге дейінгі прогресс" },
    xpStatus = { current, next -> "$current / $next XP" },
    xpToGo = { "$it XP қалды" },
    nextGoal = "Келесі мақсат",
    collectXpGoal = { xp, level -> "$level-деңгейге жету үшін тағы $xp XP жинаңыз" },
    growthHistory = "Өсу тарихы",
    challenges = "Челлендждер",
    daily = "Күнделікті",
    weekly = "Апталық",
    special = "Арнайы",
    dailyChallenges = "Күнделікті челлендждер",
    weeklyChallenges = "Апталық челлендждер",
    left = { "$it қалды" },
    active = "Белсенді",
    completed = "Аяқталды",
    locked = "Құлыптаулы",
    ecoSpecialTitle = "Эко-арнайы: парк тазалау",
    ecoSpecialSubtitle = "Сенбі күні қосылып, екі есе балл алыңыз!",
    leaderboard = "Рейтинг",
    students = "Студенттер",
    faculties = "Факультеттер",
    topFaculty = "Үздік факультет",
    thisWeek = { "+$it% осы аптада" },
    total = "Барлығы",
    members = "Қатысушылар",
    topContributor = { members, name -> "$members қатысушы • Үздік: $name" },
    memberSince = { "$it бастап қатысушы" },
    kgWaste = "кг қалдық",
    trees = "Ағаштар",
    sustainabilityScore = "Тұрақтылық ұпайы",
    topContributorText = "Сіз осы айдағы\nқатысушылардың\nтоп 12%-ындасыз!",
    nextMilestone = "Келесі мақсат: 5-деңгей",
    myAchievements = "Менің жетістіктерім",
    recyclingHistory = "Қайта өңдеу тарихы",
    settings = "Баптаулар",
    support = "Қолдау",
    logout = "Шығу",
    all = "Барлығы",
    achievements = "Жетістіктер",
    ecoProgress = "Эко-прогресс",
    achievementsUnlocked = { unlocked, total -> "$total жетістіктің $unlocked ашылды" },
    completePercent = { "$it% аяқталды" },
    recentSubmissions = "Соңғы тапсырулар",
    yourImpact = "Сіздің үлесіңіз",
    impactSubtitle = "Не тапсырғаныңызды және қанша балл алғаныңызды бақылаңыз.",
    supportHeroTitle = "EcoDala Қазақстан қолдауы",
    supportHeroSubtitle = "Қолдаудың жергілікті демосы. Байланыстар шартты.",
    contactUs = "Бізбен байланысу",
    sendRequest = "Сұрау жіберу",
    describeIssue = "Мәселені сипаттаңыз...",
    requestSaved = "Демо сұрау жергілікті өңделді. Хабарлама жіберілген жоқ.",
    faq = "Жиі сұрақтар",
    settingsDisplayUnits = "Экран және өлшемдер",
    settingsDarkMode = "Қараңғы режим",
    settingsDarkModeSubtitle = "Қолданба тақырыбын бірден ауыстырады",
    settingsLanguage = "Тіл",
    settingsLanguageSubtitle = "Қолданба тілін таңдаңыз",
    settingsPreferredUnit = "Өлшем бірлігі",
    settingsPreferredUnitSubtitle = "Қалдық тапсыруда және тарихта қолданылады",
    settingsInfo = "Баптаулар, демо тапсырыстар және таңдаулылар DataStore арқылы сақталады.",
    thisMonthImpact = "Осы айдағы үлес",
    streakSummary = { days, points -> "$days күндік серия - келесі +$points балл" },
    recycled = "Өңделді",
    submits = "Тапсырулар",
    wasteLabel = "Қалдық",
    currentUserLeaderboardName = "Сіз (EcoWarrior)",
    ecoRatingTitle = {
        when (it) {
            0 -> "Эко бастау"
            1, 2 -> "Өскін көмекшісі"
            3, 4 -> "Қайта өңдеу құрастырушысы"
            5, 6 -> "Жасыл қорғаушы"
            7, 8 -> "Эко чемпион"
            9 -> "Орман сақшысы"
            else -> "EcoDala аңызы"
        }
    },
    localizedDate = {
        when (it) {
            "Today" -> "Бүгін"
            "Yesterday" -> "Кеше"
            "2 days ago" -> "2 күн бұрын"
            "3 days ago" -> "3 күн бұрын"
            "Last week" -> "Өткен апта"
            "Now" -> "Қазір"
            "Goal" -> "Мақсат"
            "EcoPoints" -> "Эко-балл"
            "24 May" -> "24 мамыр"
            "18 May" -> "18 мамыр"
            "15 May" -> "15 мамыр"
            else -> it
        }
    },
    localizedAchievementTitle = {
        when (it) {
            "First Recycling" -> "Алғашқы қайта өңдеу"
            "100 Points" -> "100 балл"
            "Challenge Joined" -> "Челленджге қосылды"
            "Tree Keeper" -> "Ағаш сақшысы"
            "Map Explorer" -> "Карта зерттеушісі"
            else -> it
        }
    },
    localizedAchievementDescription = {
        when (it) {
            "Completed first recycling task" -> "Алғашқы қайта өңдеу тапсырмасы орындалды"
            "Reached 100 eco points" -> "100 эко-балл жиналды"
            "Joined your first challenge" -> "Алғашқы челленджге қосылдыңыз"
            "Reach virtual tree level 5" -> "Виртуалды ағаштың 5-деңгейіне жетіңіз"
            "Visit 5 recycling points" -> "5 қайта өңдеу пунктіне барыңыз"
            else -> it
        }
    },
    localizedTreeTitle = {
        when {
            it.startsWith("Level ") -> it.replace("Level ", "") + "-деңгей"
            it == "EcoPoints" -> "Эко-балл"
            it == "Goal" -> "Мақсат"
            it == "Tree" -> "Ағаш"
            it == "Young Plant" -> "Жас өсімдік"
            it == "Seedling" -> "Көшет"
            else -> it
        }
    },
    localizedTreeDescription = {
        when {
            it == "Every action grows your tree" -> "Әр әрекет ағашыңызды өсіреді"
            it == "Reached level 4" -> "4-деңгейге жетті"
            it == "New leaves unlocked" -> "Жаңа жапырақтар ашылды"
            it == "Started eco journey" -> "Эко сапар басталды"
            it.startsWith("Reach ") -> it.replace("Reach", "Жетіңіз").replace("EcoPoints", "эко-баллға")
            it.contains("unlocked with") -> it
                .replace("Twig", "Бұтақ")
                .replace("Seedling", "Көшет")
                .replace("Young tree", "Жас ағаш")
                .replace("Growing tree", "Өсіп жатқан ағаш")
                .replace("Dense tree", "Қалың ағаш")
                .replace("Eco forest", "Эко орман")
                .replace("unlocked with", "ашылды, жиналған")
                .replace("EcoPoints", "эко-балл")
            else -> it
        }
    },
    supportTopic = {
        when (it) {
            "Recycling point issue" -> "Қайта өңдеу пункті мәселесі"
            "Waste submission" -> "Қалдық тапсыру"
            "Eco points" -> "Эко-балл"
            "Account and profile" -> "Аккаунт және профиль"
            else -> it
        }
    },
    supportContactTitle = {
        when (it) {
            "EcoDala Hotline" -> "EcoDala жедел желісі"
            "WhatsApp Support" -> "WhatsApp қолдауы"
            "Email" -> "Email"
            else -> it
        }
    },
    supportContactSubtitle = {
        when (it) {
            "Daily, 09:00 - 20:00" -> "Күн сайын, 09:00 - 20:00"
            "Fast replies in Kazakhstan" -> "Қазақстан бойынша жылдам жауап"
            "Response within 24 hours" -> "24 сағат ішінде жауап"
            else -> it
        }
    },
    supportFaqQuestion = {
        when (it) {
            "How are EcoPoints counted?" -> "EcoPoints қалай есептеледі?"
            "Can I suggest a new recycling point?" -> "Жаңа қайта өңдеу пунктін ұсынуға бола ма?"
            "Which cities are supported?" -> "Қай қалалар қолдайды?"
            else -> it
        }
    },
    supportFaqAnswer = {
        when (it) {
            "Points are added after a confirmed waste submission or completed challenge." -> "Балл расталған қалдық тапсырудан немесе аяқталған челленджден кейін қосылады."
            "Yes. Send the address, photo and accepted waste types through support." -> "Иә. Мекенжайды, фотоны және қабылданатын қалдық түрлерін қолдау арқылы жіберіңіз."
            "The demo flow is prepared for Kazakhstan, starting with Almaty and Astana." -> "Демо сценарий Қазақстанға, алдымен Алматы мен Астанаға дайындалған."
            else -> it
        }
    },
    wasteTypeName = {
        when (it) {
            WasteType.Plastic -> "Пластик"
            WasteType.Paper -> "Қағаз"
            WasteType.Glass -> "Шыны"
            WasteType.Batteries -> "Батареялар"
            WasteType.Electronics -> "Электроника"
            WasteType.Organic -> "Органика"
            WasteType.Metal -> "Металл"
        }
    },
    challengeTypeName = {
        when (it) {
            ChallengeType.Daily -> "Күнделікті"
            ChallengeType.Weekly -> "Апталық"
            ChallengeType.Special -> "Арнайы"
        }
    },
    challengeStatusName = {
        when (it) {
            ChallengeStatus.Active -> "Белсенді"
            ChallengeStatus.Completed -> "Аяқталды"
            ChallengeStatus.Locked -> "Құлыптаулы"
        }
    }
)

/** Small labels for the SIS4 demonstration, using the existing language selection. */
fun EcoStrings.sis4Text(en: String, ru: String, kk: String): String = localized(en, ru, kk)
