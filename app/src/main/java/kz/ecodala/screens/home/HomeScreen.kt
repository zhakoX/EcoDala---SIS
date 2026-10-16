package kz.ecodala.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.ecodala.R
import kz.ecodala.components.common.EcoBottomBar
import kz.ecodala.components.home.EcoCard
import kz.ecodala.components.home.QuickActions
import kz.ecodala.components.home.RecentAchievements
import kz.ecodala.components.home.VirtualTreeCard
import kz.ecodala.model.Achievement
import kz.ecodala.model.HomeStats
import kz.ecodala.model.User
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing

@Composable
fun HomeRoute(
    onLeaderboardClick: () -> Unit,
    onRecyclingPointClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState.value,
        onLeaderboardClick = onLeaderboardClick,
        onRecyclingPointClick = onRecyclingPointClick,
        onRetry = viewModel::retry
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onLeaderboardClick: () -> Unit,
    onRecyclingPointClick: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            R.string.home_greeting,
                            uiState.user?.name.orEmpty()
                        )
                    )
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = stringResource(R.string.notifications)
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
        when (uiState.status) {
            HomeStatus.LOADING -> HomeLoadingState(paddingValues = paddingValues)
            HomeStatus.CONTENT -> HomeContent(
                uiState = uiState,
                paddingValues = paddingValues,
                onLeaderboardClick = onLeaderboardClick,
                onRecyclingPointClick = onRecyclingPointClick
            )
            HomeStatus.EMPTY -> HomeEmptyState(paddingValues = paddingValues)
            HomeStatus.ERROR -> HomeErrorState(
                paddingValues = paddingValues,
                message = uiState.errorMessage.orEmpty(),
                onRetry = onRetry
            )
        }
    }
}

@Composable
private fun HomeContent(
    uiState: HomeUiState,
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    onLeaderboardClick: () -> Unit,
    onRecyclingPointClick: () -> Unit
) {
    val stats = uiState.stats ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(EcoSpacing.Medium),
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium)
    ) {
        EcoCard(
            points = stats.points,
            level = stats.level,
            globalRank = stats.globalRank
        )

        VirtualTreeCard(
            progress = stats.treeProgress,
            currentLevel = stats.level,
            nextLevel = stats.nextLevel
        )

        Text(
            text = stringResource(R.string.quick_actions_title),
            style = MaterialTheme.typography.titleMedium
        )

        QuickActions(
            onMapClick = onRecyclingPointClick,
            onSubmitClick = {},
            onChallengesClick = {},
            onLeaderboardClick = onLeaderboardClick
        )

        RecentAchievements(achievements = uiState.recentAchievements)
    }
}

@Composable
private fun HomeLoadingState(
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    HomeMessageState(
        paddingValues = paddingValues,
        message = stringResource(R.string.home_loading),
        showProgress = true
    )
}

@Composable
private fun HomeEmptyState(
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    HomeMessageState(
        paddingValues = paddingValues,
        message = stringResource(R.string.home_empty)
    )
}

@Composable
private fun HomeErrorState(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(EcoSpacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium, Alignment.CenterVertically)
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.home_retry))
        }
    }
}

@Composable
private fun HomeMessageState(
    paddingValues: androidx.compose.foundation.layout.PaddingValues,
    message: String,
    showProgress: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(EcoSpacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium, Alignment.CenterVertically)
    ) {
        if (showProgress) {
            CircularProgressIndicator()
        }
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private val previewUser = User(
    id = 9,
    name = "Zhanarys A.",
    faculty = "Software Engineering",
    points = 540,
    avatarResId = R.mipmap.ic_launcher
)

private val previewStats = HomeStats(
    points = 540,
    level = 4,
    globalRank = 12,
    treeProgress = 0.70f,
    nextLevel = 5
)

private val previewAchievements = listOf(
    Achievement(
        id = 1,
        title = "First Recycling",
        description = "Recycled your first item",
        date = "Yesterday",
        iconResId = R.mipmap.ic_launcher
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenContentPreview() {
    EcoDalaTheme {
        HomeScreen(
            uiState = HomeUiState(
                status = HomeStatus.CONTENT,
                user = previewUser,
                stats = previewStats,
                recentAchievements = previewAchievements
            ),
            onLeaderboardClick = {},
            onRecyclingPointClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenLoadingPreview() {
    EcoDalaTheme {
        HomeScreen(
            uiState = HomeUiState(status = HomeStatus.LOADING),
            onLeaderboardClick = {},
            onRecyclingPointClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenEmptyPreview() {
    EcoDalaTheme {
        HomeScreen(
            uiState = HomeUiState(status = HomeStatus.EMPTY),
            onLeaderboardClick = {},
            onRecyclingPointClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenErrorPreview() {
    EcoDalaTheme {
        HomeScreen(
            uiState = HomeUiState(
                status = HomeStatus.ERROR,
                errorMessage = "Unable to load home data"
            ),
            onLeaderboardClick = {},
            onRecyclingPointClick = {},
            onRetry = {}
        )
    }
}
