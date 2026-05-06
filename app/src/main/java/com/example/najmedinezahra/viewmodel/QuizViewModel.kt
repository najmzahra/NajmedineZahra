package com.example.najmedinezahra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.najmedinezahra.data.model.Difficulty
import com.example.najmedinezahra.data.model.GameState
import com.example.najmedinezahra.data.model.Question
import com.example.najmedinezahra.data.model.QuizCategory
import com.example.najmedinezahra.data.model.QuizResult
import com.example.najmedinezahra.data.repository.QuizRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: QuizRepository) : ViewModel() {
    
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
    
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()
    
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _quizResult = MutableStateFlow<QuizResult?>(null)
    val quizResult: StateFlow<QuizResult?> = _quizResult.asStateFlow()

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun initializeQuiz(category: QuizCategory, difficulty: Difficulty, enableTimer: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val loadedQuestions = repository.getRandomQuestions(category, difficulty, 10)
                
                if (loadedQuestions.isEmpty()) {
                    _error.value = "No questions found"
                    _isLoading.value = false
                    return@launch
                }

                _questions.value = loadedQuestions
                _gameState.value = GameState(
                    totalQuestions = loadedQuestions.size,
                    selectedCategory = category,
                    difficulty = difficulty,
                    enableTimer = enableTimer
                )

                loadNextQuestion()
                
                if (enableTimer) {
                    startTimer()
                }
                
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    private fun loadNextQuestion() {
        val currentIndex = _gameState.value.currentQuestionIndex
        if (currentIndex < _questions.value.size) {
            _currentQuestion.value = _questions.value[currentIndex]
        }
    }

    fun submitAnswer(selectedOptionIndex: Int) {
        val currentQuestion = _currentQuestion.value ?: return
        val currentState = _gameState.value

        val isCorrect = selectedOptionIndex == currentQuestion.correctAnswer
        val newScore = if (isCorrect) currentState.score + 10 else currentState.score

        val updatedAnswers = currentState.answers.toMutableList()
        updatedAnswers.add(selectedOptionIndex)

        _gameState.value = currentState.copy(
            score = newScore,
            answers = updatedAnswers
        )

        moveToNextQuestion()
    }

    private fun moveToNextQuestion() {
        val currentState = _gameState.value
        val nextIndex = currentState.currentQuestionIndex + 1

        if (nextIndex < currentState.totalQuestions) {
            _gameState.value = currentState.copy(
                currentQuestionIndex = nextIndex,
                timeRemaining = 15
            )
            loadNextQuestion()
        } else {
            endQuiz()
        }
    }

    fun skipQuestion() {
        val currentState = _gameState.value
        val updatedAnswers = currentState.answers.toMutableList()
        updatedAnswers.add(-1)  

        _gameState.value = currentState.copy(answers = updatedAnswers)
        moveToNextQuestion()
    }

    private fun startTimer() {
        if (_isTimerRunning.value) return

        _isTimerRunning.value = true
        viewModelScope.launch {
            while (_isTimerRunning.value && _gameState.value.timeRemaining > 0) {
                delay(1000)
                
                val currentState = _gameState.value
                if (currentState.isQuizComplete) {
                    _isTimerRunning.value = false
                    return@launch
                }

                _gameState.value = currentState.copy(
                    timeRemaining = currentState.timeRemaining - 1
                )

                if (currentState.timeRemaining - 1 == 0) {
                    submitAnswer(-1)  
                    if (!_gameState.value.isQuizComplete) {
                        delay(500)
                        startTimer()  
                    } else {
                        _isTimerRunning.value = false
                    }
                }
            }
        }
    }

    fun stopTimer() {
        _isTimerRunning.value = false
    }

    private fun endQuiz() {
        val state = _gameState.value
        val percentage = (state.score.toFloat() / (state.totalQuestions * 10)) * 100

        val result = QuizResult(
            category = state.selectedCategory ?: QuizCategory.ROMAN_HERITAGE,
            difficulty = state.difficulty,
            score = state.score,
            totalQuestions = state.totalQuestions,
            percentage = percentage
        )

        _quizResult.value = result
        _gameState.value = state.copy(isQuizComplete = true)
        stopTimer()
    }

    fun resetQuiz() {
        _gameState.value = GameState()
        _questions.value = emptyList()
        _currentQuestion.value = null
        _quizResult.value = null
        _error.value = null
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
