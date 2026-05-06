package com.example.najmedinezahra.data.repository

import com.example.najmedinezahra.data.database.QuestionDao
import com.example.najmedinezahra.data.model.Difficulty
import com.example.najmedinezahra.data.model.Question
import com.example.najmedinezahra.data.model.QuizCategory
import kotlinx.coroutines.flow.Flow

class QuizRepository(private val questionDao: QuestionDao) {

    suspend fun getQuestionsByCategory(category: QuizCategory): List<Question> {
        return questionDao.getQuestionsByCategory(category.name)
    }

    suspend fun getQuestionsByCategoryAndDifficulty(
        category: QuizCategory,
        difficulty: Difficulty
    ): List<Question> {
        return questionDao.getQuestionsByCategoryAndDifficulty(
            category.name,
            difficulty.name
        )
    }
    
    suspend fun getQuestionById(questionId: Int): Question? {
        return questionDao.getQuestionById(questionId)
    }
    
    suspend fun getQuestionsByDifficulty(difficulty: Difficulty): List<Question> {
        return questionDao.getQuestionsByDifficulty(difficulty.name)
    }
    
    suspend fun getAllQuestions(): List<Question> {
        return questionDao.getAllQuestions()
    }
    
    suspend fun countQuestionsByCategory(category: QuizCategory): Int {
        return questionDao.countQuestionsByCategory(category.name)
    }
    
    suspend fun getTotalQuestionCount(): Int {
        return questionDao.getTotalQuestionCount()
    }
    
    fun getAllQuestionsFlow(): Flow<List<Question>> {
        return questionDao.getAllQuestionsFlow()
    }
    
    suspend fun getRandomQuestionFromCategory(category: QuizCategory): Question? {
        val questions = getQuestionsByCategory(category)
        return questions.randomOrNull()
    }
    
    suspend fun getRandomQuestions(
        category: QuizCategory,
        difficulty: Difficulty,
        count: Int
    ): List<Question> {
        val questions = getQuestionsByCategoryAndDifficulty(category, difficulty)
        return questions.shuffled().take(count)
    }
}
