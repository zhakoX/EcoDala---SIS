package kz.ecodala.model

data class RecyclingPoint(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openHours: String,
    val imageResId: Int,
    val acceptedTypes: List<String>,
    val description: String
)