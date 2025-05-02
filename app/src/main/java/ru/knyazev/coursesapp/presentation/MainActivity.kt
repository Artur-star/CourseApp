package ru.knyazev.coursesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.knyazev.coursesapp.presentation.navigation.Destination
import ru.knyazev.coursesapp.presentation.screens.Login
import ru.knyazev.coursesapp.presentation.screens.MainScreen
import ru.knyazev.coursesapp.presentation.screens.MainViewModel
import ru.knyazev.coursesapp.presentation.screens.Onboarding
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
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        val navController = rememberNavController()
                        AppNavigation(mainViewModel, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(mainViewModel: MainViewModel, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Destination.Onboarding.route
    ) {
        composable(route = Destination.Onboarding.route) {
            Onboarding(onClick = { navController.navigate(Destination.Login.route) })
        }
        composable(route = Destination.Login.route) {
            Login(onClick = { navController.navigate(Destination.Main.route) })
        }
        composable(route = Destination.Main.route) {
            MainScreen(mainViewModel)
        }
    }
}