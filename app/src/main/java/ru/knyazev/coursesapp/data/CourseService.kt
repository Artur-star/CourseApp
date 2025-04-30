package ru.knyazev.coursesapp.data

import retrofit2.Call
import retrofit2.http.GET
import ru.knyazev.coursesapp.data.model.CoursesResponse
import ru.knyazev.coursesapp.utils.Constants

interface CourseService {

    @GET(Constants.GET_URL)
    fun getCourses(): Call<CoursesResponse>
}