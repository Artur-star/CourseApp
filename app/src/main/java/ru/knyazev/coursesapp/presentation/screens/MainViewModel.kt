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

    private val _homeState = MutableStateFlow(MainState.initState)
    val homeState = _homeState

    private val _favoritesState = MutableStateFlow(MainState.initState)
    val favoritesState = _favoritesState

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            val apiCourses = courseRepository.getCoursesFromApi() ?: emptyList()
            val cacheCourse = courseRepository.getCoursesFromCache()

            val mergeCourse = apiCourses.map { apiCourse ->
                cacheCourse.find { it.id == apiCourse.id }?.let { cacheCourse ->
                    apiCourse.copy(hasLike = cacheCourse.hasLike)
                } ?: apiCourse
            }

            _homeState.update {
                it.copy(listCourses = mergeCourse)
            }
            _favoritesState.update {
                it.copy(listCourses = cacheCourse)
            }
        }
    }

    fun getAllCoursesFromCache() {
        viewModelScope.launch {
            courseRepository.getCoursesFromCache().let { listCourses ->
                _favoritesState.update { it.copy(listCourses = listCourses) }
            }
        }
    }

    private fun deleteCourse(courseUI: CourseUI) {
        viewModelScope.launch {
            courseRepository.deleteCourse(courseUI)
        }
    }

    private fun addCourseToCache(courseUI: CourseUI) {
        viewModelScope.launch {
            courseRepository.addCourseToCache(courseUI)
        }
    }

    fun updateCourseToFavorites(courseUI: CourseUI) {
        viewModelScope.launch {
            val updateCourse = courseUI.copy(hasLike = !courseUI.hasLike)
            if (updateCourse.hasLike) {
                addCourseToCache(updateCourse)
            } else {
                deleteCourse(updateCourse)
            }
            _homeState.update { state ->
                state.copy(listCourses = state.listCourses.map {
                    if (it.id == updateCourse.id) updateCourse else it
                })
            }
            _favoritesState.update { state ->
                state.copy(listCourses = state.listCourses.map {
                    if (it.id == updateCourse.id) updateCourse else it
                })
            }
        }
    }
}