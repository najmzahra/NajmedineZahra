package com.example.najmedinezahra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.najmedinezahra.data.model.Difficulty
import com.example.najmedinezahra.data.model.QuizCategory
import com.example.najmedinezahra.ui.theme.CreamWhite
import com.example.najmedinezahra.ui.theme.ErrorRed
import com.example.najmedinezahra.ui.theme.LightSand
import com.example.najmedinezahra.ui.theme.MediterraneanBlue
import com.example.najmedinezahra.ui.theme.SuccessGreen
import com.example.najmedinezahra.ui.theme.WarningOrange

@Composable
fun DifficultySettingsScreen(
    category: QuizCategory,
    onStartQuiz: (Difficulty, Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    val selectedDifficulty = remember { mutableStateOf(Difficulty.EASY) }
    val enableTimer = remember { mutableStateOf(true) }

    val categoryName = when (category) {
        QuizCategory.ROMAN_HERITAGE -> "Roman Heritage"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CreamWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = MediterraneanBlue,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Text(
                    text = "Quiz Settings",
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Serif,
                    color = MediterraneanBlue,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MediterraneanBlue, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = categoryName,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Select Difficulty Level",
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                color = MediterraneanBlue
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                DifficultyOption(
                    difficulty = Difficulty.EASY,
                    label = "Easy - Tourist sites & general knowledge",
                    isSelected = selectedDifficulty.value == Difficulty.EASY,
                    color = SuccessGreen,
                    onClick = { selectedDifficulty.value = Difficulty.EASY }
                )

                Spacer(modifier = Modifier.height(12.dp))

                DifficultyOption(
                    difficulty = Difficulty.MEDIUM,
                    label = "Medium - Mixed content, requires study",
                    isSelected = selectedDifficulty.value == Difficulty.MEDIUM,
                    color = WarningOrange,
                    onClick = { selectedDifficulty.value = Difficulty.MEDIUM }
                )

                Spacer(modifier = Modifier.height(12.dp))

                DifficultyOption(
                    difficulty = Difficulty.HARD,
                    label = "Hard - Archaeological details & specialty",
                    isSelected = selectedDifficulty.value == Difficulty.HARD,
                    color = ErrorRed,
                    onClick = { selectedDifficulty.value = Difficulty.HARD }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightSand, shape = RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = enableTimer.value,
                    onCheckedChange = { enableTimer.value = it },
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Enable 15-second Timer",
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        color = MediterraneanBlue
                    )
                    Text(
                        text = "Auto-submit after time expires",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { onStartQuiz(selectedDifficulty.value, enableTimer.value) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MediterraneanBlue),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Start Quiz",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DifficultyOption(
    difficulty: Difficulty,
    label: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = color,
                unselectedColor = color.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            color = MediterraneanBlue
        )
    }
}
