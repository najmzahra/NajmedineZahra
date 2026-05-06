package com.example.najmedinezahra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.najmedinezahra.data.database.AppDatabase
import com.example.najmedinezahra.data.model.QuizCategory
import com.example.najmedinezahra.data.repository.QuizRepository
import com.example.najmedinezahra.navigation.Route
import com.example.najmedinezahra.ui.screens.DifficultySettingsScreen
import com.example.najmedinezahra.ui.screens.MainMenuScreen
import com.example.najmedinezahra.ui.screens.QuizScreen
import com.example.najmedinezahra.ui.screens.ResultsScreen
import com.example.najmedinezahra.ui.screens.SplashScreen
import com.example.najmedinezahra.ui.theme.NajmedineZahraTheme
import com.example.najmedinezahra.viewmodel.QuizViewModel
import com.example.najmedinezahra.viewmodel.QuizViewModelFactory
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: QuizViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(this)
        val repository = QuizRepository(database.questionDao())
        val factory = QuizViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(QuizViewModel::class.java)

        setContent {
            NajmedineZahraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopTimer()
    }
}

@Composable
fun AppNavigation(viewModel: QuizViewModel) {
    val navController = rememberNavController()
    val gameState by viewModel.gameState.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val quizResult by viewModel.quizResult.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isTimerRunning by viewModel.isTimerRunning.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Route.Splash,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Route.Splash> {
            SplashScreen()
            LaunchedEffect(Unit) {
                delay(2000)
                navController.navigate(Route.MainMenu) {
                    popUpTo(Route.Splash) { inclusive = true }
                }
            }
        }

        composable<Route.MainMenu> {
            MainMenuScreen(
                onPlayClick = { navController.navigate(Route.DifficultySettings(QuizCategory.ROMAN_HERITAGE.name)) },
                onRulesClick = { /* TODO */ },
                onStatsClick = { /* TODO */ }
            )
        }

        composable<Route.DifficultySettings> {
            DifficultySettingsScreen(
                category = QuizCategory.ROMAN_HERITAGE,
                onStartQuiz = { difficulty, enableTimer ->
                    viewModel.initializeQuiz(
                        category = QuizCategory.ROMAN_HERITAGE,
                        difficulty = difficulty,
                        enableTimer = enableTimer
                    )
                    navController.navigate(Route.Quiz(QuizCategory.ROMAN_HERITAGE.name, difficulty.name))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<Route.Quiz> {
            QuizScreen(
                gameState = gameState,
                currentQuestion = currentQuestion,
                isLoading = isLoading,
                isTimerRunning = isTimerRunning,
                onAnswerSelected = { selectedIndex ->
                    viewModel.submitAnswer(selectedIndex)
                    if (gameState.isQuizComplete) {
                        navController.navigate(Route.Results) {
                            popUpTo(Route.MainMenu) { inclusive = false }
                        }
                    }
                },
                onSkipQuestion = {
                    viewModel.skipQuestion()
                    if (gameState.isQuizComplete) {
                        navController.navigate(Route.Results) {
                            popUpTo(Route.MainMenu) { inclusive = false }
                        }
                    }
                }
            )
        }

        composable<Route.Results> {
            ResultsScreen(
                quizResult = quizResult,
                onPlayAgain = {
                    viewModel.resetQuiz()
                    navController.navigate(Route.DifficultySettings(QuizCategory.ROMAN_HERITAGE.name)) {
                        popUpTo(Route.MainMenu) { inclusive = false }
                    }
                },
                onMainMenu = {
                    viewModel.resetQuiz()
                    navController.navigate(Route.MainMenu) {
                        popUpTo(Route.MainMenu) { inclusive = true }
                    }
                }
            )
        }
    }
}
