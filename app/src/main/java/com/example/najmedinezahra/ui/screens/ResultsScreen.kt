package com.example.najmedinezahra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.najmedinezahra.data.model.QuizResult
import com.example.najmedinezahra.ui.theme.*

@Composable
fun ResultsScreen(
    quizResult: QuizResult?,
    onPlayAgain: () -> Unit,
    onMainMenu: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamWhite)
    ) {
        if (quizResult == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MediterraneanBlue)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Quiz Completed",
                    fontSize = 28.sp,
                    fontFamily = FontFamily.Serif,
                    color = MediterraneanBlue,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            color = getMedalColor(quizResult.percentage).copy(alpha = 0.1f),
                            shape = RoundedCornerShape(60.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = quizResult.medal,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = getMedalColor(quizResult.percentage)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                ScoreCard(quizResult)

                Spacer(modifier = Modifier.height(24.dp))

                PerformanceCard(quizResult)

                Spacer(modifier = Modifier.height(24.dp))

                DetailsCard(quizResult)

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onPlayAgain,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Play Again",
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = onMainMenu,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MediterraneanBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Main Menu",
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Serif,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreCard(quizResult: QuizResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LightSand),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "${quizResult.percentage.toInt()}%",
                fontSize = 56.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = getMedalColor(quizResult.percentage)
            )

            Text(
                text = "${quizResult.score} / ${quizResult.totalQuestions * 10} points",
                fontSize = 16.sp,
                fontFamily = FontFamily.Serif,
                color = MediterraneanBlue
            )

            Text(
                text = "${quizResult.score / 10} correct answers",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun PerformanceCard(quizResult: QuizResult) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                getMedalColor(quizResult.percentage).copy(alpha = 0.2f),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = quizResult.performanceMessage,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = getMedalColor(quizResult.percentage),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DetailsCard(quizResult: QuizResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DetailRow(label = "Difficulty", value = quizResult.difficulty.name)
            DetailRow(label = "Total Questions", value = quizResult.totalQuestions.toString())
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontFamily = FontFamily.Serif
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = MediterraneanBlue,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold
        )
    }
}

fun getMedalColor(percentage: Float): Color {
    return when {
        percentage >= 90f -> SuccessGreen
        percentage >= 70f -> WarningOrange
        else -> ErrorRed
    }
}
