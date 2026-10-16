package kz.ecodala.components.common

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kz.ecodala.R
import kz.ecodala.ui.theme.EcoDalaTheme

@Composable
fun EcoBottomBar(
    onHomeClick: () -> Unit,
    onMapClick: () -> Unit,
    onSubmitClick: () -> Unit,
    onLeaderboardClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = onHomeClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.action_home)
                )
            },
            label = {
                Text(stringResource(R.string.action_home))
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onMapClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = stringResource(R.string.action_map)
                )
            },
            label = {
                Text(stringResource(R.string.action_map))
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onSubmitClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(R.string.action_submit)
                )
            },
            label = {
                Text(stringResource(R.string.action_submit))
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onLeaderboardClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = stringResource(R.string.action_leaderboard)
                )
            },
            label = {
                Text(stringResource(R.string.bottom_bar_leads))
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.action_profile)
                )
            },
            label = {
                Text(stringResource(R.string.action_profile))
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EcoBottomBarPreview() {
    EcoDalaTheme {
        EcoBottomBar(
            onHomeClick = {},
            onMapClick = {},
            onSubmitClick = {},
            onLeaderboardClick = {},
            onProfileClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun EcoBottomBarDarkPreview() {
    EcoDalaTheme(
        darkTheme = true
    ) {
        EcoBottomBar(
            onHomeClick = {},
            onMapClick = {},
            onSubmitClick = {},
            onLeaderboardClick = {},
            onProfileClick = {}
        )
    }
}