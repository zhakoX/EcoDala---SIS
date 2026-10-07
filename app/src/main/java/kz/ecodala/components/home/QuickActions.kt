package kz.ecodala.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.ui.theme.EcoDalaTheme

private data class QuickAction(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)

@Composable
fun QuickActions(
    onMapClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onChallengesClick: () -> Unit,
    onLeaderboardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val actions = listOf(
        QuickAction(
            title = "Map",
            icon = Icons.Default.Map,
            onClick = onMapClick
        ),
        QuickAction(
            title = "Submit",
            icon = Icons.Default.AddCircle,
            onClick = onSubmitClick
        ),
        QuickAction(
            title = "Challenges",
            icon = Icons.Default.EmojiEvents,
            onClick = onChallengesClick
        ),
        QuickAction(
            title = "Leaderboard",
            icon = Icons.Default.BarChart,
            onClick = onLeaderboardClick
        )
    )

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(EcoSpacing.Medium)
    ) {
        items(
            items = actions,
            key = { it.title }
        ) { action ->
            ActionItem(
                title = action.title,
                icon = action.icon,
                onClick = action.onClick
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun QuickActionsPreview() {
    EcoDalaTheme {
        QuickActions(
            onMapClick = {},
            onSubmitClick = {},
            onChallengesClick = {},
            onLeaderboardClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun QuickActionsDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        QuickActions(
            onMapClick = {},
            onSubmitClick = {},
            onChallengesClick = {},
            onLeaderboardClick = {}
        )
    }
}