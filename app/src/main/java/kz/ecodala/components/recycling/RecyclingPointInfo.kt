package kz.ecodala.components.recycling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
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
fun RecyclingPointInfo(
    address: String,
    phone: String,
    openHours: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Address",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.width(EcoSpacing.Small)
            )

            Text(
                text = address,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Phone",
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.width(EcoSpacing.Small)
            )

            Text(
                text = phone,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = openHours,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.width(EcoSpacing.Small)
            )

            Text(
                text = "OPEN",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecyclingPointInfoPreview() {
    EcoDalaTheme {
        RecyclingPointInfo(
            address = "123 Eco Avenue, Green District, 45000",
            phone = "+1 (555) 234-5678",
            openHours = "08:00 AM - 07:00 PM"
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RecyclingPointInfoDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        RecyclingPointInfo(
            address = "123 Eco Avenue, Green District, 45000",
            phone = "+1 (555) 234-5678",
            openHours = "08:00 AM - 07:00 PM"
        )
    }
}