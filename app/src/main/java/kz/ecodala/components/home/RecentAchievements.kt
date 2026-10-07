package kz.ecodala.components.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.ecodala.model.Achievement
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.data.achievements
import kz.ecodala.ui.theme.EcoDalaTheme

@Composable
fun RecentAchievements(
    achievements: List<Achievement>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
    ) {
        Text(
            text = "Recent Achievements",
            style = MaterialTheme.typography.titleMedium
        )

        achievements.forEach { achievement ->
            RecentAchievementItem(
                title = achievement.title,
                description = achievement.description,
                date = achievement.date
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecentAchievementsPreview() {
    EcoDalaTheme {
        RecentAchievements(
            achievements = achievements.take(3)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RecentAchievementsDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        RecentAchievements(
            achievements = achievements.take(3)
        )
    }
}