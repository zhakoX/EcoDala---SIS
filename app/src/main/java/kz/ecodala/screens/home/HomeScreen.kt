package kz.ecodala.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import kz.ecodala.components.common.EcoBottomBar
import kz.ecodala.components.home.EcoCard
import kz.ecodala.components.home.QuickActions
import kz.ecodala.components.home.RecentAchievements
import kz.ecodala.components.home.VirtualTreeCard
import kz.ecodala.data.achievements
import kz.ecodala.data.currentUser
import kz.ecodala.data.homeStats
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.tooling.preview.Preview
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
                        text = "Hello, ${currentUser.name} 👋"
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
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
                onHomeClick = {},
                onMapClick = onRecyclingPointClick,
                onSubmitClick = {},
                onLeaderboardClick = onLeaderboardClick,
                onProfileClick = {}
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(EcoSpacing.Medium),
            verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium)
        ) {
            EcoCard(
                points = homeStats.points,
                level = homeStats.level,
                globalRank = homeStats.globalRank
            )

            VirtualTreeCard(
                progress = homeStats.treeProgress,
                currentLevel = homeStats.level,
                nextLevel = homeStats.nextLevel
            )

            Text(
                text = "QUICK ACTIONS",
                style = MaterialTheme.typography.titleMedium
            )

            QuickActions(
                onMapClick = onRecyclingPointClick,
                onSubmitClick = {},
                onChallengesClick = {},
                onLeaderboardClick = onLeaderboardClick
            )

            RecentAchievements(
                achievements = achievements.take(3)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
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
    EcoDalaTheme(darkTheme = true) {
        HomeScreen(
            onLeaderboardClick = {},
            onRecyclingPointClick = {}
        )
    }
}