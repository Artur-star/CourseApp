package ru.knyazev.coursesapp.data

import retrofit2.Call
import retrofit2.http.GET
import ru.knyazev.coursesapp.data.model.CoursesResponse

const val GET_URL = "u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download"

interface CourseService {

    @GET(GET_URL)
    fun getCourses(): Call<CoursesResponse>
}