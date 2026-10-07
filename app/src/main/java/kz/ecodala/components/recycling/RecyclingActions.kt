package kz.ecodala.components.recycling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.ecodala.ui.theme.EcoSpacing
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.ui.theme.EcoDalaTheme

@Composable
fun RecyclingActions(
    onRouteClick: () -> Unit,
    onCallClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
    ) {
        Button(
            onClick = onRouteClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Build route"
            )

            Spacer(
                modifier = Modifier.width(EcoSpacing.Small)
            )

            Text(text = "Build Route")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(EcoSpacing.Small)
        ) {
            OutlinedButton(
                onClick = onCallClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call"
                )

                Spacer(
                    modifier = Modifier.width(EcoSpacing.Small)
                )

                Text(text = "Call")
            }

            OutlinedButton(
                onClick = onShareClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )

                Spacer(
                    modifier = Modifier.width(EcoSpacing.Small)
                )

                Text(text = "Share")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecyclingActionsPreview() {
    EcoDalaTheme {
        RecyclingActions(
            onRouteClick = {},
            onCallClick = {},
            onShareClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RecyclingActionsDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        RecyclingActions(
            onRouteClick = {},
            onCallClick = {},
            onShareClick = {}
        )
    }
}