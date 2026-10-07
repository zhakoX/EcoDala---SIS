package kz.ecodala.components.recycling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.ui.theme.EcoDalaTheme

@Composable
fun AcceptedWasteSection(
    acceptedTypes: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
    ) {
        Text(
            text = "Accepted Waste Types",
            style = MaterialTheme.typography.titleMedium
        )

        acceptedTypes
            .chunked(2)
            .forEach { rowTypes ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
                ) {
                    rowTypes.forEach { type ->
                        WasteTypeChip(
                            type = type,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (rowTypes.size == 1) {
                        Spacer(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
    }
}


@Preview(showBackground = true)
@Composable
private fun AcceptedWasteSectionPreview() {
    EcoDalaTheme {
        AcceptedWasteSection(
            acceptedTypes = listOf(
                "Plastic",
                "Paper",
                "Glass",
                "Batteries",
                "Electronics"
            )
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun AcceptedWasteSectionDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        AcceptedWasteSection(
            acceptedTypes = listOf(
                "Plastic",
                "Paper",
                "Glass",
                "Batteries",
                "Electronics"
            )
        )
    }
}