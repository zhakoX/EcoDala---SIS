package kz.ecodala.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.ecodala.R
import kz.ecodala.components.leaderboard.LeaderboardItem
import kz.ecodala.components.leaderboard.RankingHeader
import kz.ecodala.model.User
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing

@Composable
fun LeaderboardRoute(
    onBackClick: () -> Unit,
    viewModel: LeaderboardViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LeaderboardScreen(
        uiState = uiState.value,
        onBackClick = onBackClick,
        onRetry = viewModel::retry
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    uiState: LeaderboardUiState,
    onBackClick: () -> Unit,
    onRetry: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.leaderboard_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (uiState.status) {
            LeaderboardStatus.LOADING -> LeaderboardLoadingState(paddingValues)
            LeaderboardStatus.CONTENT -> LeaderboardContent(
                users = uiState.users,
                paddingValues = paddingValues
            )
            LeaderboardStatus.EMPTY -> EmptyLeaderboardState(paddingValues)
            LeaderboardStatus.ERROR -> LeaderboardErrorState(
                paddingValues = paddingValues,
                message = uiState.errorMessage
                    ?: stringResource(R.string.leaderboard_error),
                onRetry = onRetry
            )
        }
    }
}

@Composable
private fun LeaderboardContent(
    users: List<User>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentPadding = PaddingValues(EcoSpacing.Medium),
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
    ) {
        item { RankingHeader() }

        itemsIndexed(
            items = users,
            key = { _, user -> user.id }
        ) { index, user ->
            LeaderboardItem(
                rank = index + 1,
                name = user.name,
                faculty = user.faculty,
                points = user.points
            )
        }
    }
}

@Composable
private fun EmptyLeaderboardState(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(EcoSpacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small, Alignment.CenterVertically)
    ) {
        Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(R.string.no_rankings_yet),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = stringResource(R.string.first_to_earn_points),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LeaderboardLoadingState(paddingValues: PaddingValues) {
    LeaderboardMessageState(
        paddingValues = paddingValues,
        message = stringResource(R.string.leaderboard_loading),
        showProgress = true
    )
}

@Composable
private fun LeaderboardErrorState(
    paddingValues: PaddingValues,
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
            Text(text = stringResource(R.string.leaderboard_retry))
        }
    }
}

@Composable
private fun LeaderboardMessageState(
    paddingValues: PaddingValues,
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

private val previewUsers = listOf(
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
    )
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LeaderboardContentPreview() {
    EcoDalaTheme {
        LeaderboardScreen(
            uiState = LeaderboardUiState(
                status = LeaderboardStatus.CONTENT,
                users = previewUsers
            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LeaderboardLoadingPreview() {
    EcoDalaTheme {
        LeaderboardScreen(
            uiState = LeaderboardUiState(status = LeaderboardStatus.LOADING),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LeaderboardEmptyPreview() {
    EcoDalaTheme {
        LeaderboardScreen(
            uiState = LeaderboardUiState(status = LeaderboardStatus.EMPTY),
            onBackClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LeaderboardErrorPreview() {
    EcoDalaTheme {
        LeaderboardScreen(
            uiState = LeaderboardUiState(
                status = LeaderboardStatus.ERROR,
                errorMessage = "Unable to load leaderboard"
            ),
            onBackClick = {},
            onRetry = {}
        )
    }
}
