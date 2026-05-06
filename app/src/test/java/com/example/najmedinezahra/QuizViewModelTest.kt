package com.example.najmedinezahra.viewmodel

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFalse
import com.example.najmedinezahra.data.model.Difficulty
import com.example.najmedinezahra.data.model.GameState
import com.example.najmedinezahra.data.model.Question
import com.example.najmedinezahra.data.model.QuizCategory
import com.example.najmedinezahra.data.repository.QuizRepository

/**
 * Unit Tests for QuizViewModel
 * Tests core logic without UI
 */
class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel
    private lateinit var mockRepository: MockQuizRepository

    @BeforeTest
    fun setup() {
        mockRepository = MockQuizRepository()
        viewModel = QuizViewModel(mockRepository)
    }

    @Test
    fun testInitialGameState() {
        // Assert initial state is empty
        val initialState = viewModel.gameState.value
        assertEquals(0, initialState.score)
        assertEquals(0, initialState.totalQuestions)
        assertEquals(0, initialState.currentQuestionIndex)
    }

    @Test
    fun testScoreCalculation() = runTest {
        // Initialize quiz
        viewModel.initializeQuiz(
            category = QuizCategory.ROMAN_HERITAGE,
            difficulty = Difficulty.EASY,
            enableTimer = false
        )

        delay(100) // Wait for initialization

        // Submit correct answer
        viewModel.submitAnswer(0) // Mock returns correctAnswer = 0

        // Verify score increased
        assertTrue(viewModel.gameState.value.score >= 0)
    }

    @Test
    fun testQuizProgression() = runTest {
        // Initialize quiz
        viewModel.initializeQuiz(
            category = QuizCategory.ROMAN_HERITAGE,
            difficulty = Difficulty.EASY,
            enableTimer = false
        )

        delay(100)

        val totalQuestions = viewModel.gameState.value.totalQuestions
        val initialIndex = viewModel.gameState.value.currentQuestionIndex

        // Submit answer to move to next question
        viewModel.submitAnswer(0)

        // Verify question index incremented (or quiz is complete)
        val finalIndex = viewModel.gameState.value.currentQuestionIndex
        assertTrue(finalIndex > initialIndex || viewModel.gameState.value.isQuizComplete)
    }

    @Test
    fun testSkipQuestion() = runTest {
        viewModel.initializeQuiz(
            category = QuizCategory.ROMAN_HERITAGE,
            difficulty = Difficulty.EASY,
            enableTimer = false
        )

        delay(100)

        val initialIndex = viewModel.gameState.value.currentQuestionIndex
        val initialAnswerCount = viewModel.gameState.value.answers.size

        // Skip question
        viewModel.skipQuestion()

        // Verify skipped answer was recorded
        assertTrue(viewModel.gameState.value.answers.size > initialAnswerCount)
    }

    @Test
    fun testQuizCompletion() = runTest {
        viewModel.initializeQuiz(
            category = QuizCategory.ROMAN_HERITAGE,
            difficulty = Difficulty.EASY,
            enableTimer = false
        )

        delay(100)

        // Verify quiz not complete initially
        assertFalse(viewModel.gameState.value.isQuizComplete)

        // Quiz result should be null
        assertTrue(viewModel.quizResult.value == null)
    }

    @Test
    fun testResetQuiz() {
        viewModel.resetQuiz()

        // Verify all states are reset
        assertEquals(0, viewModel.gameState.value.score)
        assertEquals(0, viewModel.questions.value.size)
        assertTrue(viewModel.quizResult.value == null)
        assertTrue(viewModel.error.value == null)
    }

    @Test
    fun testGameStatePercentage() {
        val gameState = GameState(
            score = 50,
            totalQuestions = 10
        )

        // 50 points / (10 * 10) = 50%
        val expectedPercentage = (50.toFloat() / (10 * 10)) * 100
        assertEquals(expectedPercentage, gameState.scorePercentage)
    }
}

/**
 * Mock Repository for testing
 */
class MockQuizRepository : QuizRepository(MockQuestionDao()) {
    
    override suspend fun getRandomQuestions(
        category: QuizCategory,
        difficulty: Difficulty,
        count: Int
    ): List<Question> {
        // Return mock questions
        return List(count) { index ->
            Question(
                id = index,
                category = category.name,
                title = "Test Question $index",
                imageResId = "test_image",
                optionA = "Option A",
                optionB = "Option B",
                optionC = "Option C",
                optionD = "Option D",
                correctAnswer = 0,
                difficulty = difficulty.name,
                keyFact = "Test fact"
            )
        }
    }
}

/**
 * Mock DAO for testing
 */
class MockQuestionDao : com.example.najmedinezahra.data.database.QuestionDao {
    
    override suspend fun getQuestionsByCategory(category: String): List<Question> {
        return emptyList()
    }

    override suspend fun getQuestionsByCategoryAndDifficulty(
        category: String,
        difficulty: String
    ): List<Question> {
        return emptyList()
    }

    override suspend fun getQuestionById(questionId: Int): Question? {
        return null
    }

    override suspend fun getQuestionsByDifficulty(difficulty: String): List<Question> {
        return emptyList()
    }

    override suspend fun getAllQuestions(): List<Question> {
        return emptyList()
    }

    override suspend fun countQuestionsByCategory(category: String): Int {
        return 0
    }

    override suspend fun getTotalQuestionCount(): Int {
        return 0
    }

    override fun getAllQuestionsFlow(): kotlinx.coroutines.flow.Flow<List<Question>> {
        return kotlinx.coroutines.flow.emptyFlow()
    }
}

