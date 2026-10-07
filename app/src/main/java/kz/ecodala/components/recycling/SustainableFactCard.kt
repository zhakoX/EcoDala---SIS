package kz.ecodala.components.recycling

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.ui.theme.EcoDalaTheme

@Composable
fun SustainableFactCard(
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(EcoSpacing.Medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Sustainable fact",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = EcoSpacing.Small)
            ) {
                Text(
                    text = "Sustainable Fact",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SustainableFactCardPreview() {
    EcoDalaTheme {
        SustainableFactCard(
            description = "Recycling one ton of paper saves about 17 trees."
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SustainableFactCardDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        SustainableFactCard(
            description = "Recycling one ton of paper saves about 17 trees."
        )
    }
}