package ru.knyazev.coursesapp.presentation.navigation

sealed class Destination(val route: String) {
    data object Login : Destination(route = "Login")
    data object Onboarding : Destination(route = "Onboarding")
    data object Main : Destination(route = "Main")
}