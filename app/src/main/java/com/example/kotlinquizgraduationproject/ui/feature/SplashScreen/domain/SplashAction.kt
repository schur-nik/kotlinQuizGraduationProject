package com.example.kotlinquizgraduationproject.ui.feature.SplashScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Category

sealed class SplashAction {

    data class AddUserToDB(val name: String, val listFavorite : List<Category>) : SplashAction()

}