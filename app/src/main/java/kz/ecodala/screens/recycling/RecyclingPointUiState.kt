package kz.ecodala.screens.recycling

import kz.ecodala.model.RecyclingPoint

/** The explicit status is the single source of truth for which recycling-point UI state to render. */
enum class RecyclingPointStatus {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR
}

data class RecyclingPointUiState(
    val status: RecyclingPointStatus = RecyclingPointStatus.LOADING,
    val recyclingPoint: RecyclingPoint? = null,
    val isFavorite: Boolean = false,
    val errorMessage: String? = null
)
