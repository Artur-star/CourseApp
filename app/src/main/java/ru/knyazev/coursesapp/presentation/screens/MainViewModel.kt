package ru.knyazev.coursesapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.knyazev.coursesapp.data.CourseRepository
import ru.knyazev.coursesapp.domain.model.CourseUI
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val courseRepository: CourseRepository,
) : ViewModel() {

    val state = MutableStateFlow(MainState.initState)

    fun getAllCoursesFromApi() {
        viewModelScope.launch {
            courseRepository.getCoursesFromApi()?.let { listCourses ->
                state.update {
                    it.copy(
                        listCourses = listCourses
                    )
                }
            }
        }
    }

    fun addCourseToCache(courseUI: CourseUI) {
        viewModelScope.launch {
            courseRepository.addCourseToCache(courseUI)
        }.apply {
            state.update { it.copy(tempAddCourseUI = null) }
            getAllCoursesFromCache()
        }
    }

    fun getAllCoursesFromCache() {
        viewModelScope.launch {
            courseRepository.getCoursesFromCache().let { listCourses ->
                state.update { it.copy(listCourses = listCourses) }
            }
        }
    }

    fun deleteCourse(courseUI: CourseUI) {
        viewModelScope.launch {
            courseRepository.deleteCourse(courseUI).apply {
                getAllCoursesFromCache()
            }
        }
    }

    fun updateCourseToFavorites(courseUI: CourseUI) {
        viewModelScope.launch {
            courseRepository.updateCourseToCache(courseUI).apply {
                state.update { it.copy(isFavorite = courseUI.hasLike) }
            }
        }
    }
}