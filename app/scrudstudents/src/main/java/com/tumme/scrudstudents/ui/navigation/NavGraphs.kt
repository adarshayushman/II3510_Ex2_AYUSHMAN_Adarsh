package com.tumme.scrudstudents.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import com.tumme.scrudstudents.ui.student.StudentListScreen
import com.tumme.scrudstudents.ui.student.StudentFormScreen
import com.tumme.scrudstudents.ui.student.StudentDetailScreen

import com.tumme.scrudstudents.ui.course.CourseViewModel
import com.tumme.scrudstudents.ui.course.CourseListScreen
import com.tumme.scrudstudents.ui.course.CourseFormScreen
import com.tumme.scrudstudents.ui.subscribe.SubscribeViewModel
import com.tumme.scrudstudents.ui.subscribe.SubscribeListScreen
import com.tumme.scrudstudents.ui.subscribe.SubscribeFormScreen
import androidx.hilt.navigation.compose.hiltViewModel


object Routes {
    const val STUDENT_LIST = "student_list"
    const val STUDENT_FORM = "student_form"
    const val STUDENT_DETAIL = "student_detail/{studentId}"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.STUDENT_LIST) {
        composable(Routes.STUDENT_LIST) {
            StudentListScreen(onNavigateToForm = { navController.navigate(Routes.STUDENT_FORM) },
                onNavigateToDetail = { id -> navController.navigate("student_detail/$id") },
                onNavigateToCourses = { navController.navigate("course_list") },
                onNavigateToSubscriptions = { navController.navigate("subscribe_list") })

        }
        composable(Routes.STUDENT_FORM) {
            StudentFormScreen(onSaved = { navController.popBackStack() })
        }
        composable("student_detail/{studentId}", arguments = listOf(navArgument("studentId"){ type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("studentId") ?: 0
            StudentDetailScreen(studentId = id, onBack = { navController.popBackStack() })
        }
        composable("course_list") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            CourseListScreen(
                viewModel = courseViewModel,
                onAddCourseClick = { navController.navigate("course_form") }
            )
        }
        composable("course_form") {
            val courseViewModel: CourseViewModel = hiltViewModel()
            CourseFormScreen(
                viewModel = courseViewModel,
                onSaveSuccess = { navController.popBackStack() }
            )
        }
        composable("subscribe_list") {
            val subscribeViewModel: SubscribeViewModel = hiltViewModel()
            SubscribeListScreen(
                viewModel = subscribeViewModel,
                onAddSubscribeClick = { navController.navigate("subscribe_form") }
            )
        }
        composable("subscribe_form") {
            val subscribeViewModel: SubscribeViewModel = hiltViewModel()
            SubscribeFormScreen(
                viewModel = subscribeViewModel,
                onSaveSuccess = { navController.popBackStack() }
            )
    }
}
}