package ru.knyazev.coursesapp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.knyazev.coursesapp.data.mapper.toModel
import ru.knyazev.coursesapp.data.mapper.toUi
import ru.knyazev.coursesapp.domain.model.CourseUI
import javax.inject.Inject


class CourseRepository @Inject constructor(
    private val service: CourseService,
    private val courseDao: CourseDao,
) {

    suspend fun getCoursesFromApi(): List<CourseUI>? {
        try {
            val courseCall = service.getCourses()
            val courseResponse = withContext(Dispatchers.IO) {
                courseCall.execute()
            }
            return courseResponse.body()?.courses?.map { it.toUi() }
        } catch (e: Exception) {
            Log.d("network, getCourses()", "${e.printStackTrace()}")
            return null
        }
    }

    suspend fun addCourseToCache(course: CourseUI) {
        courseDao.addCourse(course.toModel())
    }

    suspend fun getCoursesFromCache(): List<CourseUI> = courseDao.getCourses().map { it.toUi() }

    suspend fun deleteCourse(course: CourseUI) {
        courseDao.deleteCourse(course.toModel())
    }
}