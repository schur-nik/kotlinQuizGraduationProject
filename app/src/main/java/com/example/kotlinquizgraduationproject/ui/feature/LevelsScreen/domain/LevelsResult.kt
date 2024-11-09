package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

sealed class LevelsResult {

    data class QuestionCategoriesListLoaded(val list: List<String>) : LevelsResult()

    data object Loading : LevelsResult()

    data class Failure(val error: Throwable? = null) : LevelsResult()

}