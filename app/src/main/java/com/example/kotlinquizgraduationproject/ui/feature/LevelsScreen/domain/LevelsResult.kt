package com.example.kotlinquizgraduationproject.ui.feature.LevelsScreen.domain

import com.example.kotlinquizgraduationproject.model.quizinfo.Category

sealed class LevelsResult {

    data class QuestionCategoriesListLoaded(val list: List<Category>) : LevelsResult()

    data object Loading : LevelsResult()

    data class Failure(val error: Throwable? = null) : LevelsResult()

}