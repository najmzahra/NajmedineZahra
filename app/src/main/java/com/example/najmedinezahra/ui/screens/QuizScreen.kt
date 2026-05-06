package com.example.najmedinezahra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.najmedinezahra.data.model.GameState
import com.example.najmedinezahra.data.model.Question
import com.example.najmedinezahra.ui.theme.*

@Composable
fun QuizScreen(
    gameState: GameState,
    currentQuestion: Question?,
    isLoading: Boolean,
    isTimerRunning: Boolean,
    onAnswerSelected: (Int) -> Unit,
    onSkipQuestion: () -> Unit
) {
    val selectedAnswer = remember { mutableStateOf<Int?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamWhite)
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MediterraneanBlue,
                    modifier = Modifier.size(60.dp)
                )
            }
        } else if (currentQuestion != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                QuizHeader(gameState = gameState)

                if (gameState.enableTimer) {
                    TimerDisplay(timeRemaining = gameState.timeRemaining)
                }

                QuestionCard(question = currentQuestion)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val options = listOf(
                        currentQuestion.optionA,
                        currentQuestion.optionB,
                        currentQuestion.optionC,
                        currentQuestion.optionD
                    )
                    
                    options.forEachIndexed { index, option ->
                        AnswerOptionButton(
                            optionIndex = index,
                            optionText = option,
                            isSelected = selectedAnswer.value == index,
                            onClick = {
                                selectedAnswer.value = index
                                onAnswerSelected(index)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        selectedAnswer.value = null
                        onSkipQuestion()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightSand,
                        contentColor = MediterraneanBlue
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Skip Question",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Serif
                    )
                }
            }
        }
    }
}

@Composable
fun QuizHeader(gameState: GameState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MediterraneanBlue, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Question ${gameState.questionNumber} of ${gameState.totalQuestions}",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = "Progress",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Score",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = "${gameState.score} pts",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    color = SuccessGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = { gameState.questionNumber.toFloat() / gameState.totalQuestions },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = SuccessGreen,
            trackColor = Color.White.copy(alpha = 0.3f),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
    }
}

@Composable
fun TimerDisplay(timeRemaining: Int) {
    val timerColor = when {
        timeRemaining > 10 -> SuccessGreen
        timeRemaining > 5 -> WarningOrange
        else -> ErrorRed
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(timerColor.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$timeRemaining seconds remaining",
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif,
            color = timerColor
        )
    }
}

@Composable
fun QuestionCard(question: Question) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = LightSand),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = question.title,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                color = MediterraneanBlue,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = question.keyFact,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun AnswerOptionButton(
    optionIndex: Int,
    optionText: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val optionLabel = listOf("A", "B", "C", "D")[optionIndex]
    val backgroundColor = if (isSelected) MediterraneanBlue else Color.White
    val textColor = if (isSelected) Color.White else MediterraneanBlue

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(textColor.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = optionLabel,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text = optionText,
                fontSize = 14.sp,
                color = textColor,
                fontFamily = FontFamily.Serif,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
