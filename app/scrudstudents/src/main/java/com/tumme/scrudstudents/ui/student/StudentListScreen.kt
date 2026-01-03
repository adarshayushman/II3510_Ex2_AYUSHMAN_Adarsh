package com.tumme.scrudstudents.ui.student

import com.tumme.scrudstudents.ui.components.TableHeader
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    viewModel: StudentListViewModel = hiltViewModel(),
    onNavigateToForm: () -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {},
    // ADD THESE TWO PARAMETERS
    onNavigateToCourses: () -> Unit = {},
    onNavigateToSubscriptions: () -> Unit = {}
) {
    val students by viewModel.students.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("University Management") })
        },
        // ADD THIS BOTTOM BAR
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(onClick = { /* Already here */ }) {
                        Text("Students")
                    }
                    TextButton(onClick = onNavigateToCourses) {
                        Text("Courses")
                    }
                    TextButton(onClick = onNavigateToSubscriptions) {
                        Text("Subscriptions")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToForm) {
                Text("+")
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {
            TableHeader(cells = listOf("DOB","Last", "First", "Gender", "Actions"),
                weights = listOf(0.25f, 0.25f, 0.25f, 0.15f, 0.10f))

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(students) { student ->
                    StudentRow(
                        student = student,
                        onEdit = { /* ... */ },
                        onDelete = { viewModel.deleteStudent(student) },
                        onView = { onNavigateToDetail(student.idStudent) },
                        onShare = { /* ... */ }
                    )
                }
            }
        }
    }
}