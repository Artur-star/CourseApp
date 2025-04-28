package ru.knyazev.coursesapp.presentation.screens.buttonMenu

import ru.knyazev.coursesapp.R

sealed class ButtonMenuItem(
    val route: String,
    val title: Int,
    val  iconId: Int
) {
    object MainBut: ButtonMenuItem(
        route = "",
        title = R.string.main,
        iconId = R.drawable.ic_house
    )
    object FavoriteBut: ButtonMenuItem(
        route = "",
        title = R.string.favorite,
        iconId = R.drawable.ic_bookmark
    )
    object AccBut: ButtonMenuItem(
        route = "",
        title = R.string.account,
        iconId = R.drawable.ic_person
    )
}