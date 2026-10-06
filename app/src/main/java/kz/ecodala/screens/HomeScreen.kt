package kz.ecodala.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import kz.ecodala.components.EcoCard
import kz.ecodala.components.VirtualTreeCard
import kz.ecodala.components.EcoBottomBar

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.BarChart
import kz.ecodala.components.ActionItem

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import kz.ecodala.components.RecentAchievementItem
import kz.ecodala.data.achievements

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLeaderboardClick: () -> Unit,
    onRecyclingPointClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hello, Zhanarys 👋"
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Пока ничего не делаем
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            )


        },

        bottomBar = {
            EcoBottomBar(
                onHomeClick = {
                    // Мы уже на Home
                },
                onMapClick = {
                    // Пока экран Map не входит в SIS3
                },
                onSubmitClick = {
                    // Пока экран Submit не входит в SIS3
                },
                onLeaderboardClick = onLeaderboardClick,
                onProfileClick = {
                    // Пока экран Profile не входит в SIS3
                }
            )
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            EcoCard(
                points = 450,
                level = 4,
                globalRank = 12
            )

            VirtualTreeCard(
                progress = 0.70f,
                currentLevel = 4,
                nextLevel = 5
            )

            Text(
                text = "QUICK ACTIONS",
                style = MaterialTheme.typography.titleMedium
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    ActionItem(
                        title = "Map",
                        icon = Icons.Default.Map,
                        onClick = onRecyclingPointClick
                    )
                }

                item {
                    ActionItem(
                        title = "Submit",
                        icon = Icons.Default.AddCircle,
                        onClick = {
                            // Пока экран Submit не входит в SIS3
                        }
                    )
                }

                item {
                    ActionItem(
                        title = "Challenges",
                        icon = Icons.Default.EmojiEvents,
                        onClick = {
                            // Пока экран Challenges не входит в SIS3
                        }
                    )
                }

                item {
                    ActionItem(
                        title = "Rank",
                        icon = Icons.Default.BarChart,
                        onClick = onLeaderboardClick
                    )
                }
            }

            Text(
                text = "Recent Achievements",
                style = MaterialTheme.typography.titleMedium
            )

            achievements.take(3).forEach { achievement ->

                RecentAchievementItem(
                    title = achievement.title,
                    description = achievement.description,
                    date = achievement.date
                )
            }
        }
    }
}