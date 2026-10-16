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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.R
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing

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
                contentDescription = stringResource(R.string.build_route_description)
            )

            Spacer(
                modifier = Modifier.width(EcoSpacing.Small)
            )

            Text(text = stringResource(R.string.build_route_button))
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
                    contentDescription = stringResource(R.string.call_description)
                )

                Spacer(
                    modifier = Modifier.width(EcoSpacing.Small)
                )

                Text(text = stringResource(R.string.call_button))
            }

            OutlinedButton(
                onClick = onShareClick,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.share)
                )

                Spacer(
                    modifier = Modifier.width(EcoSpacing.Small)
                )

                Text(text = stringResource(R.string.share))
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