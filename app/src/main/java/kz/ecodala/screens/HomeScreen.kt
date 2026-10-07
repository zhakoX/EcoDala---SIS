package kz.ecodala.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Map
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
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.components.ActionItem
import kz.ecodala.components.EcoBottomBar
import kz.ecodala.components.EcoCard
import kz.ecodala.components.RecentAchievementItem
import kz.ecodala.components.VirtualTreeCard
import kz.ecodala.data.achievements
import kz.ecodala.ui.theme.EcoDalaTheme

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
                        onClick = { }
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
                onHomeClick = { },
                onMapClick = { },
                onSubmitClick = { },
                onLeaderboardClick = onLeaderboardClick,
                onProfileClick = { }
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
                        onClick = { }
                    )
                }

                item {
                    ActionItem(
                        title = "Challenges",
                        icon = Icons.Default.EmojiEvents,
                        onClick = { }
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

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun HomeScreenPreview() {
    EcoDalaTheme {
        HomeScreen(
            onLeaderboardClick = {},
            onRecyclingPointClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenDarkPreview() {
    EcoDalaTheme(
        darkTheme = true
    ) {
        HomeScreen(
            onLeaderboardClick = {},
            onRecyclingPointClick = {}
        )
    }
}