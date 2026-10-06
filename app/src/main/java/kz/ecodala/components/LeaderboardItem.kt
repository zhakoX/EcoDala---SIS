package kz.ecodala.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LeaderboardItem(
    rank: Int,
    name: String,
    faculty: String,
    points: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Rank
            Text(
                text = when (rank) {
                    1 -> "🥇"
                    2 -> "🥈"
                    3 -> "🥉"
                    else -> "$rank"
                },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(40.dp)
            )

            // Avatar
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User avatar"
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // Name + Faculty
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = faculty,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Eco points
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Park,
                    contentDescription = "Eco points",
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "$points pts",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}