package kz.ecodala.components

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
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onMapClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Map,
                    contentDescription = "Map"
                )
            },
            label = {
                Text("Map")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onSubmitClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Submit"
                )
            },
            label = {
                Text("Submit")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onLeaderboardClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = "Leaderboard"
                )
            },
            label = {
                Text("Leads")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = onProfileClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }
}