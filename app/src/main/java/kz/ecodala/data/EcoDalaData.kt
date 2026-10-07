package kz.ecodala.data

import kz.ecodala.R
import kz.ecodala.model.Achievement
import kz.ecodala.model.RecyclingPoint
import kz.ecodala.model.User
import kz.ecodala.model.HomeStats

// ================================
// LEADERBOARD USERS
// ================================

val users = listOf(
    User(
        id = 1,
        name = "Aruzhan S.",
        faculty = "Engineering",
        points = 1250,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 2,
        name = "Dias K.",
        faculty = "IT & CS",
        points = 980,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 3,
        name = "Erasyl A.",
        faculty = "Computer Engineering",
        points = 870,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 4,
        name = "Madina B.",
        faculty = "Business",
        points = 810,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 5,
        name = "Nurdaulet B.",
        faculty = "Cyber Security",
        points = 760,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 6,
        name = "Alisher T.",
        faculty = "Mechanical Engineering",
        points = 690,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 7,
        name = "Aigerim K.",
        faculty = "Environmental Science",
        points = 620,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 8,
        name = "Bekarys A.",
        faculty = "Mobile Development",
        points = 580,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 9,
        name = "Zhanarys A.",
        faculty = "Software Engineering",
        points = 540,
        avatarResId = R.mipmap.ic_launcher
    ),
    User(
        id = 10,
        name = "Tomiris S.",
        faculty = "Economics",
        points = 500,
        avatarResId = R.mipmap.ic_launcher
    )
)

// ================================
// RECYCLING POINTS
// ================================

val recyclingPoints = listOf(
    RecyclingPoint(
        id = 1,
        name = "Green Recycling Center",
        address = "123 Eco Avenue, Green District, 45000",
        phone = "+1 (555) 234-5678",
        openHours = "08:00 AM - 07:00 PM",
        imageResId = R.mipmap.ic_launcher,
        acceptedTypes = listOf(
            "Plastic",
            "Paper",
            "Glass",
            "Batteries",
            "Electronics"
        ),
        description = "Recycling one ton of paper saves about 17 trees."
    )
)

// ================================
// ACHIEVEMENTS
// ================================

val achievements = listOf(
    Achievement(
        id = 1,
        title = "First Recycling",
        description = "Recycled your first item",
        date = "Yesterday",
        iconResId = R.mipmap.ic_launcher
    ),
    Achievement(
        id = 2,
        title = "100 Points",
        description = "Earned your first 100 EcoPoints",
        date = "2 days ago",
        iconResId = R.mipmap.ic_launcher
    ),
    Achievement(
        id = 3,
        title = "Challenge Joined",
        description = "Joined your first eco challenge",
        date = "Last week",
        iconResId = R.mipmap.ic_launcher
    )
)


val currentUser = users.first { it.id == 9 }

val homeStats = HomeStats(
    points = currentUser.points,
    level = 4,
    globalRank = 12,
    treeProgress = 0.70f,
    nextLevel = 5
)