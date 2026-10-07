package kz.ecodala.screens.recycling

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kz.ecodala.R
import kz.ecodala.components.recycling.AcceptedWasteSection
import kz.ecodala.components.recycling.RecyclingActions
import kz.ecodala.components.recycling.RecyclingPointInfo
import kz.ecodala.components.recycling.SustainableFactCard
import kz.ecodala.data.recyclingPoints
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclingPointScreen(
    recyclingPointId: Int,
    onBackClick: () -> Unit
) {
    val recyclingPoint = recyclingPoints.find {
        it.id == recyclingPointId
    }

    var isFavorite by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = recyclingPoint?.name ?: "Recycling Point",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )
                    }

                    IconButton(
                        onClick = {
                            isFavorite = !isFavorite
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = if (isFavorite) {
                                "Remove from favorites"
                            } else {
                                "Add to favorites"
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = EcoSpacing.Medium),
            verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium)
        ) {
            item {
                Image(
                    painter = painterResource(
                        id = R.drawable.recycling_center
                    ),
                    contentDescription = "Green Recycling Center",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            item {
                Text(
                    text = recyclingPoint?.name ?: "Recycling Point",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            item {
                RecyclingPointInfo(
                    address = recyclingPoint?.address
                        ?: "Address unavailable",
                    phone = recyclingPoint?.phone
                        ?: "Phone unavailable",
                    openHours = recyclingPoint?.openHours
                        ?: "Opening hours unavailable"
                )
            }

            item {
                AcceptedWasteSection(
                    acceptedTypes = recyclingPoint
                        ?.acceptedTypes
                        .orEmpty()
                )
            }

            item {
                SustainableFactCard(
                    description = recyclingPoint?.description
                        ?: "No sustainability information available."
                )
            }

            item {
                RecyclingActions(
                    onRouteClick = {},
                    onCallClick = {},
                    onShareClick = {}
                )
            }

            item {
                androidx.compose.foundation.layout.Spacer(
                    modifier = Modifier.height(EcoSpacing.Large)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecyclingPointScreenPreview() {
    EcoDalaTheme {
        RecyclingPointScreen(
            recyclingPointId = 1,
            onBackClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun RecyclingPointScreenDarkPreview() {
    EcoDalaTheme(darkTheme = true) {
        RecyclingPointScreen(
            recyclingPointId = 1,
            onBackClick = {}
        )
    }
}