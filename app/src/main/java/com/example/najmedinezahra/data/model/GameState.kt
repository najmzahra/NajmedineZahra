package com.example.najmedinezahra.data.model

data class GameState(
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val currentQuestionIndex: Int = 0,
    val timeRemaining: Int = 15,
    val difficulty: Difficulty = Difficulty.EASY,
    val selectedCategory: QuizCategory? = null,
    val enableTimer: Boolean = true,
    val answers: List<Int> = emptyList(),
    val isQuizComplete: Boolean = false
) {
    val questionNumber: Int
        get() = currentQuestionIndex + 1

    val scorePercentage: Float
        get() = if (totalQuestions > 0) {
            (score.toFloat() / (totalQuestions * 10)) * 100
        } else {
            0f
        }
}
