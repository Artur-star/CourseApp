package ru.knyazev.coursesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Course(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    @ColumnInfo("start_date") val startDate: String,
    @ColumnInfo("has_like") var hasLike: Boolean,
    @ColumnInfo("publish_date") val publishDate: String,
)

@Serializable
data class CoursesResponse(
    val courses: List<Course> = emptyList(),
)