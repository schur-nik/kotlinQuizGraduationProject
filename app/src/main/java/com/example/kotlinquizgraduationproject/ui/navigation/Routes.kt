package com.example.kotlinquizgraduationproject.ui.navigation

sealed class Routes(val route: String) {

    data object SplashScreen : Routes("SplashScreen")
    data object MenuScreen : Routes("MenuScreen")
    data object LevelsScreen : Routes("LevelsScreen")
    data object QuizScreen : Routes("QuizScreen/{difficulty}&{category}") {
        fun createRoute(difficulty: String, category: String): String {
            return "QuizScreen/$difficulty&$category"
        }
    }

}