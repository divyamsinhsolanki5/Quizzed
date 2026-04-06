package com.example.quizzed

import LoginScreen
import ProfileScreen
import SignUpScreen
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        setContent {
            val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
            val startDest = if (auth.currentUser !=null)"home"
            else "welcome"
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = startDest
            ) {
                composable("welcome") {
                    WelcomeScreen(navController)
                }
                composable("login") {
                    LoginScreen(navController)
                }
                composable("signup") {
                    SignUpScreen(navController)
                }
                composable("home") {
                    HomeScreen(navController)
                }

                composable(route = "quiz_screen/{date}") { backStackEntry ->

                    val selectedDate = backStackEntry.arguments?.getString("date") ?: "10-4-2026"

                    QuizScreen(navController = navController, selectedDate = selectedDate)
                }
                // quiz_screen
                composable(
                    route = "result_screen/{score}/{total}",
                    arguments = listOf(
                        navArgument("score") { type = NavType.IntType },
                        navArgument("total") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val score = backStackEntry.arguments?.getInt("score") ?: 0
                    val total = backStackEntry.arguments?.getInt("total") ?: 0

                    ResultScreen(navController = navController, score = score, total = total)
                }
                composable("profile") {
                    ProfileScreen(navController = navController)
                }
            }
        }
    }
                private fun askNotificationPermission() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                            PackageManager.PERMISSION_GRANTED
                        ) {
                            val requestPermissionLauncher = registerForActivityResult(
                                ActivityResultContracts.RequestPermission()
                            ) { isGranted: Boolean ->

                            }
                            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

            }
        }
    }
}






