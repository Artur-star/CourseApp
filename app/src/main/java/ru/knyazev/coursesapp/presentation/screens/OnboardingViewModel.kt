package ru.knyazev.coursesapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.knyazev.coursesapp.data.model.Course
import ru.knyazev.coursesapp.domain.usecases.GetAllCourseUseCases
import javax.inject.Inject

data class CoursesListState(
    val coursesList: List<Course> = emptyList(),
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getCourseUseCases: GetAllCourseUseCases,
) : ViewModel() {

    private val _coursesListState = MutableStateFlow(CoursesListState())
    val coursesListState: StateFlow<CoursesListState> get() = _coursesListState

    init {
        getAllCourses()
    }

    private fun getAllCourses() {
        viewModelScope.launch {
            _coursesListState.update { coursesListState ->
                coursesListState.copy(coursesList = getCourseUseCases.invoke() ?: emptyList())
            }
        }
    }
}