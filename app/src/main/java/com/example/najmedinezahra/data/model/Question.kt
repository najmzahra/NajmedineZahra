package com.example.najmedinezahra.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey val id: Int,
    val category: String,
    val title: String,
    val imageResId: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: Int,
    val difficulty: String,
    val keyFact: String
)

enum class QuizCategory {
    ROMAN_HERITAGE
}

enum class Difficulty {
    EASY,
    MEDIUM,
    HARD
}
