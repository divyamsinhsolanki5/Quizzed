package com.example.quizzed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ResultScreen(navController: NavController, score: Int, total: Int) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.35f)
                .background(Color(0xFF1A237E)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_emoji_events_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(90.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Your Score : $score / $total", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }

        // રીવ્યુ 
        Column(modifier = Modifier.fillMaxWidth().weight(0.65f).padding(16.dp)) {
            Text(text = "Review Answers", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Divider(modifier = Modifier.padding(vertical = 10.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(finalQuestions) { index, question ->
                    val userIdx = finalUserAnswers.getOrNull(index)
                    val isCorrect = userIdx == question.correctAnswer

                    ResultDetailItem(
                        question = question.questionText,
                        userAnswer = if (userIdx != null) question.options[userIdx] else "Not Answered",
                        correctAnswer = question.options[question.correctAnswer],
                        isCorrect = isCorrect
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { navController.navigate("home") { popUpTo("home") { inclusive = true } } },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A237E))
            ) {
                Text("Back to Home", color = Color.White)
            }
        }
    }
}

@Composable
fun ResultDetailItem(question: String, userAnswer: String, correctAnswer: String, isCorrect: Boolean) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(text = "Q: $question", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Text(
            text = "Your Answer: $userAnswer",
            color = if (isCorrect) Color(0xFF4CAF50) else Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        if (!isCorrect) {
            Text(
                text = "Correct Answer: $correctAnswer",
                color = Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Divider(modifier = Modifier.padding(top = 10.dp), thickness = 0.5.dp, color = Color.LightGray)
    }
}
