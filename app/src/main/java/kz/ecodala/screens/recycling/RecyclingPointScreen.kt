package kz.ecodala.screens.recycling

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kz.ecodala.R
import kz.ecodala.components.recycling.AcceptedWasteSection
import kz.ecodala.components.recycling.RecyclingActions
import kz.ecodala.components.recycling.RecyclingPointInfo
import kz.ecodala.components.recycling.SustainableFactCard
import kz.ecodala.model.RecyclingPoint
import kz.ecodala.ui.theme.EcoDalaTheme
import kz.ecodala.ui.theme.EcoSpacing

@Composable
fun RecyclingPointRoute(
    onBackClick: () -> Unit,
    onRouteClick: () -> Unit = {},
    onCallClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    viewModel: RecyclingPointViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RecyclingPointScreen(
        uiState = uiState.value,
        onBackClick = onBackClick,
        onToggleFavorite = viewModel::toggleFavorite,
        onRetry = viewModel::retry,
        onRouteClick = onRouteClick,
        onCallClick = onCallClick,
        onShareClick = onShareClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclingPointScreen(
    uiState: RecyclingPointUiState,
    onBackClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onRetry: () -> Unit,
    onRouteClick: () -> Unit,
    onCallClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.recyclingPoint?.name
                            ?: stringResource(R.string.recycling_point_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = stringResource(R.string.share)
                        )
                    }
                    IconButton(
                        onClick = onToggleFavorite,
                        enabled = uiState.status == RecyclingPointStatus.CONTENT
                    ) {
                        Icon(
                            imageVector = if (uiState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (uiState.isFavorite) {
                                stringResource(R.string.remove_from_favorites)
                            } else {
                                stringResource(R.string.add_to_favorites)
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (uiState.status) {
            RecyclingPointStatus.LOADING -> RecyclingPointLoadingState(paddingValues)
            RecyclingPointStatus.CONTENT -> {
                val recyclingPoint = uiState.recyclingPoint
                if (recyclingPoint == null) RecyclingPointEmptyState(paddingValues) else RecyclingPointContent(
                    recyclingPoint, paddingValues, onRouteClick, onCallClick, onShareClick
                )
            }
            RecyclingPointStatus.EMPTY -> RecyclingPointEmptyState(paddingValues)
            RecyclingPointStatus.ERROR -> RecyclingPointErrorState(
                paddingValues,
                uiState.errorMessage ?: stringResource(R.string.recycling_point_error),
                onRetry
            )
        }
    }
}

@Composable
private fun RecyclingPointContent(
    recyclingPoint: RecyclingPoint,
    paddingValues: PaddingValues,
    onRouteClick: () -> Unit,
    onCallClick: () -> Unit,
    onShareClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = EcoSpacing.Medium),
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.recycling_center),
                contentDescription = stringResource(R.string.recycling_center_description),
                modifier = Modifier.fillMaxWidth().height(180.dp)
            )
        }
        item {
            Text(
                text = recyclingPoint.name,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        item {
            RecyclingPointInfo(
                address = recyclingPoint.address,
                phone = recyclingPoint.phone,
                openHours = recyclingPoint.openHours
            )
        }
        item { AcceptedWasteSection(acceptedTypes = recyclingPoint.acceptedTypes) }
        item { SustainableFactCard(description = recyclingPoint.description) }
        item { RecyclingActions(onRouteClick, onCallClick, onShareClick) }
        item { Spacer(modifier = Modifier.height(EcoSpacing.Large)) }
    }
}

@Composable
private fun RecyclingPointLoadingState(paddingValues: PaddingValues) {
    RecyclingPointMessageState(paddingValues, stringResource(R.string.recycling_point_loading), true)
}

@Composable
private fun RecyclingPointEmptyState(paddingValues: PaddingValues) {
    RecyclingPointMessageState(paddingValues, stringResource(R.string.recycling_point_empty))
}

@Composable
private fun RecyclingPointErrorState(paddingValues: PaddingValues, message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(EcoSpacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium, Alignment.CenterVertically)
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
        Button(onClick = onRetry) { Text(text = stringResource(R.string.recycling_point_retry)) }
    }
}

@Composable
private fun RecyclingPointMessageState(paddingValues: PaddingValues, message: String, showProgress: Boolean = false) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(EcoSpacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(EcoSpacing.Medium, Alignment.CenterVertically)
    ) {
        if (showProgress) CircularProgressIndicator()
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
    }
}

private val previewRecyclingPoint = RecyclingPoint(
    id = 1,
    name = "Green Recycling Center",
    address = "12 Abai Avenue, Kyzylorda",
    phone = "+7 700 123 45 67",
    openHours = "09:00 - 18:00",
    imageResId = R.drawable.recycling_center,
    acceptedTypes = listOf("Paper", "Plastic", "Glass"),
    description = "Recycling one aluminum can saves enough energy to power a TV for three hours."
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecyclingPointLoadingPreview() = RecyclingPointPreview(RecyclingPointUiState(RecyclingPointStatus.LOADING))

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecyclingPointContentPreview() = RecyclingPointPreview(
    RecyclingPointUiState(RecyclingPointStatus.CONTENT, previewRecyclingPoint, isFavorite = true)
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecyclingPointEmptyPreview() = RecyclingPointPreview(RecyclingPointUiState(RecyclingPointStatus.EMPTY))

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecyclingPointErrorPreview() = RecyclingPointPreview(
    RecyclingPointUiState(RecyclingPointStatus.ERROR, errorMessage = "Unable to load recycling point")
)

@Composable
private fun RecyclingPointPreview(uiState: RecyclingPointUiState) {
    EcoDalaTheme {
        RecyclingPointScreen(uiState, {}, {}, {}, {}, {}, {})
    }
}
