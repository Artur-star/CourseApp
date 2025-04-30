package ru.knyazev.coursesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import ru.knyazev.coursesapp.presentation.screens.MainScreen
import ru.knyazev.coursesapp.presentation.screens.MainViewModel
import ru.knyazev.coursesapp.presentation.ui.theme.CoursesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

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
                        MainScreen(mainViewModel)
//                        val navController = rememberNavController()
//                        Login(navController)
//
//
//                        NavHost(
//                            navController = navController,
//                            startDestination = OnboardingScreenObj
//                        ) {
//                            composable<OnboardingScreenObj> {
//                                Onboarding(navController)
//                            }
//                            composable<LoginScreenObj> {
//                                Login(navController)
//                            }
//                            composable<MainScreenObj> {
//                                MainScreen(mainViewModel)
//                            }
//                        }
                    }
                }
            }
        }
    }
}