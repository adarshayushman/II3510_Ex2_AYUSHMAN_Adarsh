package com.tumme.scrudstudents.ui.subscribe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tumme.scrudstudents.data.local.model.CourseEntity
import com.tumme.scrudstudents.data.local.model.StudentEntity
import com.tumme.scrudstudents.ui.subscribe.SubscribeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscribeFormScreen(
    viewModel: SubscribeViewModel,
    onSaveSuccess: () -> Unit
) {
    val students by viewModel.allStudents.collectAsState()
    val courses by viewModel.allCourses.collectAsState()

    var selectedStudent by remember { mutableStateOf<StudentEntity?>(null) }
    var selectedCourse by remember { mutableStateOf<CourseEntity?>(null) }
    var score by remember { mutableStateOf("") }

    var studentExpanded by remember { mutableStateOf(false) }
    var courseExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Assign Score to Student") }) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Student Dropdown Selection
            ExposedDropdownMenuBox(
                expanded = studentExpanded,
                onExpandedChange = { studentExpanded = !studentExpanded }
            ) {
                OutlinedTextField(
                    value = selectedStudent?.let { "${it.firstName} ${it.lastName}" } ?: "Select Student",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Student") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = studentExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = studentExpanded,
                    onDismissRequest = { studentExpanded = false }
                ) {
                    students.forEach { student ->
                        DropdownMenuItem(
                            text = { Text("${student.firstName} ${student.lastName}") },
                            onClick = {
                                selectedStudent = student
                                studentExpanded = false
                            }
                        )
                    }
                }
            }

            // Course Dropdown Selection
            ExposedDropdownMenuBox(
                expanded = courseExpanded,
                onExpandedChange = { courseExpanded = !courseExpanded }
            ) {
                OutlinedTextField(
                    value = selectedCourse?.nameCourse ?: "Select Course",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Course") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = courseExpanded,
                    onDismissRequest = { courseExpanded = false }
                ) {
                    courses.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course.nameCourse) },
                            onClick = {
                                selectedCourse = course
                                courseExpanded = false
                            }
                        )
                    }
                }
            }

            // Score Input
            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                label = { Text("Score") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            Button(
                onClick = {
                    val sId = selectedStudent?.idStudent ?: return@Button
                    val cId = selectedCourse?.idCourse ?: return@Button
                    val finalScore = score.toFloatOrNull() ?: 0f

                    viewModel.saveSubscription(sId, cId, finalScore)
                    onSaveSuccess()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedStudent != null && selectedCourse != null && score.isNotBlank()
            ) {
                Text("Save Subscription")
            }
        }
    }
}