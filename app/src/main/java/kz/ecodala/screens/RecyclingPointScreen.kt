package kz.ecodala.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.ecodala.components.WasteTypeChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclingPointScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Green Recycling Center"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ------------------------------------------------
            // 1. RECYCLING CENTER IMAGE
            // ------------------------------------------------

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Recycling Center",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            // ------------------------------------------------
            // 2. NAME
            // ------------------------------------------------

            item {
                Text(
                    text = "Green Recycling Center",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            // ------------------------------------------------
            // 3. ADDRESS
            // ------------------------------------------------

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Address"
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "123 Eco Avenue, Green District, 45000",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // ------------------------------------------------
            // 4. PHONE
            // ------------------------------------------------

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Phone"
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "+1 (555) 234-5678",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // ------------------------------------------------
            // 5. OPENING HOURS
            // ------------------------------------------------

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "08:00 AM - 07:00 PM"
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "OPEN",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            // ------------------------------------------------
            // 6. ACCEPTED WASTE TYPES
            // ------------------------------------------------

            item {
                Text(
                    text = "Accepted Waste Types",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WasteTypeChip(
                        type = "Plastic",
                        modifier = Modifier.weight(1f)
                    )

                    WasteTypeChip(
                        type = "Paper",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WasteTypeChip(
                        type = "Glass",
                        modifier = Modifier.weight(1f)
                    )

                    WasteTypeChip(
                        type = "Batteries",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    WasteTypeChip(
                        type = "Electronics",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ------------------------------------------------
            // 7. SUSTAINABLE FACT
            // ------------------------------------------------

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor =
                            MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {

                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Sustainable fact"
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        Column {
                            Text(
                                text = "Sustainable Fact",
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(4.dp)
                            )

                            Text(
                                text = "Recycling one ton of paper saves about 17 trees.",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            // ------------------------------------------------
            // 8. BUILD ROUTE BUTTON
            // ------------------------------------------------

            item {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Build route"
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Build Route"
                    )
                }
            }

            // ------------------------------------------------
            // 9. CALL + SHARE
            // ------------------------------------------------

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call"
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = "Call"
                        )
                    }

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share"
                        )

                        Spacer(
                            modifier = Modifier.width(6.dp)
                        )

                        Text(
                            text = "Share"
                        )
                    }
                }
            }

            // Bottom spacing
            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}