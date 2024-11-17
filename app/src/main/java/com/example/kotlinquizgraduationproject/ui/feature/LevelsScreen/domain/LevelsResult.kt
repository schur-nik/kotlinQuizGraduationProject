package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Category
import com.example.kotlinquizgraduationproject.model.usersinfo.LevelProgress

sealed class LevelsResult {

    data class QuestionCategoriesListLoaded(val listCategory: List<Category>) : LevelsResult()

    data object Loading : LevelsResult()

    data class Failure(val error: Throwable? = null) : LevelsResult()

    data class LevelsProgressLoaded(val listProgress: List<LevelProgress>) : LevelsResult()

    data class FavoriteCategoriesLoaded(val listFavorites: List<Category>) : LevelsResult()

    data class FavoriteCategoriesChanged(val listFavorites: List<Category>) : LevelsResult()

}