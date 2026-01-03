package com.tumme.scrudstudents.ui.subscribe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tumme.scrudstudents.data.local.model.CourseEntity
import com.tumme.scrudstudents.data.local.model.StudentEntity
import com.tumme.scrudstudents.data.local.model.SubscribeEntity
import com.tumme.scrudstudents.data.repository.SCRUDRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscribeViewModel @Inject constructor(
    private val repository: SCRUDRepository
) : ViewModel() {

    // 1. List of all subscriptions
    val allSubscriptions: StateFlow<List<SubscribeEntity>> = repository.getAllSubscribes()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    // 2. List of all students (for the selection dropdown)
    val allStudents: StateFlow<List<StudentEntity>> = repository.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    // 3. List of all courses (for the selection dropdown)
    val allCourses: StateFlow<List<CourseEntity>> = repository.getAllCourses()
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), emptyList())

    // 4. Function to save a new subscription
    fun saveSubscription(studentId: Int, courseId: Int, score: Float) {
        viewModelScope.launch {
            val subscription = SubscribeEntity(
                idStudent = studentId,
                idCourse = courseId,
                score = score
            )
            repository.insertSubscribe(subscription)
        }
    }

    // 5. Function to delete a subscription
    fun deleteSubscription(subscription: SubscribeEntity) {
        viewModelScope.launch {
            repository.deleteSubscribe(subscription)
        }
    }
}