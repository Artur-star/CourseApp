package ru.knyazev.coursesapp.presentation.screens

import ru.knyazev.coursesapp.domain.model.CourseUI

data class MainState(
    val listCourses: List<CourseUI> = emptyList(),
) {
    companion object {
        val initState = MainState(
            listCourses = emptyList(),
        )
    }
}