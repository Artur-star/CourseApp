package ru.knyazev.coursesapp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.knyazev.coursesapp.data.model.Course
import javax.inject.Inject

class CourseRepository @Inject constructor(
    private val service: CourseService,
    private val courseDao: CourseDao,
) {

    suspend fun getCoursesFromApi(): List<Course>? {
        try {
            val courseCall = service.getCourses()
            val courseResponse = withContext(Dispatchers.IO) {
                courseCall.execute()
            }
            return courseResponse.body()?.courses
        } catch (e: Exception) {
            Log.d("network, getCourses()", "${e.printStackTrace()}")
            return null
        }
    }

    suspend fun addCoursesToCache(course: Course) {
        courseDao.addCourse(course)
    }

    suspend fun getCoursesFromCache(): List<Course> = courseDao.getCourses()

    suspend fun updateCourseToCache(course: Course) {
        course.hasLike = !course.hasLike
        courseDao.updateCourse(course)
    }
}