package ru.knyazev.coursesapp.presentation.screens.buttonMenu

import ru.knyazev.coursesapp.R

sealed class ButtonMenuItem(
    val route: String,
    val title: Int,
    val  iconId: Int
) {
    object HomeBut: ButtonMenuItem(
        route = "Home",
        title = R.string.main,
        iconId = R.drawable.ic_house
    )
    object FavoriteBut: ButtonMenuItem(
        route = "Favorites",
        title = R.string.favorite,
        iconId = R.drawable.ic_bookmark
    )
    object AccBut: ButtonMenuItem(
        route = "Account",
        title = R.string.account,
        iconId = R.drawable.ic_person
    )
}