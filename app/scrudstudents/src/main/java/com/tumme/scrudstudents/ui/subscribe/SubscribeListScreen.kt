package com.tumme.scrudstudents.ui.subscribe

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tumme.scrudstudents.ui.subscribe.SubscribeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscribeListScreen(
    viewModel: SubscribeViewModel,
    onAddSubscribeClick: () -> Unit
) {
    val subscriptions by viewModel.allSubscriptions.collectAsState()
    val students by viewModel.allStudents.collectAsState()
    val courses by viewModel.allCourses.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Subscriptions & Scores") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddSubscribeClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Subscription")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(subscriptions) { sub ->
                // CHALLENGE: Find names instead of displaying IDs
                val studentName = students.find { it.idStudent == sub.idStudent }?.let { "${it.firstName} ${it.lastName}" } ?: "Unknown Student"
                val courseName = courses.find { it.idCourse == sub.idCourse }?.nameCourse ?: "Unknown Course"

                SubscriptionItem(
                    studentName = studentName,
                    courseName = courseName,
                    score = sub.score,
                    onDelete = { viewModel.deleteSubscription(sub) }
                )
            }
        }
    }
}

@Composable
fun SubscriptionItem(studentName: String, courseName: String, score: Float, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = studentName, style = MaterialTheme.typography.titleMedium)
                Text(text = "Course: $courseName", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Score: $score", color = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}