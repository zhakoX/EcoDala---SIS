package com.ecodala.feature.map.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.graphics.Bitmap
import android.graphics.Canvas as AndroidCanvas
import android.graphics.Paint
import android.graphics.Path as AndroidPath
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecodala.core.data.dummy.DummyEcoData
import com.ecodala.core.domain.model.RecyclingPoint
import com.ecodala.core.domain.model.WasteType
import com.ecodala.core.localization.LocalEcoStrings
import com.ecodala.core.localization.sis4Text
import com.ecodala.core.ui.components.EcoErrorState
import com.ecodala.core.ui.components.EcoInlineLoading
import com.ecodala.core.ui.theme.EcoDalaTheme
import com.ecodala.core.ui.theme.EcoGreen
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapRoute(
    onPointDetailsClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    MapScreen(
        uiState = uiState,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onCategorySelected = viewModel::onCategorySelected,
        onFavouriteClick = viewModel::onFavouriteClick,
        onPointSelected = viewModel::onPointSelected,
        onSelectionDismiss = viewModel::onSelectionDismiss,
        onPointClick = { onPointDetailsClick(it.id) },
        onRouteClick = { context.openRecyclingNavigation(it) },
        onRetry = viewModel::onRetry,
        onDemoErrorClick = viewModel::onDemoErrorClick,
        onDemoErrorDisabled = viewModel::onDemoErrorDisabled,
        onListModeChange = viewModel::onListModeChange,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    uiState: MapUiState,
    onSearchQueryChange: (String) -> Unit = {},
    onCategorySelected: (WasteType?) -> Unit = {},
    onFavouriteClick: (String) -> Unit = {},
    onPointSelected: (RecyclingPoint) -> Unit = {},
    onSelectionDismiss: () -> Unit = {},
    onPointClick: (RecyclingPoint) -> Unit = {},
    onRouteClick: (RecyclingPoint) -> Unit = {},
    onRetry: () -> Unit = {},
    onDemoErrorClick: () -> Unit = {},
    onDemoErrorDisabled: () -> Unit = {},
    onListModeChange: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val strings = LocalEcoStrings.current
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(title = { Text(strings.map, fontWeight = FontWeight.Bold) }, actions = {
            TextButton(onClick = onListModeChange) {
                Text(if (uiState.showList) strings.map else strings.sis4Text("List", "Список", "Тізім"))
            }
            IconButton(onClick = if (uiState.demoErrorEnabled) onDemoErrorDisabled else onDemoErrorClick) {
                Icon(Icons.Filled.BugReport, contentDescription = strings.sis4Text("Demo error mode", "Демо-ошибка", "Демо қате"),
                    tint = if (uiState.demoErrorEnabled) MaterialTheme.colorScheme.error else EcoGreen)
            }
        })
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Column(Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 8.dp)) {
                MapSearchBar(uiState.searchQuery, onSearchQueryChange)
                Spacer(Modifier.height(12.dp))
                WasteFilterRow(uiState.selectedWasteType, onCategorySelected)
                Text(strings.sis4Text("Demo locations · illustrative distances", "Демо-точки · условные расстояния", "Демо нүктелер · шартты қашықтықтар"),
                    style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 6.dp))
            }
            Box(Modifier.weight(1f).fillMaxWidth().clipToBounds()) {
                when {
                    uiState.isLoading -> EcoInlineLoading(
                        label = strings.sis4Text("Loading recycling points…", "Загрузка пунктов…", "Пункттер жүктелуде…"),
                        modifier = Modifier.align(Alignment.Center).padding(24.dp))
                    uiState.errorMessage != null -> Column(Modifier.align(Alignment.Center).padding(24.dp)) {
                        EcoErrorState(message = strings.sis4Text("Demo loading error. Retry or turn off error mode.",
                            "Демо-ошибка загрузки. Повторите или отключите режим ошибки.", "Демо жүктеу қатесі. Қайталаңыз немесе қате режимін өшіріңіз."), onRetryClick = onRetry,
                            title = strings.sis4Text("Loading error", "Ошибка загрузки", "Жүктеу қатесі"),
                            retryLabel = strings.sis4Text("Retry", "Повторить", "Қайталау"))
                        if (uiState.demoErrorEnabled) TextButton(onClick = onDemoErrorDisabled) {
                            Text(strings.sis4Text("Turn off error mode", "Отключить ошибку", "Қате режимін өшіру"))
                        }
                    }
                    uiState.filteredPoints.isEmpty() -> EmptyMapResult(Modifier.align(Alignment.Center).padding(24.dp))
                    uiState.showList -> LazyColumn(
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.filteredPoints, key = { it.id }) { point ->
                            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                shape = RoundedCornerShape(22.dp)) {
                                Row(Modifier.fillMaxWidth().padding(start = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Text(point.acceptedWasteTypes.take(3).joinToString(" · ") { strings.wasteTypeName(it) },
                                        Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelMedium, color = EcoGreen)
                                    FavouriteButton(point.id in uiState.favouriteIds) { onFavouriteClick(point.id) }
                                }
                                RecyclingPointPreviewCard(point, { onPointClick(point) }, { onRouteClick(point) },
                                    expanded = false, onExpandedChange = { onPointClick(point) })
                            }
                        }
                    }
                    else -> {
                        OpenStreetMapView(uiState.filteredPoints, uiState.selectedPoint, onPointSelected, Modifier.fillMaxSize())
                        uiState.selectedPoint?.let { point ->
                            var expanded by rememberSaveable(point.id) { mutableStateOf(false) }
                            Column(Modifier.align(Alignment.BottomCenter)) {
                                Row(Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface),
                                    horizontalArrangement = Arrangement.End) {
                                    FavouriteButton(point.id in uiState.favouriteIds) { onFavouriteClick(point.id) }
                                    IconButton(onClick = onSelectionDismiss) { Icon(Icons.Filled.Close, contentDescription = strings.sis4Text("Close", "Закрыть", "Жабу")) }
                                }
                                RecyclingPointPreviewCard(point, { onPointClick(point) }, { onRouteClick(point) },
                                    expanded, { expanded = it })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FavouriteButton(selected: Boolean, onClick: () -> Unit) {
    IconToggleButton(checked = selected, onCheckedChange = { onClick() }) {
        Icon(if (selected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = LocalEcoStrings.current.sis4Text("Toggle favourite", "Избранное", "Таңдаулы"),
            tint = if (selected) EcoGreen else MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun OpenStreetMapView(
    points: List<RecyclingPoint>, selectedPoint: RecyclingPoint?,
    onPointSelected: (RecyclingPoint) -> Unit, modifier: Modifier = Modifier
) {
    if (LocalInspectionMode.current) { StylizedMapBackground(modifier); return }
    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current
    val map = remember(context) {
        Configuration.getInstance().userAgentValue = context.packageName
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            minZoomLevel = 4.0
            maxZoomLevel = 19.0
            controller.setZoom(12.5)
            controller.setCenter(GeoPoint(43.238949, 76.919709))
        }
    }
    DisposableEffect(map, owner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> map.onResume()
                Lifecycle.Event.ON_PAUSE -> map.onPause()
                else -> Unit
            }
        }
        owner.lifecycle.addObserver(observer)
        if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) map.onResume()
        onDispose { owner.lifecycle.removeObserver(observer); map.onPause(); map.onDetach() }
    }
    LaunchedEffect(selectedPoint?.id) {
        selectedPoint?.let { map.controller.animateTo(GeoPoint(it.latitude, it.longitude)) }
    }
    AndroidView(factory = { map }, modifier = modifier, update = { view ->
        view.overlays.clear()
        points.forEach { point ->
            view.overlays.add(Marker(view).apply {
                position = GeoPoint(point.latitude, point.longitude)
                title = point.name
                snippet = point.address
                icon = createWasteMarkerIcon(context, point.acceptedWasteTypes.firstOrNull() ?: WasteType.Plastic,
                    selectedPoint?.id == point.id)
                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                setOnMarkerClickListener { _, _ -> onPointSelected(point); true }
            })
        }
        view.invalidate()
    })
}

/** Navigation is delegated to an installed maps application; no route service or GPS required. */
fun Context.openRecyclingNavigation(point: RecyclingPoint) {
    val uri = Uri.parse("geo:${point.latitude},${point.longitude}?q=${point.latitude},${point.longitude}(${Uri.encode(point.name)})")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (intent.resolveActivity(packageManager) != null) startActivity(intent)
}

@Composable
private fun WasteFilterRow(selectedWasteType: WasteType?, onCategorySelected: (WasteType?) -> Unit) {
    val strings = LocalEcoStrings.current
    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        item { FilterChip(strings.all, Icons.Filled.Recycling, selectedWasteType == null, simpleFilterColors()) { onCategorySelected(null) } }
        items(WasteType.entries) { type ->
            FilterChip(strings.wasteTypeName(type), when(type) {
                WasteType.Plastic -> Icons.Filled.LocalDrink
                WasteType.Glass -> Icons.Filled.WineBar
                WasteType.Paper -> Icons.Filled.InsertDriveFile
                else -> Icons.Filled.Recycling
            }, selectedWasteType == type, type.filterChipColors()) { onCategorySelected(type) }
        }
    }
}

@Composable
private fun MapSearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = Color(0xFF6C756E),
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.size(14.dp))
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp
            ),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = LocalEcoStrings.current.searchLocation,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 15.sp
                    )
                }
                innerTextField()
            }
        )
        Icon(
            imageVector = Icons.Filled.FilterList,
            contentDescription = "Filters",
            tint = EcoGreen,
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun EmptyMapResult(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = EcoGreen,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = LocalEcoStrings.current.sis4Text("No recycling points found", "Пункты не найдены", "Пункттер табылмады"),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = LocalEcoStrings.current.sis4Text("Try another category or query", "Измените категорию или запрос", "Санатты не сұрауды өзгертіңіз"),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun simpleFilterColors(): FilterChipColors {
    return FilterChipColors(
        selectedContainer = EcoGreen,
        unselectedContainer = Color.White,
        selectedContent = Color.White,
        unselectedContent = EcoGreen
    )
}

@Composable
private fun FilterChip(
    label: String,
    icon: ImageVector,
    selected: Boolean = false,
    colors: FilterChipColors,
    onClick: () -> Unit
) {
    val containerColor = if (selected) colors.selectedContainer else colors.unselectedContainer
    val contentColor = if (selected) colors.selectedContent else colors.unselectedContent

    Row(
        modifier = Modifier
            .height(48.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(containerColor)
            .selectable(selected = selected, onClick = onClick)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = label,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

private data class FilterChipColors(
    val selectedContainer: Color,
    val unselectedContainer: Color,
    val selectedContent: Color,
    val unselectedContent: Color
)

private fun WasteType.filterChipColors(): FilterChipColors {
    return when (this) {
        WasteType.Plastic -> FilterChipColors(
            selectedContainer = Color(0xFF0E7C46),
            unselectedContainer = Color(0xFFE8F7EE),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF0E7C46)
        )
        WasteType.Glass -> FilterChipColors(
            selectedContainer = Color(0xFF00897B),
            unselectedContainer = Color(0xFFE0F7F4),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF00897B)
        )
        WasteType.Paper -> FilterChipColors(
            selectedContainer = Color(0xFFB7791F),
            unselectedContainer = Color(0xFFFFF3D7),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF9A5E10)
        )
        WasteType.Batteries -> FilterChipColors(
            selectedContainer = Color(0xFF6D5BD0),
            unselectedContainer = Color(0xFFEDEBFF),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF5B4AC4)
        )
        WasteType.Electronics -> FilterChipColors(
            selectedContainer = Color(0xFF455A64),
            unselectedContainer = Color(0xFFECEFF1),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF455A64)
        )
        WasteType.Organic -> FilterChipColors(
            selectedContainer = Color(0xFF558B2F),
            unselectedContainer = Color(0xFFEAF4E1),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF558B2F)
        )
        WasteType.Metal -> FilterChipColors(
            selectedContainer = Color(0xFF607D8B),
            unselectedContainer = Color(0xFFE7EEF1),
            selectedContent = Color.White,
            unselectedContent = Color(0xFF607D8B)
        )
    }
}

@Composable
private fun RecyclingPointPreviewCard(
    point: RecyclingPoint,
    onClick: () -> Unit,
    onRouteClick: () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalEcoStrings.current

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(width = 46.dp, height = 4.dp)
                    .background(Color(0xFFD9DED8), RoundedCornerShape(4.dp))
                    .clickable { onExpandedChange(!expanded) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpandedChange(!expanded) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(Color(0xFFE8F5E5), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Recycling,
                        contentDescription = null,
                        tint = EcoGreen,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = point.name,
                        maxLines = 1, overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = point.openingHours,
                        color = Color(0xFF69726B),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp).clickable(onClick = onClick)
                        .background(EcoGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Open details",
                        tint = Color.White
                    )
                }
            }

            if (!expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = EcoGreen,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = strings.distanceAway(point.rating, point.distanceMeters),
                        color = Color(0xFF366B42),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                return@Column
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = EcoGreen,
                    modifier = Modifier.size(17.dp)
                )
                Text(
                    text = strings.distanceAway(point.rating, point.distanceMeters),
                    color = Color(0xFF366B42),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                InfoTile(
                    title = strings.acceptedItems,
                    value = point.acceptedWasteTypes.take(3).joinToString(", ") { strings.wasteTypeName(it) },
                    modifier = Modifier.weight(1f)
                )
                InfoTile(
                    title = strings.reward,
                    value = strings.points(point.rewardPoints),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MapCardActionButton(
                    label = strings.sis4Text("Route", "Маршрут", "Бағыт"),
                    icon = Icons.Filled.LocationOn,
                    onClick = onRouteClick,
                    modifier = Modifier.weight(1f)
                )
                MapCardActionButton(
                    label = strings.sis4Text("Details", "Подробнее", "Толығырақ"),
                    icon = Icons.Filled.ArrowForward,
                    onClick = onClick,
                    modifier = Modifier.weight(1f)
                )
            }


        }
    }
}

@Composable
private fun MapCardActionButton(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(EcoGreen)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun InfoTile(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(92.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(14.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            color = EcoGreen,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun StylizedMapBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        drawRect(
            brush = Brush.verticalGradient(
                listOf(Color(0xFFC9D9C2), Color(0xFFAEC5A7), Color(0xFFB9CDB1))
            )
        )
        drawMapGrid()
        drawMapRoads()
        drawCircle(
            color = Color(0xFF7FA273).copy(alpha = 0.22f),
            radius = size.minDimension * 0.36f,
            center = Offset(size.width * 0.18f, size.height * 0.40f)
        )
    }
}

private fun DrawScope.drawMapGrid() {
    val thin = Stroke(width = 1.dp.toPx())
    val color = Color.White.copy(alpha = 0.22f)
    var x = -size.width
    while (x < size.width * 2) {
        drawLine(
            color = color,
            start = Offset(x, 0f),
            end = Offset(x + size.width * 0.45f, size.height),
            strokeWidth = thin.width
        )
        x += 28.dp.toPx()
    }
    var y = 0f
    while (y < size.height) {
        drawLine(
            color = color,
            start = Offset(0f, y),
            end = Offset(size.width, y + 42.dp.toPx()),
            strokeWidth = thin.width
        )
        y += 32.dp.toPx()
    }
}

private fun DrawScope.drawMapRoads() {
    val majorRoad = Stroke(width = 5.dp.toPx())
    val minorRoad = Stroke(width = 2.dp.toPx())
    val roadColor = Color.White.copy(alpha = 0.72f)
    val minorRoadColor = Color.White.copy(alpha = 0.40f)

    val vertical = Path().apply {
        moveTo(size.width * 0.56f, 0f)
        cubicTo(size.width * 0.52f, size.height * 0.22f, size.width * 0.68f, size.height * 0.44f, size.width * 0.48f, size.height)
    }
    drawPath(vertical, roadColor, style = majorRoad)

    val horizontal = Path().apply {
        moveTo(0f, size.height * 0.58f)
        cubicTo(size.width * 0.22f, size.height * 0.47f, size.width * 0.56f, size.height * 0.64f, size.width, size.height * 0.48f)
    }
    drawPath(horizontal, roadColor, style = majorRoad)

    repeat(8) { index ->
        val offset = index * size.height / 8f
        val path = Path().apply {
            moveTo(0f, offset)
            cubicTo(size.width * 0.28f, offset + 40.dp.toPx(), size.width * 0.54f, offset - 24.dp.toPx(), size.width, offset + 20.dp.toPx())
        }
        drawPath(path, minorRoadColor, style = minorRoad)
    }
}


private fun createWasteMarkerIcon(
    context: Context,
    wasteType: WasteType,
    selected: Boolean
): BitmapDrawable {
    val width = if (selected) 74 else 62
    val height = if (selected) 84 else 72
    val centerX = width / 2f
    val circleRadius = if (selected) 25f else 21f
    val circleCenterY = circleRadius + 7f
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = AndroidCanvas(bitmap)
    val markerColor = wasteType.markerColor()

    val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = markerColor
        style = Paint.Style.FILL
    }
    val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = android.graphics.Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = if (selected) 6f else 4f
    }
    val symbolPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = android.graphics.Color.WHITE
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = if (selected) 25f else 21f
        isFakeBoldText = true
    }

    val pointer = AndroidPath().apply {
        moveTo(centerX - 13f, circleCenterY + circleRadius - 2f)
        lineTo(centerX + 13f, circleCenterY + circleRadius - 2f)
        lineTo(centerX, height - 5f)
        close()
    }

    canvas.drawPath(pointer, fillPaint)
    canvas.drawCircle(centerX, circleCenterY, circleRadius, fillPaint)
    canvas.drawCircle(centerX, circleCenterY, circleRadius, strokePaint)
    canvas.drawText(wasteType.markerLetter(), centerX, circleCenterY + 8f, symbolPaint)

    return BitmapDrawable(context.resources, bitmap)
}

private fun WasteType.markerColor(): Int {
    return when (this) {
        WasteType.Plastic -> android.graphics.Color.rgb(14, 124, 70)
        WasteType.Glass -> android.graphics.Color.rgb(0, 137, 123)
        WasteType.Paper -> android.graphics.Color.rgb(183, 121, 31)
        WasteType.Batteries -> android.graphics.Color.rgb(109, 91, 208)
        WasteType.Electronics -> android.graphics.Color.rgb(69, 90, 100)
        WasteType.Organic -> android.graphics.Color.rgb(85, 139, 47)
        WasteType.Metal -> android.graphics.Color.rgb(96, 125, 139)
    }
}

private fun WasteType.markerLetter(): String {
    return when (this) {
        WasteType.Plastic -> "P"
        WasteType.Glass -> "G"
        WasteType.Paper -> "Pa"
        WasteType.Batteries -> "B"
        WasteType.Electronics -> "E"
        WasteType.Organic -> "O"
        WasteType.Metal -> "M"
    }
}

@Preview(showBackground = true, name = "Map - Loading")
@Composable
private fun MapLoadingPreview() { EcoDalaTheme { MapScreen(MapUiState()) } }

@Preview(showBackground = true, name = "Map - Content")
@Composable
private fun MapContentPreview() { EcoDalaTheme { MapScreen(MapUiState(isLoading = false,
    allPoints = DummyEcoData.recyclingPoints, showList = true)) } }

@Preview(showBackground = true, name = "Map - Empty")
@Composable
private fun MapEmptyPreview() { EcoDalaTheme { MapScreen(MapUiState(isLoading = false)) } }

@Preview(showBackground = true, name = "Map - Error")
@Composable
private fun MapErrorPreview() { EcoDalaTheme { MapScreen(MapUiState(isLoading = false,
    errorMessage = "Demo error", demoErrorEnabled = true)) } }

@Preview(showBackground = true, name = "Map - Dark")
@Composable
private fun MapDarkPreview() { EcoDalaTheme(darkTheme = true) { MapScreen(MapUiState(isLoading = false,
    allPoints = DummyEcoData.recyclingPoints, showList = true)) } }
