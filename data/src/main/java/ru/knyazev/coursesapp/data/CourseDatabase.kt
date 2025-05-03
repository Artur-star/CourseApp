package ru.knyazev.coursesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.knyazev.coursesapp.data.model.Course

@Database(entities = [Course::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}