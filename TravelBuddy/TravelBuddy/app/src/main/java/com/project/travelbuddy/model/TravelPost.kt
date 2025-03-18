package com.project.travelbuddy.model

data class TravelPost(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val location: String = "",
    val authorId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

