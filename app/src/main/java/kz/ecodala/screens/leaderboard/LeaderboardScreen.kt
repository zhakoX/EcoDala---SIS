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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.components.leaderboard.LeaderboardItem
import kz.ecodala.components.leaderboard.RankingHeader
import kz.ecodala.data.users
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Leaderboard",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(EcoSpacing.Medium),
            verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
        ) {
            if (users.isEmpty()) {
                item {
                    EmptyLeaderboardState()
                }
            } else {
                item {
                    RankingHeader()
                }

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
    }
}

@Composable
private fun EmptyLeaderboardState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = EcoSpacing.Large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
    ) {
        Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "No rankings yet",
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Be the first to earn EcoPoints!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LeaderboardScreenPreview() {
    EcoDalaTheme {
        LeaderboardScreen(onBackClick = {})
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun LeaderboardScreenDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        LeaderboardScreen(onBackClick = {})
    }
}