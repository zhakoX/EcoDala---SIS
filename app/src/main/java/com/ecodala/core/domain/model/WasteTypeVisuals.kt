package com.ecodala.core.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile

/**
 * Centralized visual specification for [WasteType].
 * Consolidates color, icon, and background values used across screens.
 */
object WasteTypeVisuals {

    fun markerColor(wasteType: WasteType): Int = when (wasteType) {
        WasteType.Plastic -> android.graphics.Color.rgb(14, 124, 70)
        WasteType.Glass -> android.graphics.Color.rgb(0, 137, 123)
        WasteType.Paper -> android.graphics.Color.rgb(183, 121, 31)
        WasteType.Batteries -> android.graphics.Color.rgb(109, 91, 208)
        WasteType.Electronics -> android.graphics.Color.rgb(69, 90, 100)
        WasteType.Organic -> android.graphics.Color.rgb(85, 139, 47)
        WasteType.Metal -> android.graphics.Color.rgb(96, 125, 139)
    }

    fun markerLetter(wasteType: WasteType): String = when (wasteType) {
        WasteType.Plastic -> "P"
        WasteType.Glass -> "G"
        WasteType.Paper -> "Pa"
        WasteType.Batteries -> "B"
        WasteType.Electronics -> "E"
        WasteType.Organic -> "O"
        WasteType.Metal -> "M"
    }

    fun achievementBackground(wasteType: WasteType): Color = when (wasteType) {
        WasteType.Plastic -> Color(0xFFE6F7F0)
        WasteType.Glass -> Color(0xFFEFF5F0)
        WasteType.Paper -> Color(0xFFFFF8E1)
        WasteType.Batteries -> Color(0xFFF3E0F5)
        WasteType.Electronics -> Color(0xFFECEFF1)
        WasteType.Organic -> Color(0xFFE8F5E9)
        WasteType.Metal -> Color(0xFFECEFF1)
    }

    fun achievementTint(wasteType: WasteType): Color = when (wasteType) {
        WasteType.Plastic -> Color(0xFF0F8C65)
        WasteType.Glass -> Color(0xFF00897B)
        WasteType.Paper -> Color(0xFF9A5E10)
        WasteType.Batteries -> Color(0xFF5B4AC4)
        WasteType.Electronics -> Color(0xFF455A64)
        WasteType.Organic -> Color(0xFF558B2F)
        WasteType.Metal -> Color(0xFF607D8B)
    }

    fun achievementIcon(wasteType: WasteType): ImageVector = when (wasteType) {
        WasteType.Plastic -> Icons.Filled.Recycling
        WasteType.Glass -> Icons.Filled.WineBar
        WasteType.Paper -> Icons.AutoMirrored.Filled.InsertDriveFile
        WasteType.Batteries -> Icons.Filled.BatteryChargingFull
        WasteType.Electronics -> Icons.Filled.Spa
        WasteType.Organic -> Icons.Filled.LocalFlorist
        WasteType.Metal -> Icons.Filled.Build
    }

    data class FilterChipColors(
        val selectedContainer: Color,
        val unselectedContainer: Color,
        val selectedContent: Color,
        val unselectedContent: Color
    )

    fun filterChipColors(wasteType: WasteType): FilterChipColors = when (wasteType) {
        WasteType.Plastic -> FilterChipColors(Color(0xFF0E7C46), Color(0xFFE8F7EE), Color.White, Color(0xFF0E7C46))
        WasteType.Glass -> FilterChipColors(Color(0xFF00897B), Color(0xFFE0F7F4), Color.White, Color(0xFF00897B))
        WasteType.Paper -> FilterChipColors(Color(0xFFB7791F), Color(0xFFFFF3D7), Color.White, Color(0xFF9A5E10))
        WasteType.Batteries -> FilterChipColors(Color(0xFF6D5BD0), Color(0xFFEDEBFF), Color.White, Color(0xFF5B4AC4))
        WasteType.Electronics -> FilterChipColors(Color(0xFF455A64), Color(0xFFECEFF1), Color.White, Color(0xFF455A64))
        WasteType.Organic -> FilterChipColors(Color(0xFF558B2F), Color(0xFFEAF4E1), Color.White, Color(0xFF558B2F))
        WasteType.Metal -> FilterChipColors(Color(0xFF607D8B), Color(0xFFE7EEF1), Color.White, Color(0xFF607D8B))
    }
}