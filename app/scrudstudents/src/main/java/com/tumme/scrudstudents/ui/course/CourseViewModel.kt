package com.tumme.scrudstudents.ui.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumme.scrudstudents.data.local.model.CourseEntity
import com.tumme.scrudstudents.data.repository.SCRUDRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseViewModel @Inject constructor(
    private val repository: SCRUDRepository
) : ViewModel() {

    // Expose the list of courses using StateFlow
    val allCourses: StateFlow<List<CourseEntity>> = repository.getAllCourses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Function to save a course with validation
    fun saveCourse(name: String, ects: Float, level: String) {
        // Validation Challenge: ECTS must be greater than 0
        if (ects > 0 && name.isNotBlank()) {
            viewModelScope.launch {
                val newCourse = CourseEntity(
                    nameCourse = name,
                    ectsCourse = ects,
                    levelCourse = level
                )
                repository.insertCourse(newCourse)
            }
        }
    }

    fun deleteCourse(course: CourseEntity) {
        viewModelScope.launch {
            repository.deleteCourse(course)
        }
    }
}