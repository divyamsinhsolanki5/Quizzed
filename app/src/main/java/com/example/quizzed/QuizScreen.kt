package com.example.quizzed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(navController: NavController, selectedDate: String) {
    
    val questions = QuizRepository.quizData[selectedDate] ?: QuizRepository.quizData["10-4-2026"]!!

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateListOf<Int?>().apply { repeat(questions.size) { add(null) } } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quizzed", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A237E))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            
            Column {
                Text(
                    text = "${currentQuestionIndex + 1}. ${questions[currentQuestionIndex].questionText}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))

                questions[currentQuestionIndex].options.forEachIndexed { index, option ->
                    val isSelected = userAnswers[currentQuestionIndex] == index
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(1.dp, if (isSelected) Color(0xFF1A237E) else Color.LightGray, RoundedCornerShape(4.dp))
                            .background(if (isSelected) Color(0xFFE3F2FD) else Color.Transparent)
                            .clickable { userAnswers[currentQuestionIndex] = index }
                            .padding(15.dp)
                    ) {
                        Text(text = option, fontSize = 16.sp, color = if (isSelected) Color(0xFF1A237E) else Color.Black)
                    }
                }
            }

        
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (currentQuestionIndex > 0) {
                    Button(
                        onClick = { currentQuestionIndex-- },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0), contentColor = Color.Black)
                    ) {
                        Text("Previous")
                    }
                }

                val isLast = currentQuestionIndex == questions.size - 1
                Button(
                    onClick = {
                        if (isLast) {
                        
                            finalQuestions = questions
                            finalUserAnswers = userAnswers.toMutableList()

                            val score = questions.indices.count { userAnswers[it] == questions[it].correctAnswer }
                            navController.navigate("result_screen/$score/${questions.size}")
                        } else {
                            currentQuestionIndex++
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = if (isLast) Color(0xFF4CAF50) else Color(0xFF1A237E))
                ) {
                    Text(if (isLast) "Submit" else "Next")
                }
            }
        }
    }
}

























