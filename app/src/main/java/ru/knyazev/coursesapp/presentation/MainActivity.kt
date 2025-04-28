package ru.knyazev.coursesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.knyazev.coursesapp.presentation.data.LoginScreenObj
import ru.knyazev.coursesapp.presentation.data.MainScreenObj
import ru.knyazev.coursesapp.presentation.data.OnboardingScreenObj
import ru.knyazev.coursesapp.presentation.screens.Login
import ru.knyazev.coursesapp.presentation.screens.MainScreen
import ru.knyazev.coursesapp.presentation.screens.Onboarding
import ru.knyazev.coursesapp.presentation.ui.theme.CoursesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoursesAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = OnboardingScreenObj
                        ) {
                            composable<OnboardingScreenObj> {
                                Onboarding(navController)
                            }
                            composable<LoginScreenObj> {
                                Login(navController)
                            }
                            composable<MainScreenObj> {
                                MainScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}