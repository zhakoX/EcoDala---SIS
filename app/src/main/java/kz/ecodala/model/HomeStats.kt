package kz.ecodala.model

data class HomeStats(
    val points: Int,
    val level: Int,
    val globalRank: Int,
    val treeProgress: Float,
    val nextLevel: Int
)