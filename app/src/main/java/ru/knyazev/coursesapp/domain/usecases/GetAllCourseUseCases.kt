package ru.knyazev.coursesapp.domain.usecases

import ru.knyazev.coursesapp.data.CourseRepository
import javax.inject.Inject

class GetAllCourseUseCases @Inject constructor(
    private val courseRepository: CourseRepository,
) {
    suspend fun invoke() = courseRepository.getCoursesFromApi()
}