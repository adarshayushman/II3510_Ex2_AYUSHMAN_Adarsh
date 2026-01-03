package com.tumme.scrudstudents.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tumme.scrudstudents.ui.course.CourseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseFormScreen(
    viewModel: CourseViewModel,
    onSaveSuccess: () -> Unit // Navigate back to the list after saving
) {
    var name by remember { mutableStateOf("") }
    var ects by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("B1") } // Default value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add New Course") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Course Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = ects,
                onValueChange = { ects = it },
                label = { Text("ECTS (Must be > 0)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            // Simplest implementation for Level selection
            OutlinedTextField(
                value = level,
                onValueChange = { level = it },
                label = { Text("Level (e.g., B1, M1, PhD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val ectsValue = ects.toFloatOrNull() ?: 0f
                    // Use the validation logic we put in the ViewModel
                    viewModel.saveCourse(name, ectsValue, level)
                    onSaveSuccess()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && (ects.toFloatOrNull() ?: 0f) > 0
            ) {
                Text("Save Course")
            }
        }
    }
}