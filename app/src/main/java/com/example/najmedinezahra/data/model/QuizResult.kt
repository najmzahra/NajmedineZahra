package com.example.najmedinezahra.data.model

data class QuizResult(
    val category: QuizCategory,
    val difficulty: Difficulty,
    val score: Int,
    val totalQuestions: Int,
    val percentage: Float,
    val timestamp: Long = System.currentTimeMillis()
) {
    val performanceMessage: String
        get() = when {
            percentage >= 90f -> "Excellent performance! You're a history expert!"
            percentage >= 80f -> "Great job! You know this topic very well!"
            percentage >= 70f -> "Good effort! Keep learning more!"
            percentage >= 60f -> "Not bad! You're on the right track!"
            percentage >= 50f -> "Keep practicing! You will improve!"
            else -> "Try again! You'll do better next time!"
        }

    val medal: String
        get() = when {
            percentage >= 90f -> "Gold"
            percentage >= 80f -> "Silver"
            percentage >= 70f -> "Bronze"
            else -> "Certificate"
        }
}
