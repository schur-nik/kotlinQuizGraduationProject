package com.example.kotlinquizgraduationproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlinquizgraduationproject.model.quizinfo.LevelInformation
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.LevelsScreen
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.MenuScreen
import com.example.kotlinquizgraduationproject.ui.feature.QuizScreen.QuizScreen

@Composable
fun AppGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.MenuScreen.route) {
        composable(Routes.MenuScreen.route) {
            MenuScreen(navController)
        }
        composable(Routes.LevelsScreen.route) {
            LevelsScreen(navController)
        }
        composable(
            Routes.QuizScreen.route,
            arguments = listOf(
                navArgument("category") { type = NavType.StringType },
                navArgument("difficulty") { type = NavType.StringType }
            )
        ) { backStack ->

            val difficulty = backStack.arguments?.getString("difficulty")
            val category = backStack.arguments?.getString("category")
            val levelInformation = LevelInformation(difficulty, category)
            QuizScreen(levelInformation, navController)
        }
    }
}