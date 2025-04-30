package ru.knyazev.coursesapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.knyazev.coursesapp.data.CourseRepository
import ru.knyazev.coursesapp.data.model.Course
import javax.inject.Inject

data class CoursesListState(
    val coursesList: List<Course> = emptyList(),
    val isFavorite: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private val _coursesListState = MutableStateFlow(CoursesListState())
    val coursesListState: StateFlow<CoursesListState> get() = _coursesListState

    init {
        getAllCourses()
    }

    private fun getAllCourses() {
        viewModelScope.launch {

            val resultFromCache: List<Course> = courseRepository.getCoursesFromCache()


            val resultFromApi: List<Course> =
                courseRepository.getCoursesFromApi() ?: emptyList()
            if (resultFromApi.isNotEmpty()) {
                _coursesListState.update { coursesListState ->
                    coursesListState.copy(
                        coursesList = courseRepository.getCoursesFromApi() ?: emptyList()
                    )
                }
            }
        }
    }

    fun onFavoritesClicked(course: Course) {
        viewModelScope.launch {
            _coursesListState.update { coursesListState ->
                coursesListState.copy(
                    isFavorite = course.hasLike
                )
            }
            courseRepository.addCoursesToCache(course)
            courseRepository.updateCourseToCache(course = course)
        }
    }
}