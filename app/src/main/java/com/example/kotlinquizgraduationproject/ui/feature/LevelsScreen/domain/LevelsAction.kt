package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Category

sealed class LevelsAction {

    data object Init : LevelsAction()

    data object LoadProgress : LevelsAction()

    data object LoadFavorites : LevelsAction()

    data class ChangeFavorite(val category: String, val favorite: Boolean) : LevelsAction()

}