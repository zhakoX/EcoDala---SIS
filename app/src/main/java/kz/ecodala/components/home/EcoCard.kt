package kz.ecodala.components.home

import android.adservices.ondevicepersonalization.TrainingExampleRecord
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.res.stringResource
import kz.ecodala.R

@Composable
fun EcoCard(
    points: Int,
    level: Int,
    globalRank: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(EcoSpacing.Medium),
            verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.current_eco_rating),
                    style = MaterialTheme.typography.labelMedium
                )

                Text(
                    text = stringResource(R.string.eco_level, level),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Text(
                text = stringResource(R.string.eco_points, points),
                style = MaterialTheme.typography.headlineMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.global_rank_description),
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = stringResource(R.string.global_rank, globalRank),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = stringResource(R.string.open_global_ranking),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EcoCardPreview() {
    EcoDalaTheme {
        EcoCard(
            points = 450,
            level = 4,
            globalRank = 12
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun EcoCardDarkPreview() {
    EcoDalaTheme(
        darkTheme = true
    ) {
        EcoCard(
            points = 450,
            level = 4,
            globalRank = 12
        )
    }
}