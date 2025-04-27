package ru.knyazev.coursesapp.data

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.knyazev.coursesapp.data.model.Course
import javax.inject.Inject

class CourseRepository @Inject constructor(private val service: CourseService) {

    suspend fun getCoursesFromApi(): List<Course>? {
        try {
            val courseCall = service.getCourses()
            val courseResponse = withContext(Dispatchers.IO) {
                courseCall.execute()
            }
            return courseResponse.body()
        } catch (e: Exception) {
            Log.d("network, getCourses()", "${e.printStackTrace()}")
            return null
        }
    }
}