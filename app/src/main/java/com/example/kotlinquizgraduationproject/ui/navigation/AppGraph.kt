package com.example.kotlinquizgraduationproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.LevelsScreen
import com.example.kotlinquizgraduationproject.ui.feature.MenuScreen.MenuScreen

@Composable
fun AppGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.MenuScreen.route) {
        composable(Routes.MenuScreen.route) {
//            MenuScreen(navController)
            MenuScreen(navController)
        }
        composable(Routes.LevelsScreen.route) {
            LevelsScreen(navController)
        }
//        composable(
//            Routes.CharacterDetail.route,
//            arguments = listOf(navArgument("id") { type = NavType.StringType })
//        ) { backStack ->
//            val id = backStack.arguments?.getString("id")
//            CharacterDetailScreen(id.orEmpty())
//        }
    }
}