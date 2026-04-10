package com.example.quizzed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


data class QuizCardData(
    val title: String,
    val date: String,
    val iconRes: Int,
    val bgColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()


    val quizCards = listOf(
        QuizCardData("School", "11-4-2026", R.drawable.baseline_school_24, Color(0xFF4FA8D1)),
        QuizCardData("Maths", "12-4-2026", R.drawable.outline_calculate_24, Color(0xFFD9828E)),
        QuizCardData("Writing", "13-4-2026", R.drawable.outline_edit_24, Color(0xFFD67F6A)),
        QuizCardData("Reading", "14-4-2026", R.drawable.outline_menu_book_24, Color(0xFFEB9C56)),
        QuizCardData("Alert", "15-4-2026", R.drawable.outline_notifications_24, Color(0xFF8E99C5))
    )

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("CANCEL") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(modifier = Modifier.fillMaxHeight().width(280.dp).background(Color.White)) {
                    // Drawer Header ("Q" Logo)
                    Column(
                        modifier = Modifier.fillMaxWidth().height(170.dp).background(Color(0xFF1A237E)).padding(20.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(modifier = Modifier.size(60.dp), shape = CircleShape, color = Color(0xFF03A9F4)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("Q", color = Color.White, fontSize = 35.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Quiz YourSelf Daily", color = Color.White, fontSize = 16.sp)
                    }
                    // Drawer Items
                    NavigationDrawerItem(label = { Text("Follow Us") }, selected = false, onClick = {})
                    NavigationDrawerItem(label = { Text("Profile") }, selected = false, onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate("profile")
                    })
                    NavigationDrawerItem(label = { Text("Rate Us") }, selected = false, onClick = {})
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Quizzed", color = Color.White, fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A237E))
                )
            },
            // Floating Action Button (Calendar)
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDatePicker = true },
                    containerColor = Color(0xFF26C6DA),
                    shape = CircleShape
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_date_range_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(padding).fillMaxSize().padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(quizCards) { data ->
                    QuizItemCard(data) {
                        navController.navigate("quiz_screen/${data.date}")
                    }
                }
            }
        }
    }
}

@Composable
fun QuizItemCard(data: QuizCardData, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(170.dp).clickable { onClick() },
        shape = RoundedCornerShape(0.dp), // Rectangular cards
        colors = CardDefaults.cardColors(containerColor = data.bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = data.iconRes),
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                colorFilter = ColorFilter.tint(Color.White) // White icons
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = data.date, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}
