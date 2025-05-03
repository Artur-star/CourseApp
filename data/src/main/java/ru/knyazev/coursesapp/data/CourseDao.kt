package ru.knyazev.coursesapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.knyazev.coursesapp.data.model.Course

@Dao
interface CourseDao {

    @Query("SELECT * FROM course")
    suspend fun getCourses(): List<Course>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCourse(course: Course)

    @Delete
    suspend fun deleteCourse(course: Course)
}