package kz.ecodala.model

data class User(
    val id: Int,
    val name: String,
    val faculty: String,
    val points: Int,
    val avatarResId: Int
)