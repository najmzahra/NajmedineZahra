package com.example.najmedinezahra.data.database

import androidx.room.Dao
import androidx.room.Query
import com.example.najmedinezahra.data.model.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY id ASC")
    suspend fun getQuestionsByCategory(category: String): List<Question>

    @Query("SELECT * FROM questions WHERE category = :category AND difficulty = :difficulty ORDER BY id ASC")
    suspend fun getQuestionsByCategoryAndDifficulty(
        category: String,
        difficulty: String
    ): List<Question>

    @Query("SELECT * FROM questions WHERE id = :questionId")
    suspend fun getQuestionById(questionId: Int): Question?

    @Query("SELECT * FROM questions WHERE difficulty = :difficulty ORDER BY id ASC")
    suspend fun getQuestionsByDifficulty(difficulty: String): List<Question>

    @Query("SELECT * FROM questions ORDER BY category, difficulty, id ASC")
    suspend fun getAllQuestions(): List<Question>

    @Query("SELECT COUNT(*) FROM questions WHERE category = :category")
    suspend fun countQuestionsByCategory(category: String): Int

    @Query("SELECT COUNT(*) FROM questions")
    suspend fun getTotalQuestionCount(): Int

    @Query("SELECT * FROM questions ORDER BY id ASC")
    fun getAllQuestionsFlow(): Flow<List<Question>>
}
