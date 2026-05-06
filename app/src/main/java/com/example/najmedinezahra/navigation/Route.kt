package com.example.najmedinezahra.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Splash : Route()

    @Serializable
    data object MainMenu : Route()

    @Serializable
    data class DifficultySettings(val category: String) : Route()

    @Serializable
    data class Quiz(val category: String, val difficulty: String) : Route()

    @Serializable
    data object Results : Route()
}
