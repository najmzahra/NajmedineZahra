package com.example.najmedinezahra.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.najmedinezahra.ui.theme.MediterraneanBlue

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MediterraneanBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "H",
                    fontSize = 48.sp,
                    color = MediterraneanBlue,
                    fontFamily = FontFamily.Serif
                )
            }

            Spacer(modifier = Modifier.size(30.dp))

            Text(
                text = "Tunisia Heritage Quest",
                fontSize = 32.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = "Discover & Learn",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(40.dp))

            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit,
    onRulesClick: () -> Unit,
    onStatsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MediterraneanBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tunisia Heritage Quest",
                fontSize = 28.sp,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )

            Spacer(modifier = Modifier.size(60.dp))

            MenuButton(
                text = "Play Game",
                onClick = onPlayClick
            )

            Spacer(modifier = Modifier.size(20.dp))

            MenuButton(
                text = "How to Play",
                onClick = onRulesClick
            )

            Spacer(modifier = Modifier.size(20.dp))

            MenuButton(
                text = "Statistics",
                onClick = onStatsClick
            )
        }
    }
}

@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(width = 200.dp, height = 50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = MediterraneanBlue
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = FontFamily.Serif
        )
    }
}
