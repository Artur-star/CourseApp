package ru.knyazev.coursesapp.data.mapper

import ru.knyazev.coursesapp.data.model.Course
import ru.knyazev.coursesapp.domain.model.CourseUI

fun Course.toUi(): CourseUI {
    return CourseUI(
        id, title, text, price, rate, startDate, hasLike, publishDate
    )
}

fun CourseUI.toModel(): Course {
    return Course(
        id, title, text, price, rate, startDate, hasLike, publishDate
    )
}