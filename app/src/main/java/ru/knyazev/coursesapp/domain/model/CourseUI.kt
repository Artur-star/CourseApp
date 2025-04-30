package ru.knyazev.coursesapp.domain.model

data class CourseUI(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    var hasLike: Boolean,
    val publishDate: String,
) {
    companion object {
        val Default = CourseUI(
            id = 0,
            title = "",
            text = "",
            price = "",
            startDate = "",
            rate = "",
            hasLike = false,
            publishDate = ""
        )
    }
}