package com.example.kotlinquizgraduationproject.ui.navigation

sealed class Routes(val route: String) {

    data object MenuScreen : Routes("MenuScreen")
    data object LevelsScreen : Routes("LevelsScreen")
//    data object CharacterDetail : Routes("Characters/{id}") {
//        fun createRoute(id: String): String {
//            return "Characters/$id"
//        }
//    }

}